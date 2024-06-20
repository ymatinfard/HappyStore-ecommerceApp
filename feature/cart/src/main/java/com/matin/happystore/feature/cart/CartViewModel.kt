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
                                    inCartProducts = result.data,
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

    fun quantityChanged(inCartProduct: InCartProduct) {
        onInCartProductQuantityChange(inCartProduct)
    }

    private fun updateInCartProductQuantity(inCartProduct: InCartProduct) {
        viewModelScope.launch {
            updateInCartProductQuantityUseCase(inCartProduct)
        }
    }

    fun removeProduct(inCartProduct: InCartProduct) {
        viewModelScope.launch {
            removeProductFromCartUseCase(inCartProduct)
        }
    }

    fun intentToAction(intent: CartIntent) {
        when (intent) {
            is CartIntent.QuantityChanged -> quantityChanged(intent.product)
            is CartIntent.DeleteProduct -> removeProduct(intent.product)
        }
    }

    companion object {
        const val WAIT_TIME: Long = 200
    }
}

data class CartScreenUiState(
    val loadingState: DataLoadingState = DataLoadingState.Loading,
    val inCartProducts: List<InCartProduct> = emptyList(),
)

sealed interface CartIntent {
    data class QuantityChanged(val product: InCartProduct) : CartIntent
    data class DeleteProduct(val product: InCartProduct) : CartIntent
}
