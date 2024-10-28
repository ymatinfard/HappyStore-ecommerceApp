package com.matin.products

import ProductItem
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoryFilterChips(filters = productsAndFilters.filters, onFilterClick)
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(300.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 24.dp,
            ) {
                items(
                    items = productsAndFilters.products,
                    key = {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { onMapClick() },
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.LocationOn,
                    contentDescription = "nearby stores button",
                )
            }
        }
    }
}
