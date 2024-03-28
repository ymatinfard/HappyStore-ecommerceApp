package com.matin.happystore.ui.model

data class UiProductState(
    val products: List<UiProduct> = emptyList(),
    val filters: List<UiFilter> = emptyList()
)
