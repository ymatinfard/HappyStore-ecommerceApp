package com.matin.happystore.domain

import com.matin.happystore.domain.model.Filter
import com.matin.happystore.ui.redux.ApplicationState
import javax.inject.Inject

class ProductFilterSelectionUpdater @Inject constructor() {
    operator fun invoke(filter: Filter, appState: ApplicationState): ApplicationState {
        val currentFilter = appState.productFilterInfo.selectedFilter
        return appState.copy(productFilterInfo = appState.productFilterInfo.copy(selectedFilter = if (currentFilter != filter) filter else null))
    }
}