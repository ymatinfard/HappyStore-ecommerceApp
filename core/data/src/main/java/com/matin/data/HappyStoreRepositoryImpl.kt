package com.matin.data

import com.matin.data.model.toDomain
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.network.HappyStoreApi
import com.matin.happystore.core.network.model.NetworkProduct
import com.matin.happystore.core.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HappyStoreRepositoryImpl @Inject constructor(private val api: HappyStoreApi) :
    HappyStoreRepository {
    override suspend fun getProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val products = api.getAllProducts().map(NetworkProduct::toDomain)
            Result.Success(products)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getSingleProduct(id: Int): Result<Product> =
        withContext(Dispatchers.IO) {
            try {
                val product = api.getSingleProduct(id).toDomain()
                Result.Success(product)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}