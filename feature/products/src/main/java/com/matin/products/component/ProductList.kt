package com.matin.products.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.ui.UiProductsAndFilters
import com.matin.happystore.core.ui.CategoryFilterChips
import com.matin.happystore.core.ui.ProductItem

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