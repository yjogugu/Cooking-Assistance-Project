package com.taijoo.cookingassistance.view.storage_material

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taijoo.cookingassistance.data.model.MaterialData
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StorageMaterialViewModel @Inject constructor(private val repository: StorageMaterialRepository) : ViewModel(){


    //관찰용
    val storage : StateFlow<List<StorageMaterialData>> = repository.getStorage().stateIn(viewModelScope, SharingStarted.Lazily , emptyList())
//    val storage : LiveData<List<StorageMaterialData>> = repository.getStorage().asLiveData()

    //viewModelScope 은 메인쓰레드로 동작하게끔 만들어졌기에 PagingData3 + Room 에 접근시 에러가 나기 때문에 코루틴으로 백그라운드로 실행하여야한다
    val pagingData : Flow<PagingData<StorageMaterialData>> = repository.getPagingStorage().cachedIn(CoroutineScope(Dispatchers.IO))



    fun setDate(item : StorageMaterialData){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insertStorage(item)
            }

        }

    }




}