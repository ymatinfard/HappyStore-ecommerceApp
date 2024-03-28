package com.matin.happystore.ui.model

data class UiProductState(
    val products: List<UiProduct> = emptyList(),
    val filters: List<UiFilter> = emptyList(),
)

sealed interface ProductListScreenUiState {
    data object Loading : ProductListScreenUiState
    data class Success(val data: UiProductState) : ProductListScreenUiState
    data class Error(val error: Exception) : ProductListScreenUiState
}
