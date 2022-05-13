package com.taijoo.cookingassistance.module

import android.content.Context
import com.taijoo.cookingassistance.data.repository.room.dao.StorageMaterialDao
import com.taijoo.cookingassistance.data.repository.room.database.UserDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//Room Hilt 관련 모듈
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): UserDB {
        return UserDB.getInstance(context)!!
    }

    @Provides
    fun provideStorageMaterialDao(userDB: UserDB) : StorageMaterialDao{
        return userDB.storageMaterialDao()
    }
}