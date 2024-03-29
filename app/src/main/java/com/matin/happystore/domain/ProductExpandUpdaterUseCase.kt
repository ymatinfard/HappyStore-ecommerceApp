package com.matin.happystore.domain

import com.matin.happystore.ui.redux.ApplicationState
import javax.inject.Inject

class ProductExpandUpdaterUseCase @Inject constructor() {

    operator fun invoke(id: Int, appState: ApplicationState): ApplicationState {
        val updatedExpandedProductIds = if (appState.expandedProductIds.contains(id)) {
            appState.expandedProductIds - id
        } else {
            appState.expandedProductIds + id
        }
        return appState.copy(expandedProductIds = updatedExpandedProductIds)
    }
}