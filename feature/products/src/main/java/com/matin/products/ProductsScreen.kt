package com.matin.products

import ProductItem
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.designsystem.component.CategoryFilterRow
import com.matin.happystore.core.designsystem.component.LoadingOrContent
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.ui.UiProductsAndFilters
import com.matin.products.component.HappyStoreMainHeader
import com.matin.products.model.ProductsScreenUiState

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    onMapClick: () -> Unit,
    onSearchClick: () -> Unit,
    setBottomBarVisibility: (BottomBarVisibility) -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    onImageClick: (Int) -> Unit,
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
            },
            onImageClick = {
                onImageClick(it)
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
    onImageClick: (Int) -> Unit,
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
                    onImageClick = onImageClick,
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
    onImageClick: (Int) -> Unit,
) {
    val listState = rememberLazyStaggeredGridState()
    val previousIndex = remember { mutableFloatStateOf(0f) }
    val showButton = remember { mutableStateOf(false) }

    LaunchedEffect(listState.firstVisibleItemIndex) {
        val currentIndex = listState.firstVisibleItemIndex
        showButton.value = if (currentIndex > previousIndex.floatValue) false else true
        previousIndex.floatValue = currentIndex.toFloat()
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            CategoryFilterRow(filters = productsAndFilters.filters, onFilterClick)
            LazyVerticalStaggeredGrid(
                state = listState,
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
                        onImageClick,
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showButton.value,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(300)),
        ) {
            MapButton(onMapClick)
        }
    }
}

@Composable
private fun MapButton(onMapClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(
                    alpha = .5f
                )
            ),
            onClick = { onMapClick() },
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "nearby stores button",
            )
        }
    }
}
