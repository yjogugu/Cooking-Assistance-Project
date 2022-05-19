package com.taijoo.cookingassistance.view.cookinglist

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taijoo.cookingassistance.data.model.CookingListData
import com.taijoo.cookingassistance.data.model.CookingListResponse
import com.taijoo.cookingassistance.data.repository.room.repository.CookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CookingListViewModel @Inject constructor(private val repository: CookingRepository) : ViewModel() {

    private var item : Flow<PagingData<CookingListData>>? = null

    val storage : LiveData<List<String>> = repository.getStorageName()

    var type = MutableLiveData<Int>()  //0: 전체 요리리스트 , 1 : 현재 가지고있는 재료로 가능한 요리 리스트

    val jsonObject = JSONObject()
    val jsonArray = JSONArray()

    init {
        type.value = 0
    }

    //서버에서 음식 리스트 가져오기
    fun getSelectFoodList(localData : String) : Flow<PagingData<CookingListData>>{
        val newItem : Flow<PagingData<CookingListData>> = repository.getSelectFoodList(type.value!!,localData).cachedIn(viewModelScope)

        item = newItem

        return newItem

    }

}