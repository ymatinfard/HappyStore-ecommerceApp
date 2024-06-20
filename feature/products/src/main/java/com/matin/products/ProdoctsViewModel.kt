package com.matin.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.common.asResource
import com.matin.happystore.core.domain.AddProductToCartUseCase
import com.matin.happystore.core.domain.GetInCartProductIdsUseCase
import com.matin.happystore.core.domain.GetProductsUseCase
import com.matin.happystore.core.domain.RemoveProductFromCartUseCase
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.Product
import com.matin.products.helper.ProductListUiHelper
import com.matin.products.model.ProductsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel
@Inject
constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getInCartProductIdsUseCase: GetInCartProductIdsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val productsUiStateHelper: ProductListUiHelper,
) : ViewModel() {
    val productsScreenUiState = MutableStateFlow(ProductsScreenUiState())

    init {
        collectProducts()
    }

    private fun collectProducts() {
        viewModelScope.launch {
            productsScreenUiState.update { it.copy(loadingState = DataLoadingState.Loading) }

            combine(
                getProductsUseCase(),
                getInCartProductIdsUseCase(),
                productsScreenUiState.map { it.selectedFilter },
            ) { allProducts, inCartProductIds, selectedFilter ->

                productsUiStateHelper.generateUiProductsAndFilters(
                    allProducts,
                    productsScreenUiState.value,
                    inCartProductIds,
                    selectedFilter,
                )
            }.asResource().collect { uiStateResult ->
                val newUiState =
                    when (uiStateResult) {
                        is Result.Success -> {
                            productsScreenUiState.value.copy(
                                uiProductsAndFilters = uiStateResult.data,
                                loadingState = DataLoadingState.Loaded,
                            )
                        }

                        is Result.Error -> {
                            productsScreenUiState.value.copy(
                                loadingState = DataLoadingState.Error(uiStateResult.exception),
                            )
                        }
                    }

                productsScreenUiState.update { newUiState }
            }
        }
    }

    private fun updateFavoriteIds(id: Int) {
        viewModelScope.launch {
            productsScreenUiState.update { state ->
                productsUiStateHelper.updateProductFavoriteState(id, state)
            }
        }
    }

    private fun updateProductExpand(id: Int) {
        viewModelScope.launch {
            productsScreenUiState.update { state ->
                productsUiStateHelper.updateProductExpansionState(id, state)
            }
        }
    }

    private fun updateFilterSelection(filter: Filter) {
        viewModelScope.launch {
            productsScreenUiState.update { state ->
                state.copy(selectedFilter = filter)
            }
        }
    }

    private fun addToCart(id: Int) {
        viewModelScope.launch {
            addProductToCartUseCase(id = id, quantity = 1)
        }
    }

    private fun removeFromCart(id: Int) {
        viewModelScope.launch {
            removeProductFromCartUseCase(InCartProduct(Product(id)))
        }
    }

    fun intentToAction(intent: ProductsIntent) {
        when (intent) {
            is ProductsIntent.AddToCard -> addToCart(intent.id)
            is ProductsIntent.RemoveFromCard -> removeFromCart(intent.id)
            is ProductsIntent.UpdateFilter -> updateFilterSelection(intent.filter)
            is ProductsIntent.UpdateProductExpansion -> updateProductExpand(intent.id)
            is ProductsIntent.UpdateProductFavorite -> updateFavoriteIds(intent.id)
        }
    }
}

sealed interface ProductsIntent {
    data class AddToCard(val id: Int) : ProductsIntent
    data class RemoveFromCard(val id: Int) : ProductsIntent
    data class UpdateFilter(val filter: Filter) : ProductsIntent
    data class UpdateProductExpansion(val id: Int) : ProductsIntent
    data class UpdateProductFavorite(val id: Int) : ProductsIntent
}
