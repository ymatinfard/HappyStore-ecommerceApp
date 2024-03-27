package com.matin.happystore.di

import com.matin.happystore.data.HappyStoreApi
import com.matin.happystore.data.HappyStoreRepositoryImp
import com.matin.happystore.domain.HappyStoreRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitService(): HappyStoreApi =
        Retrofit.Builder().baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(HappyStoreApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideRepository(repository: HappyStoreRepositoryImp): HappyStoreRepository
}