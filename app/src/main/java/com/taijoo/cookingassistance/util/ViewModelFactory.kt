package com.taijoo.cookingassistance.util

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import com.taijoo.cookingassistance.view.MainActivity
import com.taijoo.cookingassistance.view.MainViewModel
import com.taijoo.cookingassistance.view.cookinglist.CookingListFragment
import com.taijoo.cookingassistance.view.search.SearchActivity
import com.taijoo.cookingassistance.view.search.SearchViewModel
import com.taijoo.cookingassistance.view.storage_material.StorageMaterialFragment

class ViewModelFactory(private var repository: StorageMaterialRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(repository) as T
        }
        else{
            throw IllegalAccessException("unknow view model class")
        }

    }

}