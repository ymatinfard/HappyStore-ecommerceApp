package com.matin.happystore.domain

interface HappyStoreRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getSingleProduct(id: Int): Product
}