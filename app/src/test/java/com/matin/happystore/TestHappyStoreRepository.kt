package com.matin.happystore

import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.model.Product
import com.matin.happystore.domain.util.Result
import java.math.BigDecimal

class TestHappyStoreRepository : HappyStoreRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        TODO()
    }

    override suspend fun getSingleProduct(id: Int): Result<Product> {
        TODO()
    }
}