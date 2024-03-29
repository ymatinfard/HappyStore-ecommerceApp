package com.matin.happystore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.happystore.domain.HappyStoreRepository
import com.matin.happystore.domain.ProductCategoryFilterGeneratorUseCase
import com.matin.happystore.domain.ProductExpandUpdaterUseCase
import com.matin.happystore.domain.ProductFavoriteUpdaterUseCase
import com.matin.happystore.domain.ProductFilterSelectionUpdater
import com.matin.happystore.domain.ProductInCartItemUpdater
import com.matin.happystore.domain.ProductListReducerUseCase
import com.matin.happystore.domain.model.Filter
import com.matin.happystore.ui.redux.ApplicationState
import com.matin.happystore.ui.redux.ApplicationState.ProductFilterInfo
import com.matin.happystore.ui.redux.Store
import com.matin.happystore.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HappyStoreViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val repository: HappyStoreRepository,
    private val categoryFilterGeneratorUseCase: ProductCategoryFilterGeneratorUseCase,
    val productListReducerUseCase: ProductListReducerUseCase,
    private val productFavoriteUpdaterUseCase: ProductFavoriteUpdaterUseCase,
    private val productExpandUpdaterUseCase: ProductExpandUpdaterUseCase,
    private val productFilterSelectionUpdater: ProductFilterSelectionUpdater,
    private val productInCartItemUpdater: ProductInCartItemUpdater,
) : ViewModel() {

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            when (val result = repository.getProducts()) {
                is Result.Success -> {
                    store.update { applicationState ->
                        return@update applicationState.copy(
                            products = result.data,
                            productFilterInfo = ProductFilterInfo(
                                filters = categoryFilterGeneratorUseCase(
                                    result.data
                                )
                            )
                        )
                    }
                }

                is Result.Error -> TODO()
            }
        }
    }

    fun updateFavoriteIds(id: Int) {
        viewModelScope.launch {
            store.update { appState ->
                productFavoriteUpdaterUseCase(id, appState)
            }
        }
    }

    fun updateProductExpand(id: Int) {
        viewModelScope.launch {
            store.update { appState ->
                productExpandUpdaterUseCase(id, appState)
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

    fun updateInCartIds(id: Int) {
        viewModelScope.launch {
            store.update { appState ->
                productInCartItemUpdater(id, appState)
            }
        }
    }
}