package com.matin.happystore.core.ui

import com.matin.happystore.core.redux.ApplicationState
import javax.inject.Inject

class ProductInCartItemUpdater @Inject constructor() {

    operator fun invoke(id: Int, appState: ApplicationState): ApplicationState {
        val currentInCartProductIds = appState.inCartProductIds
        val currentInCartProductQuantity = appState.inCartProductQuantity
        val newInCartProducts: Set<Int>
        val updatedInCartProductQuantity: Map<Int, Int>

        if (currentInCartProductIds.contains(id)) {
            newInCartProducts = currentInCartProductIds - id
            // Reset quantity value if deleted from cart
            updatedInCartProductQuantity = currentInCartProductQuantity + (id to 1)
        } else {
            newInCartProducts = currentInCartProductIds + id
            updatedInCartProductQuantity = currentInCartProductQuantity
        }

        return appState.copy(
            inCartProductIds = newInCartProducts,
            inCartProductQuantity = updatedInCartProductQuantity
        )
    }
}