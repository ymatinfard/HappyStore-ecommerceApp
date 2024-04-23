package com.matin.happystore.core.model.ui

data class UiProductsAndFilters(
    val products: List<UiProduct> = emptyList(),
    val filters: List<UiFilter> = emptyList(),
)
