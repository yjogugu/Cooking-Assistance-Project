package com.taijoo.cookingassistance.view.storage_material

import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taijoo.cookingassistance.data.model.MaterialData
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import com.taijoo.cookingassistance.util.NetworkState
import com.taijoo.cookingassistance.view.search.SearchActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StorageMaterialViewModel @Inject constructor(private val repository: StorageMaterialRepository) : ViewModel(){


    var adapterPosition = 0//어뎁터 포지션

    var viewType = 0 // 0: 기본 , 1:검색 화면 전환
    private var _storage : MutableStateFlow<StorageMaterialData> = MutableStateFlow<StorageMaterialData>(StorageMaterialData())
    val storage : StateFlow<StorageMaterialData> get() = _storage.asStateFlow()

    //viewModelScope 은 메인쓰레드로 동작하게끔 만들어졌기에 PagingData3 + Room 에 접근시 에러가 나기 때문에 코루틴으로 백그라운드로 실행하여야한다
    private var _pagingData = MutableStateFlow<PagingData<StorageMaterialData>>(PagingData.empty())
    val pagingData : StateFlow<PagingData<StorageMaterialData>> = _pagingData.asStateFlow()

    init {
        getData()
        getPagingData()
    }

    //db 옵저버
    private fun getData(){
        viewModelScope.launch {
            _storage.collectLatest {
                repository.getStorage(it.seq).collectLatest {data ->
                    if(data != null){
                        _storage.emit(data)
                    }

                }
            }

        }

    }

    //페이징 데이터
    private fun getPagingData(){
        viewModelScope.launch {
            repository.getPagingStorage().collectLatest {
                _pagingData.emit(it)
            }
        }
    }

    //아이템 데이터
    fun setStorage(item : StorageMaterialData , position : Int){
        viewModelScope.launch {
            adapterPosition = position
            _storage.emit(item)
        }

    }


}