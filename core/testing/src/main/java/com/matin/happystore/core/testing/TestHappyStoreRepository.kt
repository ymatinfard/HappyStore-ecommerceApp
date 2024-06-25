package com.matin.happystore.core.testing

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class TestHappyStoreRepository : HappyStoreRepository {
    private val productList =
        listOf(
            Product(
                123,
                "title1",
                BigDecimal("23.3"),
                "Jewerly",
                "description1",
                "http://example.png",
                Product.Rating(3.4f, 1000),
            ),
            Product(
                124,
                "title2",
                BigDecimal("24.4"),
                "Jewerly",
                "description2",
                "http://example.png",
                Product.Rating(4.4f, 2000),
            ),
        )

    private val inCartProductsStateFlow =
        MutableStateFlow(
            listOf(
                InCartProduct(
                    Product(
                        123,
                        "title1",
                        BigDecimal("23.3"),
                        "Jewerly",
                        "description1",
                        "http://example.png",
                        Product.Rating(3.4f, 1000),
                    ),
                    quantity = 1,
                ),
            ),
        )

    private val productStateFlow = MutableStateFlow(productList)

    override fun getProducts() = productStateFlow

    override suspend fun sync() {
        // TODO("Not yet implemented")
    }

    override suspend fun getSingleProduct(id: Int): Result<Product> {
        TODO("Not yet implemented")
    }

    override fun getInCartProductIds(): Flow<List<Int>> =
        inCartProductsStateFlow.map { inCartProducts ->
            inCartProducts.map { it.product.id }
        }

    override suspend fun addToCart(inCartProduct: InCartProduct) {
        inCartProductsStateFlow.update { inCartProducts ->
            inCartProducts.toMutableList().apply { add(inCartProduct) }.toList()
        }
    }

    override suspend fun removeFromCart(inCartProduct: InCartProduct) {
        inCartProductsStateFlow.update { inCartProducts ->
            inCartProducts.toMutableList().apply { remove(inCartProduct) }
        }
    }

    override  fun getInCartProductsFullDetail(): Flow<List<InCartProduct>> = inCartProductsStateFlow

    override suspend fun updateProductQuantity(inCartProduct: InCartProduct) {
        inCartProductsStateFlow.update {
            val mutableList = it.toMutableList()
            val index = mutableList.indexOfFirst { it.product.id == inCartProduct.product.id }
            if (index != -1) {
                mutableList[index] = inCartProduct
            }
            mutableList
        }
    }

    override fun getSearchSuggestion(query: String): Flow<List<String>> {
        TODO("Not yet implemented")
    }
}
