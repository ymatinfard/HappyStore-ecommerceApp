package com.matin.data

import com.matin.happystore.core.common.Result
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import kotlinx.coroutines.flow.Flow

interface HappyStoreRepository {
    fun getProducts(): Flow<List<Product>>

    suspend fun sync()

    suspend fun getSingleProduct(id: Int): Result<Product>

    fun getInCartProductIds(): Flow<List<Int>>

    suspend fun addToCart(inCartProduct: InCartProduct)

    suspend fun removeFromCart(inCartProduct: InCartProduct)

    fun getInCartProductsFullDetail(): Flow<List<InCartProduct>>

    suspend fun updateProductQuantity(inCartProduct: InCartProduct)

    fun getSearchSuggestion(query: String): Flow<List<String>>
}
