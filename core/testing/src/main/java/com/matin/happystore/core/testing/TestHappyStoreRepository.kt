package com.matin.happystore.core.testing

import com.matin.data.HappyStoreRepository
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.model.Product
import java.math.BigDecimal

class TestHappyStoreRepository : HappyStoreRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        val productsTest = listOf(
            Product(
                123,
                "title1",
                BigDecimal("23.3"),
                "Jewerly",
                "description1",
                "http://example.png",
                Product.Rating(3.4f, 1000)
            ),
            Product(
                124,
                "title2",
                BigDecimal("24.4"),
                "Jewerly",
                "description2",
                "http://example.png",
                Product.Rating(4.4f, 2000)
            )
        )

        return Result.Success(data = productsTest)
    }

    override suspend fun getSingleProduct(id: Int): Result<Product> {
        TODO()
    }
}