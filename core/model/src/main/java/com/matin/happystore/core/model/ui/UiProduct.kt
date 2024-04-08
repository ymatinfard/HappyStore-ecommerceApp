package com.matin.happystore.core.model.ui

import com.matin.happystore.domain.model.Product

data class UiProduct (
    val product: Product,
    val isFavorite: Boolean,
    val isExpended: Boolean,
    val isInCart: Boolean,
    val inCartQuantity: Int,
)