package com.matin.data

import com.matin.data.model.toDomain
import com.matin.data.model.toEntity
import com.matin.data.testdouble.TestCartDao
import com.matin.data.testdouble.TestHappyStoreApi
import com.matin.data.testdouble.TestProductsDao
import com.matin.happystore.core.database.model.ProductEntity
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.network.model.NetworkProduct
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class HappyStoreRepositoryTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())
    private val productsDao = TestProductsDao()
    private val happyStoreApi = TestHappyStoreApi()
    private val cartDao = TestCartDao()

    private lateinit var repository: HappyStoreRepository

    @Before
    fun setup() {
        repository = HappyStoreRepositoryImpl(happyStoreApi, productsDao, cartDao)
    }

    @Test
    fun getProducts_returns_product_list_from_cache() =
        testScope.runTest {
            val resultList = repository.getProducts().first()
            assertEquals(resultList, productList)
        }

    @Test
    fun cached_products_synced_with_network_products() =
        testScope.runTest {
            val networkProductList = happyStoreApi.getAllProducts()

            repository.sync()

            val cachedProductList = repository.getProducts().first()

            assertEquals(
                networkProductList
                    .map(NetworkProduct::toEntity)
                    .map(ProductEntity::toDomain),
                cachedProductList,
            )
        }

    @Test
    fun addToCart_adds_product_into_cart() =
        testScope.runTest {
            repository.addToCart(InCartProduct(Product(124), 2))
            val inCartProductCount = repository.getInCartProductIds().first().size

            assertEquals(2, inCartProductCount)
        }

    @Test
    fun removeFromCart_removes_product_from_cart() =
        testScope.runTest {
            repository.removeFromCart(InCartProduct(Product(123), 1))
            val inCartProductCount = repository.getInCartProductIds().first().size

            assertEquals(0, inCartProductCount)
        }

    private val productList =
        listOf(
            Product(
                123,
                "title1",
                BigDecimal("23.3"),
                "Jewerly",
                "description1",
                "http://example.png",
                Product.Rating(3.4f, 1000),
            ),
            Product(
                124,
                "title2",
                BigDecimal("24.4"),
                "Jewerly",
                "description2",
                "http://example.png",
                Product.Rating(4.4f, 2000),
            ),
        )
}
