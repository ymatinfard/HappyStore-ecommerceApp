package com.matin.happystore.domain.di

import com.matin.happystore.data.HappyStoreRepositoryImp
import com.matin.happystore.domain.HappyStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideRepository(repository: HappyStoreRepositoryImp): HappyStoreRepository
}