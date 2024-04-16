package com.matin.data

import com.matin.happystore.core.model.Product
import com.matin.happystore.core.common.Result
import kotlinx.coroutines.flow.Flow

interface HappyStoreRepository {
    suspend fun getProducts(): Flow<List<Product>>
    suspend fun sync()
    suspend fun getSingleProduct(id: Int): Result<Product>
}