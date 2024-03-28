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
                    val uiProducts = products.map {
                        UiProduct(
                            it,
                            favorites.contains(it.id),
                            expandeds.contains(it.id)
                        )
                    }
                    val filters = filterInfo.filters.map { filter ->
                        UiFilter(filter, filter == filterInfo.selectedFilter)
                    }
                    UiProductState(
                        uiProducts,
                        filters
                    )

                }.collectAsState(initial = UiProductState())

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(uiProductState = uiProductState.value,
                        onFavoriteClick = { productId ->
                            viewModel.updateFavoriteIds(productId)
                        }, onProductClick = { productId ->
                            viewModel.updateProductExpand(productId)
                        }, onFilterClick = {filter ->
                            viewModel.updateFilterSelection(filter)
                        })
                }
            }
        }
    }
}