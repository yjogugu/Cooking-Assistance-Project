package com.taijoo.cookingassistance.data.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageMaterialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorageMaterial(storageMaterialData: StorageMaterialData)

    @Query("SELECT * FROM StorageMaterial WHERE name LIKE '%' ||:name|| '%' ORDER BY seq")
    fun getStorage(name : String): Flow<List<StorageMaterialData>>

    @Query("SELECT * FROM StorageMaterial ORDER BY seq")
    fun getStorage(): Flow<List<StorageMaterialData>>

    @Query("SELECT * FROM StorageMaterial ORDER BY seq ASC LIMIT :loadSize OFFSET (:startSize-1) * :loadSize")
    fun getStorageList(startSize: Int, loadSize: Int): List<StorageMaterialData>



}