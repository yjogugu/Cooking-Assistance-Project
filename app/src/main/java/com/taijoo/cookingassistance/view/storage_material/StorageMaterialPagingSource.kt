package com.taijoo.cookingassistance.view.storage_material

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.dao.StorageMaterialDao
import com.taijoo.cookingassistance.data.repository.room.repository.StorageMaterialRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn


class StorageMaterialPagingSource(private val storageMaterialDao : StorageMaterialDao) : PagingSource<Int ,StorageMaterialData >(){

    //시작하는 값
    private companion object {
        const val INIT_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, StorageMaterialData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StorageMaterialData> {

        val position = params.key ?: INIT_PAGE_INDEX

        val items = storageMaterialDao.getStorageList(position,params.loadSize)

        return try {
            LoadResult.Page(
                data = items,
//                prevKey = if (position == INIT_PAGE_INDEX) null else position -1,
//                nextKey = if (items.isNullOrEmpty()) null else position + 1

                prevKey = if (position == INIT_PAGE_INDEX) null else position - 1,
                nextKey = if (items.isEmpty()) null else position + (params.loadSize / 20)

            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}