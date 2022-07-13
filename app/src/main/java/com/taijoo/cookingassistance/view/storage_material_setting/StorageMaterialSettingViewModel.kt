package com.taijoo.cookingassistance.view.storage_material_setting

import android.util.Log
import androidx.lifecycle.*
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class StorageMaterialSettingViewModel @Inject constructor(private val repository: StorageMaterialRepository , @Named("seq") a : String): ViewModel() {

    private var _storageData = MutableStateFlow(StorageMaterialData())

    val storageData : StateFlow<StorageMaterialData> = _storageData.asStateFlow()


    private var _seq = MutableStateFlow<Long>(0)//intent 넘겨받은 고유값
    val seq : StateFlow<Long> get() = _seq//intent 넘겨받은 고유값


    private var _size = MutableStateFlow<String>("0")//intent 넘겨받은 고유값
    val size : StateFlow<String> get() = _size//intent 넘겨받은 고유값

    init {

        viewModelScope.launch {
            seq.collectLatest {
                if(it != 0L){
                    getData(it)
                }

            }
        }


    }

    fun setSeq(value : Long){
        viewModelScope.launch {
            _seq.emit(value)
        }

    }
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
    private fun getData(seq : Long){
        viewModelScope.launch {
            repository.getStorage(seq).collectLatest {
                if(it != null){
                    _storageData.emit(it)
                    _size.emit(it.size.toString())
                }

            }
        }

    }

    //갯수 바꾸기
    fun setSize(str: String){

        val size = if(str == "" ){
            0
        }
        else{
            str.toInt()
        }

        _storageData.update {

            if(_storageData.value.size == 0){//size 가 Int 형이기때문에 받는값이 "" 일때 강제로 0 을 넣어준다 0을 넣어주면 커서 포지션이 앞쪽으로 이동하기에 그걸 방지하기 위하 코드
                val strSize = size.toString().replaceFirst(".$".toRegex(), "")
                if(strSize != ""){
                    it.copy(size = strSize.toInt())
                }
                else{
                    it.copy(size = size)
                }

            }
            else{
                it.copy(size = size)
            }

        }

    }

    //Compose TextFiled 용
    fun setNote(note : String){
        _storageData.update {
            it.copy(note = note)
        }
    }

    //재료삭제
    fun setDelete(){
        viewModelScope.launch {
            repository.deleteStorage(storageData.value)
        }
    }

}