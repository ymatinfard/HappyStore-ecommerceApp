package com.matin.happystore.domain

import com.matin.happystore.domain.model.Product
import com.matin.happystore.utils.Result

interface HappyStoreRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getSingleProduct(id: Int): Result<Product>
}