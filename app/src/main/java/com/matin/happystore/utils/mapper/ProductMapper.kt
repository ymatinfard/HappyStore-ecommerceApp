package com.matin.happystore.utils.mapper

import com.matin.happystore.data.model.ProductEntity
import com.matin.happystore.domain.model.Product
import com.matin.happystore.utils.orZero
import java.math.BigDecimal
import java.math.RoundingMode

class ProductMapper {
    companion object {
        fun toDomain(pe: ProductEntity): Product = Product(
            pe.id,
            pe.title.orEmpty(),
            BigDecimal(pe.price ?: 0.0).setScale(2, RoundingMode.HALF_UP) ?: BigDecimal.ZERO,
            pe.category.orEmpty().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            },
            pe.description.orEmpty(),
            pe.image.orEmpty(),
            Product.Rating(pe.rating?.rate.orZero(), pe.rating?.count.orZero())
        )
    }
}