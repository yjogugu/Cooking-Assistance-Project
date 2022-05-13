package com.taijoo.cookingassistance.view.cookinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taijoo.cookingassistance.data.repository.room.repository.CookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CookingListViewModel @Inject constructor(private val repository: CookingRepository) : ViewModel() {

    private var item : Flow<PagingData<String>>? = null


    fun getTestData():Flow<PagingData<String>>{

        val newItem : Flow<PagingData<String>> = repository.getTestData().cachedIn(viewModelScope)

        item = newItem

        return newItem
    }
}