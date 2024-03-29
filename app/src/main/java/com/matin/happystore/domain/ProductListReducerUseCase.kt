package com.matin.happystore.domain

import com.matin.happystore.ui.model.UiProduct
import com.matin.happystore.ui.redux.ApplicationState
import com.matin.happystore.ui.redux.Store
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductListReducerUseCase @Inject constructor(private val store: Store<ApplicationState>) {
    fun reduce(): Flow<List<UiProduct>> {
        return combine(
            store.state.map { it.products },
            store.state.map { it.favoriteProductId },
            store.state.map { it.expandedProductIds },
            store.state.map { it.inCartProductIds }) { products, favorites, expandeds, inCartProducts ->

            products.map { product ->
                UiProduct(
                    product,
                    favorites.contains(product.id),
                    expandeds.contains(product.id),
                    inCartProducts.contains(product.id)
                )
            }
        }
    }
}