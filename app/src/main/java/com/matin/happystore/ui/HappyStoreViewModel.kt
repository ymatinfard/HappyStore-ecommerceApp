package com.matin.happystore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.model.Product
import com.matin.happystore.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HappyStoreViewModel @Inject constructor(private val repository: HappyStoreRepository) :
    ViewModel() {

    private val _products = MutableStateFlow<ProductUIState>(ProductUIState.Loading)
    val products: StateFlow<ProductUIState> = _products.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            when (val result = repository.getProducts()) {
                is Result.Success -> _products.value = ProductUIState.Success(result.data)
                is Result.Error -> _products.value = ProductUIState.Error(result.exception)
            }
        }
    }
}

sealed interface ProductUIState {
    data object Loading : ProductUIState
    data class Success(val data: List<Product>) : ProductUIState
    data class Error(val exception: Throwable) : ProductUIState
}