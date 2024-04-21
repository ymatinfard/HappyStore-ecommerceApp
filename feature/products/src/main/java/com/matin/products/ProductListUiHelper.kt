package com.matin.products

import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.model.generateCategory
import com.matin.happystore.core.model.ui.UiFilter
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.model.ui.UiProductsAndFilters
import javax.inject.Inject

class ProductListUiHelper
    @Inject
    constructor() {
        fun generateUiProductsAndFilters(
            allProducts: List<Product>,
            uiStateValue: ProductsScreenUiState,
            inCartProductIds: List<Int>,
            selectedFilter: Filter?,
        ): UiProductsAndFilters {
            val uiProducts =
                mapToUiProduct(
                    allProducts,
                    uiStateValue.favoriteProductId,
                    uiStateValue.expandedProductIds,
                    inCartProductIds,
                )

            val productFilterInfo =
                ProductFilterInfo(
                    filters = allProducts.generateCategory(),
                    selectedFilter = selectedFilter,
                )

            return mapToUiProductsAndFilters(uiProducts, productFilterInfo)
        }

        private fun mapToUiProduct(
            allProducts: List<Product>,
            favoriteProductIds: Set<Int>,
            expandedProductIds: Set<Int>,
            inCartProductIds: List<Int>,
        ) = allProducts.map { product ->
            UiProduct(
                product,
                favoriteProductIds.contains(product.id),
                expandedProductIds.contains(product.id),
                inCartProductIds.contains(product.id),
            )
        }

        private fun mapToUiProductsAndFilters(
            products: List<UiProduct>,
            filterInfo: ProductFilterInfo,
        ): UiProductsAndFilters {
            val uiProducts =
                products.filter { uiProduct ->
                    filterInfo.selectedFilter == null || uiProduct.product.category == filterInfo.selectedFilter.value
                }

            val uiFilters = mapToUiFilters(filterInfo)

            return UiProductsAndFilters(
                uiProducts,
                uiFilters,
            )
        }

        private fun mapToUiFilters(filterInfo: ProductFilterInfo) =
            filterInfo.filters.map { filter ->
                UiFilter(filter, filter == filterInfo.selectedFilter)
            }

        fun updateProductExpansionState(
            id: Int,
            state: ProductsScreenUiState,
        ): ProductsScreenUiState {
            val updatedExpandedProductIds = updateIds(id, state.expandedProductIds)
            return state.copy(expandedProductIds = updatedExpandedProductIds)
        }

        fun updateProductFavoriteState(
            id: Int,
            state: ProductsScreenUiState,
        ): ProductsScreenUiState {
            val updateFavIds = updateIds(id, state.favoriteProductId)
            return state.copy(favoriteProductId = updateFavIds)
        }

        private fun updateIds(
            id: Int,
            ids: Set<Int>,
        ) = if (ids.contains(id)) ids - id else ids + id
    }
