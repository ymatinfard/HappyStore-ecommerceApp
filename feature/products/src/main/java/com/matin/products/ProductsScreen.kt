package com.matin.products

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.ui.UiProductsAndFilters
import com.matin.happystore.core.ui.CategoryFilterChips
import com.matin.happystore.core.ui.ProductItem
import com.matin.happystore.core.ui.ShowProductListShimmerOrContent
import com.matin.happystore.feature.products.R
import com.matin.products.model.ProductsScreenUiState

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    onMapClick: () -> Unit,
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
                onMapClick = onMapClick,
            )
        },
        modifier = Modifier.fillMaxSize(),
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowProductList(
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
        containerColor = MaterialTheme.colorScheme.background,
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
            HappyStoreLogo()
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

@Preview
@Composable
fun HappyStoreLogo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 7.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.size(width = 200.dp, height = 100.dp)
        )
    }
}
