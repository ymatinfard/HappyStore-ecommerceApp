package com.matin.happystore.core.model.ui

import com.matin.happystore.core.model.Product

data class UiInCartProduct (
    val product: Product?,
    val isFavorite: Boolean = false,
    val inCartQuantity: Int = 0,
)