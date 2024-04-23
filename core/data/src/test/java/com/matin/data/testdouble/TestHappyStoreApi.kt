package com.matin.data.testdouble

import com.matin.happystore.core.network.HappyStoreApi
import com.matin.happystore.core.network.model.NetworkProduct

class TestHappyStoreApi : HappyStoreApi {
    override suspend fun getAllProducts(): List<NetworkProduct> {
        return listOf(
            NetworkProduct(
                123,
                "title1",
                23.3,
                "Jewerly",
                "description1",
                "http://example.png",
                NetworkProduct.Rating(3.4f, 1000),
            ),
            NetworkProduct(
                124,
                "title2",
                24.4,
                "Jewerly",
                "description2",
                "http://example.png",
                NetworkProduct.Rating(4.4f, 2000),
            ),
        )
    }

    override suspend fun getSingleProduct(id: Int): NetworkProduct {
        TODO("Not yet implemented")
    }
}
