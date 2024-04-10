package com.matin.happystore.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.ui.CartItem
import com.matin.happystore.core.ui.TotalCartItemsPrice
import com.matin.happystore.feature.cart.CartScreenUiState
import com.matin.happystore.feature.cart.CartViewModel


@Composable
fun CartScreen(viewModel: CartViewModel) {

    val inCartProductsState = viewModel.inCartProductsUiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

        HandleCartScreen(inCartProductsState.value, onFavoriteClick = { favoriteItemId ->
            viewModel.updateFavoriteIds(favoriteItemId)
        }, onDeleteClick = { deletedItemId ->
            viewModel.updateInCartItemIds(deletedItemId)
        }, onQuantityChange = { productId, newQuantity ->
            viewModel.updateProductInCartQuantity(productId, newQuantity)
        })
    }
}

@Composable
fun HandleCartScreen(
    inCartProductsState: CartScreenUiState,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onQuantityChange: (Int, Int) -> Unit,
) {
    when (inCartProductsState) {
        is CartScreenUiState.Data -> ShowCartItems(
            inCartProductsState.data,
            onFavoriteClick,
            onDeleteClick,
            onQuantityChange
        )

        is CartScreenUiState.Empty -> Text("No item in cart")
    }
}

@Composable
fun ShowCartItems(
    cartItems: List<UiProduct>,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onQuantityChange: (Int, Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { item ->
                CartItem(
                    item = item,
                    onFavoriteClick = onFavoriteClick,
                    onDeleteClick = onDeleteClick,
                    onQuantityChange = onQuantityChange
                )
            }
        }

        TotalCartItemsPrice(cartItems)
    }
}