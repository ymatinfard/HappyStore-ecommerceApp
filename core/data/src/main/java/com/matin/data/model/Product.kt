package com.matin.data.model

import com.matin.data.orZero
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.network.model.NetworkProduct
import java.math.BigDecimal
import java.math.RoundingMode

fun NetworkProduct.toDomain(): Product =
    Product(
        id,
        title.orEmpty(),
        BigDecimal(price ?: 0.0).setScale(2, RoundingMode.HALF_UP) ?: BigDecimal.ZERO,
        category.orEmpty().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        },
        description.orEmpty(),
        image.orEmpty(),
        Product.Rating(rating?.rate.orZero(), rating?.count.orZero())
    )
