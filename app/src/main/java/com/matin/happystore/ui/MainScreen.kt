package com.matin.happystore.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.matin.happystore.domain.model.Filter
import com.matin.happystore.ui.model.UiProductState
import com.matin.happystore.widgets.CategoryFilterChips
import com.matin.happystore.widgets.ProductItem

@Composable
fun MainScreen(
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