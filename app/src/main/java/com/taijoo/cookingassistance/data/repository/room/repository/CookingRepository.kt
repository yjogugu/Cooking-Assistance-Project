package com.taijoo.cookingassistance.data.repository.room.repository

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.taijoo.cookingassistance.data.model.CookingListData
import com.taijoo.cookingassistance.data.model.CookingListResponse
import com.taijoo.cookingassistance.data.model.CookingRecipeData
import com.taijoo.cookingassistance.data.model.CookingRecipeResponse
import com.taijoo.cookingassistance.data.repository.http.ServerApi
import com.taijoo.cookingassistance.data.repository.room.dao.StorageMaterialDao
import com.taijoo.cookingassistance.util.NetworkState
import com.taijoo.cookingassistance.view.cookinglist.CookingListPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Response
import javax.inject.Inject


class CookingRepository @Inject constructor(private val storageMaterialDao : StorageMaterialDao, private val service : ServerApi){


    //로컬디비에있는 재료 가져오기
    fun getStorageName() = storageMaterialDao.getStorageName().flowOn(Dispatchers.IO)


    //서버에서 요리리스트 가져오기
    fun getSelectFoodList(type : Int , localData : String) : Flow<PagingData<CookingListData>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 10 ),
            pagingSourceFactory = { CookingListPagingSource(service, type ,localData) }
        ).flow
    }

    //서버에서 요리 레시피 가져오기
    suspend fun getRecipe(seq : Int) : Response<CookingRecipeResponse> {
        return service.getSelectRecipe("recipe",seq)
    }
}