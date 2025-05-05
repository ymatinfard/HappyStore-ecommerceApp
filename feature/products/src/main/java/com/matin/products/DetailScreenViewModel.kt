package com.matin.products

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.common.Constants
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.common.asResult
import com.matin.happystore.core.domain.GetSingleProductUseCase
import com.matin.happystore.core.model.ui.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getSingleProductUseCase: GetSingleProductUseCase,
    saveStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val productId = saveStateHandle[Constants.PRODUCT_ID] ?: DEFAULT_PRODUCT_ID

    private val _uiState = MutableStateFlow(DetailScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProductById(productId)
    }

    private fun loadProductById(id: Int) {
        viewModelScope.launch {
            getSingleProductUseCase(id).asResult().collect { item ->
                when (item) {
                    is Result.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = it.error) }
                    }

                    is Result.Success -> {
                        _uiState.update { it.copy(product = item.data, isLoading = false, error = null) }
                    }

                    is Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                }
            }
        }
    }

    companion object {
        const val DEFAULT_PRODUCT_ID = 0
    }
}

data class DetailScreenUiState(
    val product: UiProduct = UiProduct.empty(),
    val isLoading: Boolean = false,
    val error: String? = null
)