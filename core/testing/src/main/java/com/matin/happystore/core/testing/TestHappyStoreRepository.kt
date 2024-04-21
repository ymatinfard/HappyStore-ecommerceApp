package com.matin.happystore.core.testing

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val productStateFlow = MutableStateFlow(productList)

    override suspend fun getProducts() = productStateFlow

    override suspend fun sync() {
        // TODO("Not yet implemented")
    }

    override suspend fun getSingleProduct(id: Int): Result<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getInCartProductIds(): Flow<List<Int>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToCart(inCartProduct: InCartProduct) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromCart(inCartProduct: InCartProduct) {
        TODO("Not yet implemented")
    }

    override suspend fun getInCartProductsFullDetail(): Flow<List<InCartProduct>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductQuantity(inCartProduct: InCartProduct) {
        TODO("Not yet implemented")
    }
}
