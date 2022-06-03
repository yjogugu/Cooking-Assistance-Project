package com.taijoo.cookingassistance.view.storage_material

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taijoo.cookingassistance.data.model.MaterialData
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import com.taijoo.cookingassistance.util.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StorageMaterialViewModel @Inject constructor(private val repository: StorageMaterialRepository) : ViewModel(){


    val storage : StateFlow<List<StorageMaterialData>> = repository.getStorage().stateIn(viewModelScope, SharingStarted.Lazily , emptyList())

//    private var _storage : MutableStateFlow<List<StorageMaterialData>> = MutableStateFlow(emptyList())
//    val storage : StateFlow<List<StorageMaterialData>> = _storage.asStateFlow()

//    val storage : LiveData<List<StorageMaterialData>> get()= repository.getStorage().asLiveData()

    //viewModelScope 은 메인쓰레드로 동작하게끔 만들어졌기에 PagingData3 + Room 에 접근시 에러가 나기 때문에 코루틴으로 백그라운드로 실행하여야한다
//    val pagingData : Flow<PagingData<StorageMaterialData>> = repository.getPagingStorage().cachedIn(CoroutineScope(Dispatchers.IO))

    private var _pagingData = MutableStateFlow<PagingData<StorageMaterialData>>(PagingData.empty())
    val pagingData : StateFlow<PagingData<StorageMaterialData>> = _pagingData.asStateFlow()

//    private var _pagingData = MutableSharedFlow<PagingData<StorageMaterialData>>(replay = 1)
//    val pagingData : SharedFlow<PagingData<StorageMaterialData>> = _pagingData.asSharedFlow()

    init {
//        getData()
        getPagingData()
    }

    //db 옵저버
    private fun getData(va : StorageMaterialData){

        viewModelScope.launch {
//            repository.getStorage().collectLatest {
//                _storage.emit(it)
//            }

        }

    }

    //페이징 데이터
    private fun getPagingData(){
        viewModelScope.launch {
            repository.getPagingStorage().collectLatest {
                Log.e("여기","ㅇㅇ")
                _pagingData.value = it
            }
        }
    }




}