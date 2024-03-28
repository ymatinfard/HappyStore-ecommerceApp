package com.matin.happystore.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matin.happystore.domain.model.Filter
import com.matin.happystore.ui.model.ProductListScreenUiState
import com.matin.happystore.ui.model.UiFilter
import com.matin.happystore.ui.model.UiProduct
import com.matin.happystore.ui.model.UiProductState
import com.matin.happystore.widgets.CategoryFilterChips
import com.matin.happystore.widgets.ProductItem
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map


@Composable
fun ProductsScreen(viewModel: HappyStoreViewModel, navController: NavController) {
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

    Surface {
        ShowProductList(uiProductState = uiProductState.value,
            onFavoriteClick = { productId ->
                viewModel.updateFavoriteIds(productId)
            }, onProductClick = { productId ->
                viewModel.updateProductExpand(productId)
            }, onFilterClick = { filter ->
                viewModel.updateFilterSelection(filter)
            })
    }
}

@Composable
fun ShowProductList(
    uiProductState: ProductListScreenUiState,
    onFavoriteClick: (Int) -> Unit,
    onProductClick: (Int) -> Unit,
    onFilterClick: (Filter) -> Unit,
) {
    when (uiProductState) {
        is ProductListScreenUiState.Success -> ShowProductList(
            uiProductState = uiProductState.data,
            onFavoriteClick = onFavoriteClick,
            onProductClick = onProductClick,
            onFilterClick = onFilterClick
        )

        is ProductListScreenUiState.Loading -> ShowLoading()
        is ProductListScreenUiState.Error -> TODO()
    }
}

@Composable
fun ShowLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(60.dp))
    }
}

@Composable
fun ShowProductList(
    uiProductState: UiProductState,
    onFavoriteClick: (Int) -> Unit,
    onProductClick: (Int) -> Unit,
    onFilterClick: (Filter) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            CategoryFilterChips(filters = uiProductState.filters, onFilterClick)
            LazyColumn {
                items(uiProductState.products) { product ->
                    ProductItem(product, onFavoriteClick, onProductClick)
                }
            }
        }
    }
}