package com.matin.happystore

import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.model.Product
import com.matin.happystore.domain.util.Result
import java.math.BigDecimal

class TestHappyStoreRepository : HappyStoreRepository {

    override suspend fun getProducts(): Result<List<Product>> {

        val productTest = Product(
            123,
            "title",
            BigDecimal.ZERO,
            "categoryTest",
            "descriptionTest",
            "http://image.test.jpg"
        )

        val products = listOf(productTest)

        return Result.Success(products)
    }

    override suspend fun getSingleProduct(id: Int): Result<Product> {
        TODO()
    }
}