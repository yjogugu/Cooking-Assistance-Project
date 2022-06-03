package com.taijoo.cookingassistance.view.storage_material_setting

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageMaterialSettingViewModel @Inject constructor(private val repository: StorageMaterialRepository): ViewModel() {

    private var _storageData = MutableStateFlow(StorageMaterialData())

    val storageData : StateFlow<StorageMaterialData> = _storageData.asStateFlow()

    var seq : Long = 0//intent 넘겨받은 고유값

    //유통기한 변경하기
    fun setExpirationDate(date : String){
        _storageData.update {
            it.copy(expiration_date = date)
        }
    }

    //구입한 날짜 변경하기
    fun setDate(date : String){
        _storageData.update {
            it.copy(date = date)
        }
    }

    //정보 수정
    fun setData(){
        viewModelScope.launch {
            repository.insertStorage(storageData.value)
        }
    }

    //정보 가져오기
    fun getData(){
        viewModelScope.launch {
            repository.getStorage(seq).collectLatest {
                _storageData.emit(it)
            }
        }

    }

    //재료삭제
    fun setDelete(){
        viewModelScope.launch {
            repository.deleteStorage(storageData.value)
        }
    }

}