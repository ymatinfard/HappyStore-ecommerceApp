package com.matin.happystore.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.matin.happystore.ui.model.ProductListScreenUiState
import com.matin.happystore.ui.model.UiFilter
import com.matin.happystore.ui.model.UiProduct
import com.matin.happystore.ui.model.UiProductState
import com.matin.happystore.ui.theme.HappyStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyStoreTheme {
                val viewModel = hiltViewModel<HappyStoreViewModel>()
                val uiProductState = combine(
                    viewModel.store.state.map { it.products },
                    viewModel.store.state.map { it.favoriteProductId },
                    viewModel.store.state.map { it.expandedProductIds },
                    viewModel.store.state.map { it.productFilterInfo }) { products, favorites, expandeds, filterInfo ->

                    if (products.isEmpty()) return@combine ProductListScreenUiState.Loading

                    val uiProducts =
                        products.filter { filterInfo.selectedFilter == null || it.category == filterInfo.selectedFilter.value }
                            .map {
                                UiProduct(
                                    it,
                                    favorites.contains(it.id),
                                    expandeds.contains(it.id)
                                )
                            }
                    val filters = filterInfo.filters.map { filter ->
                        UiFilter(filter, filter == filterInfo.selectedFilter)
                    }

                    ProductListScreenUiState.Success(
                        UiProductState(
                            uiProducts,
                            filters
                        )
                    )

                }.collectAsState(initial = ProductListScreenUiState.Loading)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductListScreen(uiProductState = uiProductState.value,
                        onFavoriteClick = { productId ->
                            viewModel.updateFavoriteIds(productId)
                        }, onProductClick = { productId ->
                            viewModel.updateProductExpand(productId)
                        }, onFilterClick = { filter ->
                            viewModel.updateFilterSelection(filter)
                        })
                }
            }
        }
    }
}