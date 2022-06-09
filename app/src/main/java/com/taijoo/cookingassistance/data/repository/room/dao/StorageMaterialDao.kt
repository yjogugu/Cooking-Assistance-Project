package com.taijoo.cookingassistance.data.repository.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface StorageMaterialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorageMaterial(storageMaterialData: StorageMaterialData)

    //검색쪽 관련 데이터 가져오기
    @Query("SELECT * FROM StorageMaterial WHERE name LIKE '%' ||:name|| '%' ORDER BY seq")
    fun getStorage(name : String): Flow<List<StorageMaterialData>>

    //재료 전체 데이터 가져오기
    @Query("SELECT * FROM StorageMaterial ORDER BY seq")
    fun getStorage(): Flow<StorageMaterialData?>

    //추천 데이터용 재료 이름만 가져오기
    @Query("SELECT name FROM StorageMaterial WHERE type  IN (0,1) ORDER BY seq")
    fun getStorageName(): Flow<List<String>>


    //본인이 가지고있는 재료 페이징 형태로 가져오기
    @Query("SELECT * FROM StorageMaterial ORDER BY seq ASC LIMIT :loadSize OFFSET (:startSize-1) * :loadSize")
    fun getStorageList(startSize: Int, loadSize: Int): List<StorageMaterialData>


    //유통기한 변경하기
    @Query("UPDATE StorageMaterial SET size = :size WHERE seq = :seq")
    suspend fun setUpdateDate(seq: Long, size: Int)

    //특정데이터만 가져오기
    @Query("SELECT * FROM StorageMaterial WHERE seq = :seq")
    fun getStorage(seq: Long) : Flow<StorageMaterialData?>

    //해당 항목 지우기
    @Delete
    suspend fun deleteStorageMaterial(storageMaterialData: StorageMaterialData)

}