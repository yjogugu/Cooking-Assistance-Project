package com.taijoo.cookingassistance.data.repository.room.repository

import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.taijoo.cookingassistance.data.model.SearchMaterialData
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.http.ServerApi
import com.taijoo.cookingassistance.data.repository.room.dao.StorageMaterialDao
import com.taijoo.cookingassistance.view.storage_material.StorageMaterialPagingSource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageMaterialRepository @Inject constructor(private val storageMaterialDao : StorageMaterialDao , private val service : ServerApi) {

    //재료 저장
    suspend fun insertStorage(storageMaterialData : StorageMaterialData){
        storageMaterialDao.insertStorageMaterial(storageMaterialData)
    }

    //현재 재료 가져오기
    fun getStorage(name : String) = storageMaterialDao.getStorage(name).asLiveData()

    //현재 재료 가져오기
    fun getStorage() = storageMaterialDao.getStorage().asLiveData()

    //재료 가져오기 페이징
    fun getPagingStorage() : Flow<PagingData<StorageMaterialData>>{

        return Pager( config = PagingConfig( pageSize = 20, enablePlaceholders = false ), pagingSourceFactory = { StorageMaterialPagingSource(storageMaterialDao) } ).flow
    }


    //검색 가능한 재료 가져오기
    suspend fun getMaterialList(search : String) : Response<SearchMaterialData> {
        return service.getSearchMaterialData(search)
    }


    //검색한 내용이 서버에 없을때 서버에 추가
    suspend fun setSearchMaterialData(search : String) : Response<SearchMaterialData> {
        return service.setSearchMaterialData(search)
    }
}