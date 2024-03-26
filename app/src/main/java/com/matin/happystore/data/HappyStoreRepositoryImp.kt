package com.matin.happystore.data

import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.Product
import javax.inject.Inject

class HappyStoreRepositoryImp @Inject constructor(api: HappyStoreApi): HappyStoreRepository {
    override suspend fun getProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingleProduct(id: Int): Product {
        TODO("Not yet implemented")
    }
}