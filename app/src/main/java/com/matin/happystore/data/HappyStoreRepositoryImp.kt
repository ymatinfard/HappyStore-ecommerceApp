package com.matin.happystore.data

import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.model.Product
import com.matin.happystore.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HappyStoreRepositoryImp @Inject constructor(private val api: HappyStoreApi) :
    HappyStoreRepository {
    override suspend fun getProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val products = api.getAllProducts().map(ProductMapper::toDomain)
            Result.Success(products)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getSingleProduct(id: Int): Result<Product> =
        withContext(Dispatchers.IO) {
            try {
                val product = ProductMapper.toDomain(api.getSingleProduct(id))
                Result.Success(product)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}