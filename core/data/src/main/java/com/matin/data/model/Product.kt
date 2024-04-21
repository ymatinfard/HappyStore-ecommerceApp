package com.matin.data.model

import com.matin.data.orZero
import com.matin.happystore.core.database.model.InCartProductEntity
import com.matin.happystore.core.database.model.InCartProductFullEntity
import com.matin.happystore.core.database.model.ProductEntity
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.network.model.NetworkProduct
import java.math.BigDecimal
import java.math.RoundingMode

fun ProductEntity.toDomain(): Product =
    Product(
        id,
        title,
        price,
        category.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        },
        description,
        image,
        Product.Rating(rating.rate.orZero(), rating.count.orZero()),
    )

fun NetworkProduct.toEntity() =
    ProductEntity(
        id,
        title.orEmpty(),
        BigDecimal(price ?: 0.0).setScale(2, RoundingMode.HALF_UP) ?: BigDecimal.ZERO,
        category.orEmpty().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        },
        description.orEmpty(),
        image.orEmpty(),
        ProductEntity.Rating(rating?.rate.orZero(), rating?.count.orZero()),
    )

fun InCartProductFullEntity.toDomain(): InCartProduct = InCartProduct(product = product.toDomain(), quantity = inCartProduct.quantity)

fun InCartProduct.toEntity() = InCartProductEntity(product.id, quantity)
