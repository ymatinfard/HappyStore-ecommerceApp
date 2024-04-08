package com.matin.products.stateupdater

import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.redux.ApplicationState
import javax.inject.Inject

class ProductFilterSelectionUpdater @Inject constructor() {
    operator fun invoke(filter: Filter, appState: ApplicationState): ApplicationState {
        val currentFilter = appState.productFilterInfo.selectedFilter
        return appState.copy(productFilterInfo = appState.productFilterInfo.copy(selectedFilter = if (currentFilter != filter) filter else null))
    }
}