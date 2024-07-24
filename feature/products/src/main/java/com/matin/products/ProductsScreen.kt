package com.matin.products

import ProductItem
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.ui.UiProductsAndFilters
import com.matin.happystore.core.ui.CategoryFilterChips
import com.matin.happystore.core.ui.LoadingOrContent
import com.matin.products.component.HappyStoreMainHeader
import com.matin.products.model.ProductsScreenUiState

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    onMapClick: () -> Unit,
    onSearchClick: () -> Unit,
    setBottomBarVisibility: (BottomBarVisibility) -> Unit,
) {
    val uiProductsState = viewModel.productsScreenUiState.collectAsState().value
    setBottomBarVisibility(BottomBarVisibility.VISIBLE)
    Surface {
        ProductScreenContent(
            uiProductState = uiProductsState,
            onFavoriteClick = { productId ->
                viewModel.intentToAction(ProductsIntent.UpdateProductFavorite(productId))
            },
            onProductClick = { productId ->
                viewModel.intentToAction(ProductsIntent.UpdateProductExpansion(productId))
            },
            onFilterClick = { filter ->
                viewModel.intentToAction(ProductsIntent.UpdateFilter(filter))
            },
            onAddToCartClick = { productId ->
                viewModel.intentToAction(ProductsIntent.AddToCard(productId))
            },
            onRemoveFromCartClick = { productId ->
                viewModel.intentToAction(ProductsIntent.RemoveFromCard(productId))
            },
            onMapClick = {
                onMapClick()
            },
            onSearchClick = {
                onSearchClick()
            }
        )
    }
}

@Composable
fun ProductScreenContent(
    uiProductState: ProductsScreenUiState,
    onFavoriteClick: (Int) -> Unit = {},
    onProductClick: (Int) -> Unit = {},
    onFilterClick: (Filter) -> Unit = {},
    onAddToCartClick: (Int) -> Unit = {},
    onRemoveFromCartClick: (Int) -> Unit = {},
    onMapClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    LoadingOrContent(
        isLoading = (uiProductState.loadingState is DataLoadingState.Loading),
        contentAfterLoading = {
            Column {
                HappyStoreMainHeader(onSearchClick = onSearchClick)
                ProductList(
                    productsAndFilters = uiProductState.uiProductsAndFilters,
                    onFavoriteClick = onFavoriteClick,
                    onProductClick = onProductClick,
                    onFilterClick = onFilterClick,
                    onAddToCartClick = onAddToCartClick,
                    onRemoveFromCartClick = onRemoveFromCartClick,
                    onMapClick = onMapClick,
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductList(
    productsAndFilters: UiProductsAndFilters,
    onFavoriteClick: (Int) -> Unit,
    onProductClick: (Int) -> Unit,
    onFilterClick: (Filter) -> Unit,
    onAddToCartClick: (Int) -> Unit,
    onRemoveFromCartClick: (Int) -> Unit,
    onMapClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 40.dp),
                onClick = { onMapClick() },
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.LocationOn,
                    contentDescription = "nearby stores button",
                )
            }
        },
    ) {
        Column {
            CategoryFilterChips(filters = productsAndFilters.filters, onFilterClick)
            LazyColumn(modifier = Modifier.padding(bottom = 50.dp)) {
                items(productsAndFilters.products, key = {
                    it.product.id
                }) { product ->
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
