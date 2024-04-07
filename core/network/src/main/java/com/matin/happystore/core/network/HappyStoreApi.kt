package com.matin.happystore.core.network

import com.matin.happystore.core.network.model.NetworkProduct
import retrofit2.http.GET
import retrofit2.http.Path

interface HappyStoreApi {

    @GET("products")
    suspend fun getAllProducts(): List<NetworkProduct>

    @GET("products/{id}")
    suspend fun getSingleProduct(@Path("id") id: Int): NetworkProduct
}