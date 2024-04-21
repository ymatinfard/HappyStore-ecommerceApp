package com.matin.products

import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.ui.UiProductsAndFilters

data class ProductsScreenUiState(
    val uiProductsAndFilters: UiProductsAndFilters = UiProductsAndFilters(),
    val favoriteProductId: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val inCartProductIds: Set<Int> = emptySet(),
    val selectedFilter: Filter? = null,
    val loadingState: DataLoadingState = DataLoadingState.Loading,
)

data class ProductFilterInfo(
    val filters: Set<Filter> = emptySet(),
    val selectedFilter: Filter? = null,
)
