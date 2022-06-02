package com.taijoo.cookingassistance.view.cookinglist

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taijoo.cookingassistance.data.model.CookingListData
import com.taijoo.cookingassistance.data.model.CookingListResponse
import com.taijoo.cookingassistance.data.repository.http.ServerApi
import com.taijoo.cookingassistance.util.NetworkState
import okio.IOException
import retrofit2.Response

class CookingListPagingSource(private val service : ServerApi,private val type : Int, private val localData : String) : PagingSource<Int , CookingListData>() {

    //시작하는 값
    private companion object {
        const val INIT_PAGE_INDEX = 1
        var SEQ = 0
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CookingListData> {
        val position = params.key ?: INIT_PAGE_INDEX

        var items : Response<CookingListResponse>? = null

        try {
            when (type) {
                0 -> {//전체
                    SEQ = 0
                    items = service.getSelectFoodList(type,position,params.loadSize)

                }
                1 -> {//요리가능한 목록
                    if(position != 1){
                        SEQ += 50
                    }
                    else if(position == 1){
                        SEQ = 0
                    }
                    items = service.getSelectFoodList(type,SEQ,localData)
                }
                else -> {

                }
            }
            val cookingListData = items?.body()!!.data.data

            return  if(type == 0){
                LoadResult.Page(
                    data = cookingListData,
                    prevKey = if (position == INIT_PAGE_INDEX) null else position - 1,
                    nextKey = if (cookingListData.isEmpty()) null else position + (params.loadSize / 10)
                )
            }
            else{
                LoadResult.Page(
                    data = cookingListData,
                    prevKey = if (position == INIT_PAGE_INDEX) null else position - 1,
                    nextKey = if (cookingListData.isEmpty()) null else position + 1
                )
            }
        }
        catch (e : IOException){
            return LoadResult.Error(e)
        }
        catch (e: Exception) {
            return LoadResult.Error(e)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, CookingListData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }



}