package com.matin.happystore.ui

import com.matin.happystore.domain.model.Product

data class UiProduct (
    val product: Product,
    val isFavorite: Boolean,
)