package com.taijoo.cookingassistance.module

import com.taijoo.cookingassistance.data.repository.http.ServerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//서버 통신 Hilt 관련 모듈
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideServerApi():ServerApi{
        return ServerApi.create()
    }
}