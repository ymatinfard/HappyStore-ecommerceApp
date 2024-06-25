package com.matin.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.ui.ShowProductListShimmerOrContent
import com.matin.products.component.HappyStoreMainHeader
import com.matin.products.component.ProductList
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
        HandleProductScreen(
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
fun HandleProductScreen(
    uiProductState: ProductsScreenUiState,
    onFavoriteClick: (Int) -> Unit = {},
    onProductClick: (Int) -> Unit = {},
    onFilterClick: (Filter) -> Unit = {},
    onAddToCartClick: (Int) -> Unit = {},
    onRemoveFromCartClick: (Int) -> Unit = {},
    onMapClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    ShowProductListShimmerOrContent(
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
