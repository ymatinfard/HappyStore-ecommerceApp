package com.matin.happystore.core.network.di

import com.matin.happystore.core.network.HappyStoreDataSource
import com.matin.happystore.core.network.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredNetworkModule {

    @Binds
    @Singleton
    fun bindDataSource(datasource: RetrofitNetwork): HappyStoreDataSource
}