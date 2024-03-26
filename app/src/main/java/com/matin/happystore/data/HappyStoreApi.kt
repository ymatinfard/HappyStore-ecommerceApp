package com.matin.happystore.data

import com.matin.happystore.data.model.ProductEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface HappyStoreApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductEntity>

    @GET("products/{id}")
    suspend fun getSingleProduct(@Path("id") id: Int): ProductEntity
}