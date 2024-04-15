package com.matin.happystore.core.database.model.di

import com.matin.happystore.core.database.model.HappyStoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideProductsDao(database: HappyStoreDatabase): ProductsDao = database.productsDao()
}