package com.matin.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.ui.UiProductsAndFilters
import com.matin.happystore.core.ui.CategoryFilterChips
import com.matin.happystore.core.ui.ProductItem
import com.matin.happystore.core.ui.ShowProductListShimmerOrContent

@Composable
fun ProductsScreen(viewModel: ProductsViewModel) {
    val uiProductsState = viewModel.productsScreenUiState.collectAsState().value

    Surface {
        HandleProductScreen(
            uiProductState = uiProductsState,
            onFavoriteClick = { productId ->
                viewModel.updateFavoriteIds(productId)
            },
            onProductClick = { productId ->
                viewModel.updateProductExpand(productId)
            },
            onFilterClick = { filter ->
                viewModel.updateFilterSelection(filter)
            },
            onAddToCartClick = { productId ->
                viewModel.addToCart(productId)
            },
            onRemoveFromCartClick = { productId ->
                viewModel.removeFromCart(productId)
            },
        )
    }
}

@Composable
fun HandleProductScreen(
    uiProductState: ProductsScreenUiState,
    onFavoriteClick: (Int) -> Unit = {},
    onProductClick: (Int) -> Unit = {},
    onFilterClick: (Filter) -> Unit = {},
    onAddToCartClick: (Int) -> Unit = {},
    onRemoveFromCartClick: (Int) -> Unit = {},
) {
    ShowProductListShimmerOrContent(
        isLoading = (uiProductState.loadingState is DataLoadingState.Loading),
        contentAfterLoading = {
            ShowProductList(
                productsAndFilters = uiProductState.uiProductsAndFilters,
                onFavoriteClick = onFavoriteClick,
                onProductClick = onProductClick,
                onFilterClick = onFilterClick,
                onAddToCartClick = onAddToCartClick,
                onRemoveFromCartClick = onRemoveFromCartClick,
            )
        },
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun ShowLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(60.dp))
    }
}

@Composable
fun ShowProductList(
    productsAndFilters: UiProductsAndFilters,
    onFavoriteClick: (Int) -> Unit,
    onProductClick: (Int) -> Unit,
    onFilterClick: (Filter) -> Unit,
    onAddToCartClick: (Int) -> Unit,
    onRemoveFromCartClick: (Int) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column {
            CategoryFilterChips(filters = productsAndFilters.filters, onFilterClick)
            LazyColumn {
                items(productsAndFilters.products) { product ->
                    ProductItem(
                        product,
                        onFavoriteClick,
                        onProductClick,
                        onAddToCartClick,
                        onRemoveFromCartClick,
                    )
                }
            }
        }
    }
}
