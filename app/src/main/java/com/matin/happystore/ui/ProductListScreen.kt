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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.domain.model.Filter
import com.matin.happystore.ui.model.ProductListScreenUiState
import com.matin.happystore.ui.model.UiProductState
import com.matin.happystore.widgets.CategoryFilterChips
import com.matin.happystore.widgets.ProductItem

@Composable
fun ProductListScreen(
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