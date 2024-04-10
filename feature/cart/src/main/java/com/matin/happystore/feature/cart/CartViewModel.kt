package com.matin.happystore.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.redux.ApplicationState
import com.matin.happystore.core.redux.Store
import com.matin.happystore.core.ui.ProductFavoriteUpdater
import com.matin.happystore.core.ui.ProductInCartItemUpdater
import com.matin.happystore.core.ui.ProductListReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productListReducer: ProductListReducer,
    private val productInCartItemUpdater: ProductInCartItemUpdater,
    private val productFavoriteUpdater: ProductFavoriteUpdater,
    val store: Store<ApplicationState>,
) : ViewModel() {

    val inCartProductsUiState = productListReducer.reduce().map { uiProducts ->
        val inCartProducts = uiProducts.filter { it.isInCart }

        if (inCartProducts.isNotEmpty()) {
            CartScreenUiState.Data(inCartProducts)
        } else {
            CartScreenUiState.Empty
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CartScreenUiState.Empty
    )

    fun updateProductInCartQuantity(productId: Int, newQuantity: Int) {
        viewModelScope.launch {
            store.update { appState ->
                val currentQuantityMap = appState.inCartProductQuantity
                appState.copy(inCartProductQuantity = currentQuantityMap + (productId to newQuantity))
            }
        }
    }

    fun updateInCartItemIds(id: Int) {
        viewModelScope.launch {
            store.update { appState ->
                productInCartItemUpdater(id, appState)
            }
        }
    }

    fun updateFavoriteIds(id: Int) {
        viewModelScope.launch {
            store.update { appState ->
                productFavoriteUpdater(id, appState)
            }
        }
    }
}

interface CartScreenUiState {
    data class Data(val data: List<UiProduct>) : CartScreenUiState
    data object Empty : CartScreenUiState
}