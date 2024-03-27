package com.matin.happystore.ui.redux

import com.matin.happystore.domain.model.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val favoriteProductId: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
)
