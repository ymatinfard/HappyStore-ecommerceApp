package com.matin.happystore.ui

import com.matin.happystore.ui.model.ProductsAndFilters
import com.matin.happystore.ui.model.UiFilter
import com.matin.happystore.ui.model.UiProduct
import com.matin.happystore.ui.redux.ApplicationState.ProductFilterInfo
import javax.inject.Inject

class ProductListUIStateGenerator @Inject constructor() {

    operator fun invoke(
        products: List<UiProduct>,
        filterInfo: ProductFilterInfo,
    ): ProductsScreenUiState {

        if (products.isEmpty()) return ProductsScreenUiState.Loading

        val uiProducts =
            products.filter { uiProduct ->
                filterInfo.selectedFilter == null ||
                        uiProduct.product.category == filterInfo.selectedFilter.value
            }

        val filters = filterInfo.filters.map { filter ->
            UiFilter(filter, filter == filterInfo.selectedFilter)
        }

        val productsAndFilters = ProductsAndFilters(
            uiProducts,
            filters
        )

        return ProductsScreenUiState.Success(productsAndFilters)
    }
}