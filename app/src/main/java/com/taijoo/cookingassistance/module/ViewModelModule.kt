package com.taijoo.cookingassistance.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(ViewModelComponent::class)
@Module
class ViewModelModule {

    @Provides
    @ViewModelScoped
    @Named("seq")
    fun provideTestString() = "das"
}