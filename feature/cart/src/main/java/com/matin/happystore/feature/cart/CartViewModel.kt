package com.matin.happystore.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.common.asResource
import com.matin.happystore.core.common.debounce
import com.matin.happystore.core.domain.GetInCartProductFullDetailUseCase
import com.matin.happystore.core.domain.RemoveProductFromCartUseCase
import com.matin.happystore.core.domain.UpdateInCartProductQuantityUseCase
import com.matin.happystore.core.model.InCartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel
    @Inject
    constructor(
        private val getInCartProductFullDetailUseCase: GetInCartProductFullDetailUseCase,
        private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
        private val updateInCartProductQuantityUseCase: UpdateInCartProductQuantityUseCase,
    ) : ViewModel() {
        val cartScreenUiState = MutableStateFlow(CartScreenUiState())
        val onInCartProductQuantityChange: (InCartProduct) -> Unit =
            debounce(WAIT_TIME, viewModelScope, ::updateInCartProductQuantity)

        init {
            collectInCartProducts()
        }

        private fun collectInCartProducts() {
            viewModelScope.launch {
                getInCartProductFullDetailUseCase().asResource().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            cartScreenUiState.update { state ->
                                state.copy(
                                    products = result.data,
                                    loadingState = DataLoadingState.Loaded,
                                )
                            }
                        }

                        is Result.Error -> {
                            // Todo()
                        }
                    }
                }
            }
        }

        fun onQuantityChanged(inCartProduct: InCartProduct) {
            onInCartProductQuantityChange(inCartProduct)
        }

        private fun updateInCartProductQuantity(inCartProduct: InCartProduct) {
            viewModelScope.launch {
                updateInCartProductQuantityUseCase(inCartProduct)
            }
        }

        fun removeItem(inCartProduct: InCartProduct) {
            viewModelScope.launch {
                removeProductFromCartUseCase(inCartProduct)
            }
        }

        fun updateFavoriteIds(id: Int) {
            viewModelScope.launch {
                // Todo()
            }
        }

        companion object {
            const val WAIT_TIME: Long = 500
        }
    }

data class CartScreenUiState(
    val loadingState: DataLoadingState = DataLoadingState.Loading,
    val products: List<InCartProduct> = emptyList(),
)
