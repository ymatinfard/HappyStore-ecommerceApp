package com.matin.products

import com.matin.happystore.core.model.ui.ProductsAndFilters
import com.matin.happystore.core.model.ui.UiFilter
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.redux.ApplicationState
import javax.inject.Inject

class ProductListUIStateGenerator @Inject constructor() {

    operator fun invoke(
        products: List<UiProduct>,
        filterInfo: ApplicationState.ProductFilterInfo,
    ): ProductsScreenUiState {

        if (products.isEmpty()) return ProductsScreenUiState.Loading

        val uiProducts =
            products.filter { uiProduct ->
                filterInfo.selectedFilter == null || uiProduct.product.category == filterInfo.selectedFilter?.value
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