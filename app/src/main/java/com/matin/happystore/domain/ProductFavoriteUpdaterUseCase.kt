package com.matin.happystore.domain

import com.matin.happystore.ui.redux.ApplicationState
import javax.inject.Inject

class ProductFavoriteUpdaterUseCase @Inject constructor() {

    operator fun invoke(id: Int, appState: ApplicationState): ApplicationState {
        val updateFavIds = if (appState.favoriteProductId.contains(id)) {
            appState.favoriteProductId - id
        } else {
            appState.favoriteProductId + id
        }
        return appState.copy(favoriteProductId = updateFavIds)
    }
}