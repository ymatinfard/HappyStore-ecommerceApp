package com.matin.data.model

import com.matin.happystore.core.database.model.ProductEntity
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.network.model.NetworkProduct
import org.junit.Assert.*

import org.junit.Test
import java.math.BigDecimal

class ProductKtTest {

    @Test
    fun entity_mapped_to_domain_model() {
        val mappedToDomain = ProductEntity(
            123,
            "title1",
            BigDecimal("23.3"),
            "Jewerly",
            "description1",
            "http://example.png",
            ProductEntity.Rating(3.4f, 1000)
        ).toDomain()

        val domainProduct = Product(
            123,
            "title1",
            BigDecimal("23.3"),
            "Jewerly",
            "description1",
            "http://example.png",
            Product.Rating(3.4f, 1000)
        )

        assertEquals(domainProduct, mappedToDomain)
    }

    @Test
    fun network_model_mapped_to_entity() {
        val mappedToEntity = NetworkProduct(
            123,
            "title1",
            23.3,
            "Jewerly",
            "description1",
            "http://example.png",
            NetworkProduct.Rating(3.4f, 1000)
        ).toEntity()

        val productEntity = ProductEntity(
            123,
            "title1",
            BigDecimal("23.3"),
            "Jewerly",
            "description1",
            "http://example.png",
            com.matin.happystore.core.database.model.ProductEntity.Rating(3.4f, 1000)
        )

        assertEquals(productEntity, mappedToEntity)
    }


}