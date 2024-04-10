package com.matin.data

import com.matin.happystore.core.model.Product
import com.matin.happystore.core.common.Result

interface HappyStoreRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getSingleProduct(id: Int): Result<Product>
}