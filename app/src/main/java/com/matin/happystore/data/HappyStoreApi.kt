package com.matin.happystore.data

import retrofit2.http.GET
import retrofit2.http.Path

interface HappyStoreApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductEntity>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductEntity
}