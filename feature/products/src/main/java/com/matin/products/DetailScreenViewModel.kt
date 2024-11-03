package com.matin.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.domain.GetSingleProductUseCase
import com.matin.happystore.core.model.ui.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.common.asResult
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val getSingleProductUseCase: GetSingleProductUseCase) :
    ViewModel() {
    val detailScreenUiState = MutableStateFlow(DetailScreenUiState())

    fun getItem(id: Int) {
        viewModelScope.launch {
            getSingleProductUseCase(id).asResult().collect { item ->
                when (item) {
                    is Result.Error -> {}
                    is Result.Success -> {
                        detailScreenUiState.update { it.copy(product = item.data) }
                    }

                    is Result.Loading -> {}
                }
            }
        }
    }
}

data class DetailScreenUiState(val product: UiProduct? = null)