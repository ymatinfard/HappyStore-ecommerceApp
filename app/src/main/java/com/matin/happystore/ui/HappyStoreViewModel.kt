package com.matin.happystore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.ui.redux.ApplicationState
import com.matin.happystore.ui.redux.Store
import com.matin.happystore.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HappyStoreViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val repository: HappyStoreRepository,
) :
    ViewModel() {

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            when (val result = repository.getProducts()) {
                is Result.Success -> {
                    store.update { applicationState ->
                        return@update applicationState.copy(products = result.data)
                    }
                }

                is Result.Error -> TODO()
            }
        }
    }

    fun updateFavoriteIds(id: Int) {
        viewModelScope.launch {
            store.update { appState ->
                val updateFavIds = if (appState.favoriteProductId.contains(id)) {
                    appState.favoriteProductId - id
                } else {
                    appState.favoriteProductId + id
                }
                appState.copy(favoriteProductId = updateFavIds)
            }
        }
    }

    fun updateProductExpand(id: Int) {
        viewModelScope.launch {
            store.update { appState ->
                val updatedExpandedProductIds = if (appState.expandedProductIds.contains(id)) {
                    appState.expandedProductIds - id
                } else {
                    appState.expandedProductIds + id
                }
                appState.copy(expandedProductIds = updatedExpandedProductIds)
            }
        }
    }
}