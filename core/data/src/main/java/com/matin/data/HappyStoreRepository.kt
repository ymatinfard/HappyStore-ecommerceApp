package com.matin.data

import com.matin.happystore.core.common.Result
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import kotlinx.coroutines.flow.Flow

interface HappyStoreRepository {
    suspend fun getProducts(): Flow<List<Product>>

    suspend fun sync()

    suspend fun getSingleProduct(id: Int): Result<Product>

    suspend fun getInCartProductIds(): Flow<List<Int>>

    suspend fun addToCart(inCartProduct: InCartProduct)

    suspend fun removeFromCart(inCartProduct: InCartProduct)

    suspend fun getInCartProductsFullDetail(): Flow<List<InCartProduct>>

    suspend fun updateProductQuantity(inCartProduct: InCartProduct)
}
