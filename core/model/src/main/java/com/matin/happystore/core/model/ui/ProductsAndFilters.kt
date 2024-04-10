package com.matin.happystore.core.model.ui

data class ProductsAndFilters(
    val products: List<UiProduct> = emptyList(),
    val filters: List<UiFilter> = emptyList(),
)
