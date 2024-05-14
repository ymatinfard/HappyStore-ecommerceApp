package com.matin.happystore.core.network.di

import android.content.Context
import com.matin.happystore.core.network.HappyStoreApi
import com.matin.happystore.core.network.demo.DemoAssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
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

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideDemoAssert(@ApplicationContext context: Context): DemoAssetManager =
        DemoAssetManager(context.assets::open)
}
