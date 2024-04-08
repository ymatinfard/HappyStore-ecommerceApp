package com.matin.happystore.core.redux

import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.Product


data class ApplicationState(
    val products: List<Product> = emptyList(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val favoriteProductId: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val inCartProductIds: Set<Int> = emptySet(),
    val inCartProductQuantity: Map<Int, Int> = emptyMap(),
) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null,
    )
}
