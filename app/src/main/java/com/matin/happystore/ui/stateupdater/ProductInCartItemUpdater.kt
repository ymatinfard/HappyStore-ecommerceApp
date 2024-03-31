package com.matin.happystore.ui.stateupdater

import com.matin.happystore.ui.redux.ApplicationState
import javax.inject.Inject

class ProductInCartItemUpdater @Inject constructor() {

    operator fun invoke(id: Int, appState: ApplicationState): ApplicationState {
        val currentInCartProducts = appState.inCartProductIds
        return appState.copy(
            inCartProductIds = if (currentInCartProducts.contains(id)) {
                currentInCartProducts - id
            } else {
                currentInCartProducts + id
            }
        )
    }
}