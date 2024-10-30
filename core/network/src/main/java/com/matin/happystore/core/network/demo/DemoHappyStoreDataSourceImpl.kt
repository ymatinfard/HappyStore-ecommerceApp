package com.matin.happystore.core.network.demo

import com.matin.happystore.core.network.HappyStoreDataSource
import com.matin.happystore.core.network.model.NetworkProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class DemoHappyStoreDataSourceImpl @Inject constructor(
    private val assetManager: DemoAssetManager,
    private val networkJson: Json,
) : HappyStoreDataSource {
    override suspend fun getAllProducts(): List<NetworkProduct> {
        return assetManager.open(PRODUCTS_ASSET).use(networkJson::decodeFromStream)
    }

    override suspend fun getSingleProduct(id: Int): Flow<NetworkProduct> {
        TODO("Not yet implemented")
    }

    companion object {
        const val PRODUCTS_ASSET = "products.json"
    }
}