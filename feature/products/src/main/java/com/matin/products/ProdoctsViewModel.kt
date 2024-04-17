package com.matin.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.core.domain.GetProductsUseCase
import com.matin.happystore.core.domain.ProductCategoryFilterGeneratorUseCase
import com.matin.happystore.core.redux.ApplicationState
import com.matin.happystore.core.redux.Store
import com.matin.products.stateupdater.ProductFilterSelectionUpdater
import com.matin.products.stateupdater.ProductExpandUpdater
import com.matin.happystore.core.ui.ProductFavoriteUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.matin.happystore.core.common.Result
import com.matin.happystore.core.common.asResource
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.ui.ProductsAndFilters
import com.matin.happystore.core.ui.ProductInCartItemUpdater
import com.matin.happystore.core.ui.ProductListReducer
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val getProductsUseCase: GetProductsUseCase,
    private val categoryFilterGeneratorUseCase: ProductCategoryFilterGeneratorUseCase,
    private val productListReducer: ProductListReducer,
    private val productFavoriteUpdater: ProductFavoriteUpdater,
    private val productExpandUpdater: ProductExpandUpdater,
    private val productFilterSelectionUpdater: ProductFilterSelectionUpdater,
    private val productInCartItemUpdater: ProductInCartItemUpdater,
    val productListUIStateGenerator: ProductListUIStateGenerator,
) : ViewModel() {

    init {
        collectProducts()
    }

    private fun collectProducts() {
        viewModelScope.launch {
            getProductsUseCase().asResource().collect { result ->
                when (result) {
                    is Result.Success -> {
                        store.update { applicationState ->
                            return@update applicationState.copy(
                                products = result.data,
                                productFilterInfo = ApplicationState.ProductFilterInfo(
                                    filters = categoryFilterGeneratorUseCase(
                                        result.data
                                    ),
                                    selectedFilter = applicationState.productFilterInfo.selectedFilter
                                )
                            )
                        }
                    }

                    is Result.Error -> { //ToDo()
                    }
                }
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

        fun updateProductExpand(id: Int) {
            viewModelScope.launch {
                store.update { appState ->
                    productExpandUpdater(id, appState)
                }
            }
        }

        fun updateFilterSelection(filter: Filter) {
            viewModelScope.launch {
                store.update { appState ->
                    productFilterSelectionUpdater(filter, appState)
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

        val productListUiState = combine(
            productListReducer.reduce(),
            store.state.map { it.productFilterInfo }) { products, filterInfo ->
            productListUIStateGenerator(products, filterInfo)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProductsScreenUiState.Loading
        )

        val inCartItemsCount: StateFlow<Int> = store.state.map { it.inCartProductIds.size }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )
    }

    sealed interface ProductsScreenUiState {
        data object Loading : ProductsScreenUiState
        data class Success(val data: ProductsAndFilters) : ProductsScreenUiState
        data class Error(val error: Exception) : ProductsScreenUiState
    }
