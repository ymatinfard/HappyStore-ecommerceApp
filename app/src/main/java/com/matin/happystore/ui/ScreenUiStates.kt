package com.matin.happystore.ui

import com.matin.happystore.ui.model.ProductsAndFilters
import com.matin.happystore.ui.model.UiProduct

sealed interface ProductsScreenUiState {
    data object Loading : ProductsScreenUiState
    data class Success(val data: ProductsAndFilters) : ProductsScreenUiState
    data class Error(val error: Exception) : ProductsScreenUiState
}

sealed interface CartScreenUiState {
    data object Empty: CartScreenUiState
    data class Data(val data: List<UiProduct>): CartScreenUiState
}