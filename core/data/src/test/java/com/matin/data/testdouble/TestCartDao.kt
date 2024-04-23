package com.matin.data.testdouble

import com.matin.happystore.core.database.model.CartDao
import com.matin.happystore.core.database.model.InCartProductEntity
import com.matin.happystore.core.database.model.InCartProductFullEntity
import com.matin.happystore.core.database.model.ProductEntity
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class TestCartDao : CartDao {
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

    val inCartProduct =
        InCartProduct(
            Product(
                123,
                "title1",
                BigDecimal("23.3"),
                "Jewerly",
                "description1",
                "http://example.png",
                Product.Rating(3.4f, 1000),
            ),
            quantity = 1,
        )

    private val inCartProductStateFlow = MutableStateFlow(listOf(inCartProduct))

    override fun getInCartProductIds(): Flow<List<Int>> =
        inCartProductStateFlow.map { inCartProducts ->
            inCartProducts.map { it.product.id }
        }

    override suspend fun addToCart(inCartProductEntity: InCartProductEntity) {
        inCartProductStateFlow.update { inCartProducts ->
            val intendedProduct = productList.first { it.id == inCartProductEntity.productId }
            inCartProducts.toMutableList()
                .apply { add(InCartProduct(intendedProduct, inCartProductEntity.quantity)) }
        }
    }

    override suspend fun removeFromCart(inCartProductEntity: InCartProductEntity) {
        inCartProductStateFlow.update { inCArtProduct ->
            inCArtProduct.toMutableList().apply { remove(inCartProduct) }
        }
    }

    override fun getInCartProductsFullDetail(): Flow<List<InCartProductFullEntity>> =
        inCartProductStateFlow.map { inCartProducts ->
            inCartProducts.map {
                InCartProductFullEntity(
                    inCartProduct =
                        InCartProductEntity(
                            it.product.id,
                            it.quantity,
                        ),
                    product = it.product.toEntity(),
                )
            }
        }

    override suspend fun updateQuantity(inCartProductEntity: InCartProductEntity) {
        inCartProductStateFlow.update { inCartProducts ->
            inCartProducts.map {
                if (it.product.id == inCartProductEntity.productId) {
                    it.copy(
                        quantity = inCartProductEntity.quantity,
                    )
                } else {
                    it
                }
            }
        }
    }

    private fun Product.toEntity() =
        ProductEntity(
            id,
            title,
            price,
            category,
            description,
            image,
            ProductEntity.Rating(rating.rate, rating.count),
        )
}
