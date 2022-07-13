package com.taijoo.cookingassistance.data.repository.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import com.taijoo.cookingassistance.data.repository.room.dao.StorageMaterialDao

@Database(entities = [StorageMaterialData::class] , version = 4 , exportSchema = false)

abstract class UserDB : RoomDatabase(){

    abstract fun storageMaterialDao() : StorageMaterialDao


    companion object{
        @Volatile private var INSTANCE : UserDB? = null

        fun getInstance(context: Context):UserDB?{

            if(INSTANCE == null){
                synchronized(UserDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserDB::class.java, "User.db")
                        .fallbackToDestructiveMigration()//DB가 업데이트 됬을때 데이터 초기화
//                        .allowMainThreadQueries()
                        .build()
                }
            }

            return INSTANCE
        }
    }

}