package com.matin.happystore.core.network

import com.matin.happystore.core.network.model.NetworkProduct
import kotlinx.coroutines.flow.Flow

interface HappyStoreDataSource {
    suspend fun getAllProducts(): List<NetworkProduct>
    suspend fun getSingleProduct(id: Int): Flow<NetworkProduct>
}