package com.matin.products.stateupdater

import com.matin.happystore.core.redux.ApplicationState
import javax.inject.Inject

class ProductFavoriteUpdater @Inject constructor() {

    operator fun invoke(id: Int, appState: ApplicationState): ApplicationState {
        val updateFavIds = if (appState.favoriteProductId.contains(id)) {
            appState.favoriteProductId - id
        } else {
            appState.favoriteProductId + id
        }
        return appState.copy(favoriteProductId = updateFavIds)
    }
}