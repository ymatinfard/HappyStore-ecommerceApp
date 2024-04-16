package com.matin.data

import com.matin.happystore.core.database.model.ProductEntity
import com.matin.happystore.core.database.model.di.ProductsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class TestProductsDao : ProductsDao {
    private val productList = listOf(
        ProductEntity(
            123,
            "title1",
            BigDecimal("23.3"),
            "Jewerly",
            "description1",
            "http://example.png",
            ProductEntity.Rating(3.4f, 1000)
        ),
        ProductEntity(
            124,
            "title2",
            BigDecimal("24.4"),
            "Jewerly",
            "description2",
            "http://example.png",
            ProductEntity.Rating(4.4f, 2000)
        )
    )

    private val productEntitiesStateFlow = MutableStateFlow(productList)

    override fun upsertProducts(newList: List<ProductEntity>) {
        productEntitiesStateFlow.update { oldList ->
            (newList + oldList).distinctBy(ProductEntity::id)
        }
    }

    override fun getProducts(): Flow<List<ProductEntity>> = productEntitiesStateFlow

    override fun deleteProducts(ids: List<Int>) {
        productEntitiesStateFlow.update { list ->
            list.filter { it.id !in ids }
        }
    }
}