package com.taijoo.cookingassistance.data.repository.room.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.taijoo.cookingassistance.data.repository.http.ServerApi
import com.taijoo.cookingassistance.view.cookinglist.CookingListPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


class CookingRepository @Inject constructor(private val service : ServerApi){


    fun getTestData() : Flow<PagingData<String>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10),
            pagingSourceFactory = { CookingListPagingSource(service) }
        ).flow
    }
}