package com.taijoo.cookingassistance.view.search

import android.util.Log
import androidx.lifecycle.*
import com.taijoo.cookingassistance.data.model.MaterialData
import com.taijoo.cookingassistance.data.model.SearchCategoryData
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import com.taijoo.cookingassistance.util.NetworkChecker
import com.taijoo.cookingassistance.util.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: StorageMaterialRepository , private val networkChecker: NetworkChecker) : ViewModel() {

    private val _item = MutableStateFlow(emptyList<MaterialData>())//서버에서 받아올 식재품 목록
    val item : StateFlow<List<MaterialData>> get() = _item.asStateFlow()//서버에서 받아올 식재품 목록

    val search = MutableLiveData<String>()//검색 내용


    private val _listItem = MutableLiveData<List<StorageMaterialData>>()//리사이클러뷰에 뿌려줄 아이템 목록
    val listItem : LiveData<List<StorageMaterialData>> get() = _listItem //리사이클러뷰에 뿌려줄 아이템 목록


    private lateinit var storage : StateFlow<List<StorageMaterialData>>//로컬디비 데이터


    lateinit var categoryItem : List<SearchCategoryData>//카테고리 아이템


    private val _networkState = MutableSharedFlow<NetworkState>(replay = 1)
    val networkState: SharedFlow<NetworkState> get() = _networkState//네트워크 체크용

    var networkCheck: Boolean = false//네트워크 체크용2

    companion object{
        const val TAG = "SearchViewModel"
    }

    init {

        val list = ArrayList<MaterialData>()
        list.add(MaterialData(0,"",0,""))
        _item.value = list

        getNetworkCheck()

    }

    private fun getNetworkCheck(){
        viewModelScope.launch {

            networkChecker.networkState.collectLatest {
                networkCheck = it == NetworkState.Connected
                _networkState.emit(it)
            }

        }
    }



    //Room 에서 입력한 키워드 값 가져오기
    fun getStorage(name : String) :StateFlow<List<StorageMaterialData>> {

        storage = repository.getStorage(name).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

        return storage
    }


    //Room 에 재료 저장
    fun setStorage(storageMaterialData : StorageMaterialData){

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insertStorage(storageMaterialData)

            }
        }

    }


    //서버에서 검색 리스트 재료 가져오기
    fun getMaterialList(search : String){
        viewModelScope.launch {
            try {
                val response = repository.getMaterialList(search)

                when(response.isSuccessful){
                    true->{
                        _item.emit(response.body()!!.response.data)
                    }
                    false->{
                        Log.e(TAG,"getMaterialList false")
                    }
                }
            }
            catch (e : IOException){
                Log.e(TAG,e.message.toString())
            }
            catch (e : Exception){
                Log.e(TAG,e.message.toString())
            }

        }

    }


    //서버에 재료 추가 하기
    fun setMaterialList(type : Int,search : String)  {
        viewModelScope.launch {
            try {
                val response = repository.setSearchMaterialData(type,search)

                when(response.isSuccessful){
                    true ->{
                        val item = ArrayList<StorageMaterialData>()
                        item.add(StorageMaterialData(response.body()!!.response.data[0].seq.toLong(),search,0,0,
                            "0000-00-00" , SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(System.currentTimeMillis())))
//                        _listItem.emit(item)
                        _listItem.value = item

                    }
                    false ->{
                        Log.e(TAG,"setMaterialList false")
                    }

                }
            }
            catch (e : IOException){
                Log.e(TAG,e.message.toString())
            }
            catch (e : Exception){
                Log.e(TAG,e.message.toString())
            }

        }
    }


    //서버에 데이터가 있을떄 리사이클러뷰 아이템 뿌려주기
    fun setAdapterData(search : String , roomData : List<StorageMaterialData>){
        viewModelScope.launch {
            val list = ArrayList<StorageMaterialData>()

            for(j in 0 until item.value.size){
                if (item.value[j].name.contains(search) && search != ""){

                    list.add(StorageMaterialData(
                        item.value[j].seq.toLong() , item.value[j].name , 0 ,item.value[j].type
                        ,"0000-00-00", SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(System.currentTimeMillis())))

                }
            }

            //로컬디비에 있는 재료 갯수 가져와서 적용
            for (listItem in list){
                if(!list.isNullOrEmpty()){
                    for (j in roomData.indices){
                        if(listItem.seq == roomData[j].seq){
                            listItem.size = roomData[j].size
                        }
                    }
                }
            }
            _listItem.value = list
//            _listItem.emit(list)

        }

    }

    //카테고리 가져오기
    fun getSearchCategory(){
        viewModelScope.launch {
            try {
                val response = repository.getSearchCategory("category")

                when(response.isSuccessful){
                    true ->{
                        categoryItem = response.body()!!.data.data

                    }
                    false ->{

                    }
                }
            }
            catch (e : IOException){
                Log.e(TAG,e.message.toString())
            }
            catch (e : Exception){
                Log.e(TAG,e.message.toString())
            }

        }

    }


}