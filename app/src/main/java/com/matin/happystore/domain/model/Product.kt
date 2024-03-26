package com.matin.happystore.domain.model

import java.math.BigDecimal

class Product(
    val id: Int,
    val title: String,
    val price: BigDecimal,
    val category: String,
    val description: String,
    val image: String,
)
