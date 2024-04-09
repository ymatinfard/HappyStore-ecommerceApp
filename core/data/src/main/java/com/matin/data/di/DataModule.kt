package com.matin.data.di

import com.matin.data.HappyStoreRepository
import com.matin.data.HappyStoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    @Singleton
    fun bindRepository(repository: HappyStoreRepositoryImpl): HappyStoreRepository
}