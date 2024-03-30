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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.domain.model.Filter
import com.matin.happystore.ui.model.ProductsAndFilters
import com.matin.happystore.widgets.CategoryFilterChips
import com.matin.happystore.widgets.ProductItem


@Composable
fun ProductsScreen(
    viewModel: HappyStoreViewModel,
) {

    val productListUiState by viewModel.productListUIState.collectAsState()

    Surface {
        HandleProductScreen(uiProductState = productListUiState,
            onFavoriteClick = { productId ->
                viewModel.updateFavoriteIds(productId)
            }, onProductClick = { productId ->
                viewModel.updateProductExpand(productId)
            }, onFilterClick = { filter ->
                viewModel.updateFilterSelection(filter)
            },
            onAddToCartClick = { productId ->
                viewModel.updateInCartIds(productId)
            })
    }
}

@Composable
fun HandleProductScreen(
    uiProductState: ProductsScreenUiState,
    onFavoriteClick: (Int) -> Unit = {},
    onProductClick: (Int) -> Unit = {},
    onFilterClick: (Filter) -> Unit = {},
    onAddToCartClick: (Int) -> Unit = {},
) {
    when (uiProductState) {
        is ProductsScreenUiState.Success -> ShowProductList(
            productsAndFilters = uiProductState.data,
            onFavoriteClick = onFavoriteClick,
            onProductClick = onProductClick,
            onFilterClick = onFilterClick,
            onAddToCartClick = onAddToCartClick
        )
        is ProductsScreenUiState.Loading -> ShowLoading()
        is ProductsScreenUiState.Error -> TODO()
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
    productsAndFilters: ProductsAndFilters,
    onFavoriteClick: (Int) -> Unit,
    onProductClick: (Int) -> Unit,
    onFilterClick: (Filter) -> Unit,
    onAddToCartClick: (Int) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            CategoryFilterChips(filters = productsAndFilters.filters, onFilterClick)
            LazyColumn {
                items(productsAndFilters.products) { product ->
                    ProductItem(product, onFavoriteClick, onProductClick, onAddToCartClick)
                }
            }
        }
    }
}