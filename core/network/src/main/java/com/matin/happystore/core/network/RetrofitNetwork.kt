package com.matin.happystore.core.network

import com.matin.happystore.core.network.model.NetworkProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitNetwork @Inject constructor(private val api: HappyStoreApi) : HappyStoreDataSource {
    override suspend fun getAllProducts(): List<NetworkProduct> = api.getAllProducts()
    override suspend fun getSingleProduct(id: Int): Flow<NetworkProduct> = api.getSingleProduct(id)
}