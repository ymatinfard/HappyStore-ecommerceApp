package com.matin.happystore.core.network

import com.matin.happystore.core.network.model.NetworkProduct

interface HappyStoreDataSource {
    suspend fun getAllProducts(): List<NetworkProduct>
    suspend fun getSingleProduct(id: Int): NetworkProduct
}