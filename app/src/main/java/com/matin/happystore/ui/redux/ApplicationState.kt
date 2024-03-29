package com.matin.happystore.ui.redux

import com.matin.happystore.domain.model.Filter
import com.matin.happystore.domain.model.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val favoriteProductId: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val inCartProductIds: Set<Int> = emptySet(),
) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null,
    )
}
