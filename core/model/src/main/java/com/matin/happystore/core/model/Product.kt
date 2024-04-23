package com.matin.happystore.core.model

import java.math.BigDecimal

data class Product(
    val id: Int = 0,
    val title: String = "",
    val price: BigDecimal = BigDecimal.ZERO,
    val category: String = "",
    val description: String = "",
    val image: String = "",
    val rating: Rating = Rating(),
) {
    data class Rating(
        val rate: Float = 0f,
        val count: Int = 0,
    )
}

fun List<Product>.generateCategory() = this.groupBy { it.category }.map { entry ->
    Filter(entry.key, "${entry.key} (${entry.value.size})")
}.toSet()

data class InCartProduct(
    val product: Product,
    val quantity: Int = 1
)