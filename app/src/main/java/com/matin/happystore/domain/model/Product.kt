package com.matin.happystore.domain.model

import java.math.BigDecimal

data class Product(
    val id: Int,
    val title: String,
    val price: BigDecimal,
    val category: String,
    val description: String,
    val image: String,
    val rating: Rating,
) {
    data class Rating (
        val rate: Float,
        val count: Int
    )
}
