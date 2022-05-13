package com.taijoo.cookingassistance.view.cookinglist

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taijoo.cookingassistance.data.repository.http.ServerApi
import org.json.JSONObject

class CookingListPagingSource(private val service : ServerApi ) : PagingSource<Int , String>() {

    //시작하는 값
    private companion object {
        const val INIT_PAGE_INDEX = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val position = params.key ?: INIT_PAGE_INDEX

        val items = service.test(position,params.loadSize)

        val json = JSONObject(items.body()!!.string())

        val item = ArrayList<String>()

        val response = json.getJSONObject("response")


        val dataJsonArray = response.getJSONArray("data")
//
        for(i in 0 until dataJsonArray.length()){
            val dataObject : JSONObject = dataJsonArray.getJSONObject(i)
            item.add(dataObject.getString("name"))
        }


        return try {
            LoadResult.Page(
                data = item,
                prevKey = if (position == INIT_PAGE_INDEX) null else position - 1,
                nextKey = if (item.isEmpty()) null else position + (params.loadSize / 10)

            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}