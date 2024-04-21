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

        fun updateFavoriteIds(id: Int) {
            viewModelScope.launch {
                productsScreenUiState.update { state ->
                    productsUiStateHelper.updateProductFavoriteState(id, state)
                }
            }
        }

        fun updateProductExpand(id: Int) {
            viewModelScope.launch {
                productsScreenUiState.update { state ->
                    productsUiStateHelper.updateProductExpansionState(id, state)
                }
            }
        }

        fun updateFilterSelection(filter: Filter) {
            viewModelScope.launch {
                productsScreenUiState.update { state ->
                    state.copy(selectedFilter = filter)
                }
            }
        }

        fun addToCart(id: Int) {
            viewModelScope.launch {
                addProductToCartUseCase(id = id, quantity = 1)
            }
        }

        fun removeFromCart(id: Int) {
            viewModelScope.launch {
                removeProductFromCartUseCase(InCartProduct(Product(id)))
            }
        }
    }
