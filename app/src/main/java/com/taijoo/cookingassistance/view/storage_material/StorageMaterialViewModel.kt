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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class StorageMaterialViewModel @Inject constructor(private val repository: StorageMaterialRepository) : ViewModel(){

//    //viewModelScope 은 메인쓰레드로 동작하게끔 만들어졌기에 PagingData3 + Room 에 접근시 에러가 나기 때문에 코루틴으로 백그라운드로 실행하여야한다
    private var _pagingData = MutableStateFlow<PagingData<StorageMaterialData>>(PagingData.empty())
    val pagingData : StateFlow<PagingData<StorageMaterialData>> = _pagingData.asStateFlow()


//    private var _pagingData = MutableStateFlow<List<StorageMaterialData>>(emptyList())
//    val pagingData : StateFlow<List<StorageMaterialData>> = _pagingData.asStateFlow()


    private var _list = MutableStateFlow<StorageMaterialData>(StorageMaterialData())
    val list : StateFlow<StorageMaterialData> = _list.asStateFlow()


//    private var _loadSize = MutableStateFlow<Int>(0)
//    val loadSize : StateFlow<Int> = _loadSize.asStateFlow()

    var loadSize = 0


    var adapterPosition = 1

    var seq : Long = 0

    init {
        getPagingData()
        getData()
    }



    //페이징 데이터
    private fun getPagingData(){
        viewModelScope.launch {
            repository.getPagingStorage().collectLatest {
                _pagingData.emit(it)
            }
        }
    }

//    //페이징 데이터
//    fun getPagingData(){
//        viewModelScope.launch {
//
//            val newItem = repository.getStorage(loadSize = loadSize).first()
//
//            _pagingData.emit(_pagingData.value+newItem)
//
////            _loadSize.collectLatest {size ->
////                repository.getStorage(size).collectLatest {
////                    val newItem = _pagingData.value + it
////                    _pagingData.emit(newItem)
////                }
////            }
//
//        }
//    }

//    private fun getData(){
//
//        viewModelScope.launch {
//            repository.getStorage().collectLatest {
//
//                val item = repository.getStorage(seq).first()
//                if(item == null){
//                    _list.emit(StorageMaterialData())
//                }
//                else{
//                    _list.emit(item)
//                }
//
//            }
//
//        }
//    }

    private fun getData(){

        viewModelScope.launch {
            repository.getStorage().collectLatest {

                val item = repository.getStorage(seq).first()
                if(item == null && seq.toInt() != 0){
                    Log.e("여기","ㅇㅇ")
                    _list.emit(StorageMaterialData())
                }
                else if(item != null){
                    _list.emit(item)
                }


            }

        }
    }


    fun setData(storageMaterialData : StorageMaterialData){
        viewModelScope.launch {
            repository.insertStorage(storageMaterialData)
        }
    }



}