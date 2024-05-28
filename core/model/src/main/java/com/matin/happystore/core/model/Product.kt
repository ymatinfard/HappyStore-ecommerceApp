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

fun List<Product>.generateCategory(): Set<Filter> {
    val filters = this.groupingBy { it.category }
        .eachCount()
        .mapTo(mutableSetOf()) { (category, count) ->
            Filter(category, "$category ($count)")
        }.toMutableList()

    filters.add(0, Filter(SHOW_ALL, SHOW_ALL))
    return filters.toSet()
}

data class InCartProduct(
    val product: Product,
    val quantity: Int = 1
)

const val SHOW_ALL = "All"