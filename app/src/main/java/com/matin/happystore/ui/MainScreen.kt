package com.matin.happystore.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.matin.happystore.domain.model.Product
import com.matin.happystore.widgets.ProductItem

@Composable
fun MainScreen(products: List<UiProduct>, onFavoriteClick: (Int) -> Unit) {

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(products) { product ->
                ProductItem(product, onFavoriteClick)
            }
        }
    }
}