package com.matin.happystore.utils.mapper

import com.matin.happystore.data.model.ProductEntity
import com.matin.happystore.domain.model.Product
import java.math.BigDecimal
import java.math.RoundingMode

class ProductMapper {
    companion object {
        fun toDomain(pn: ProductEntity): Product = Product(
            pn.id,
            pn.title.orEmpty(),
            BigDecimal(pn.price ?: 0.0).setScale(2, RoundingMode.HALF_UP) ?: BigDecimal.ZERO,
            pn.category.orEmpty().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            },
            pn.description.orEmpty(),
            pn.image.orEmpty()
        )
    }
}