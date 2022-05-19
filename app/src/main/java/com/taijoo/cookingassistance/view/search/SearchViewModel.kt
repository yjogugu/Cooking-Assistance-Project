package com.taijoo.cookingassistance.view.search

import android.util.Log
import androidx.lifecycle.*
import com.taijoo.cookingassistance.data.model.MaterialData
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: StorageMaterialRepository) : ViewModel() {

    var liveDataType = 0

    private var _item = MutableLiveData<List<MaterialData>>()//서버에서 받아올 식재품 목록
    val item : LiveData<List<MaterialData>> get() = _item//서버에서 받아올 식재품 목록

    var search = MutableLiveData<String>()//검색 내용

    private var _listItem = MutableLiveData<List<StorageMaterialData>>()//리사이클러뷰에 뿌려줄 아이템 목록
    val listItem : LiveData<List<StorageMaterialData>> get() = _listItem //리사이클러뷰에 뿌려줄 아이템 목록


    lateinit var storage : LiveData<List<StorageMaterialData>>//로컬디비 데이터


    val TAG = "SearchViewModel"


    init {
        val list = ArrayList<MaterialData>()
        list.add(MaterialData(0,"",0,""))
        _item.value = list
    }

    //Room 에서 입력한 키워드 값 가져오기
    fun getStorage(name : String) :LiveData<List<StorageMaterialData>> {

        storage = repository.getStorage(name)

        return storage
    }


    //재료 저장
    fun setStorage(storageMaterialData : StorageMaterialData){
        liveDataType = 1

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insertStorage(storageMaterialData)

            }
        }

    }


    //서버에서 검색 리스트 재료 가져오기
    fun getMaterialList(search : String){
        viewModelScope.launch {
            val response = repository.getMaterialList(search)

            when(response.isSuccessful){
                true->{
                    _item.value = response.body()!!.response.data
                }
                false->{
                    Log.e(TAG,"getMaterialList false")
                }
            }
        }

    }


    //서버에 재료 추가 하기
    fun setMaterialList(type : Int,search : String)  {
        liveDataType = 0
        viewModelScope.launch {
            val response = repository.setSearchMaterialData(type,search)

            when(response.isSuccessful){
                true ->{
                    val item = ArrayList<StorageMaterialData>()
                    item.add(StorageMaterialData(response.body()!!.response.data[0].seq.toLong(),search,0,0,
                        SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(System.currentTimeMillis())))

                    _listItem.value = item

                }
                false ->{
                    Log.e(TAG,"setMaterialList false")
                }

            }
        }
    }


    //서버에 데이터가 있을떄 리사이클러뷰 아이템 뿌려주기
    fun setAdapterData(search : String , roomData : List<StorageMaterialData>){
        val list = ArrayList<StorageMaterialData>()

        for(j in 0 until item.value!!.size){
            if (item.value!![j].name.contains(search) && search != ""){

                list.add(StorageMaterialData(
                    item.value!![j].seq.toLong() , item.value!![j].name , 0 ,item.value!![j].type
                    , SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(System.currentTimeMillis())))

            }
        }

        //로컬디비에 있는 재료 갯수 가져와서 적용
        for(j in 0 until list.size){
            if(!list.isNullOrEmpty()){
                for(p in roomData.indices){
                    if(list[j].seq == roomData[p].seq){
                        list[j].size = roomData[p].size
                    }
                }
            }
        }

        _listItem.value = list


    }


}