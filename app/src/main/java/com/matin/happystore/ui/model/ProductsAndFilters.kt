package com.matin.happystore.ui.model

data class ProductsAndFilters(
    val products: List<UiProduct> = emptyList(),
    val filters: List<UiFilter> = emptyList(),
)
