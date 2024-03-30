package com.matin.happystore.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.matin.happystore.ui.model.UiProduct
import com.matin.happystore.widgets.CartItem
import kotlinx.coroutines.flow.map

@Composable
fun CartScreen(viewModel: HappyStoreViewModel) {

    val inCartProductsState = viewModel.inCartProductsUiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {

        HandleCartScreen(inCartProductsState.value, onFavoriteClick = { favoriteItemId ->
            viewModel.updateFavoriteIds(favoriteItemId)
        }, onDeleteClick = { deletedItemId ->
            viewModel.updateInCartIds(deletedItemId)
        })
    }
}

@Composable
fun HandleCartScreen(
    inCartProductsState: CartScreenUiState,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    when (inCartProductsState) {
        is CartScreenUiState.Data -> ShowCartItems(
            inCartProductsState.data,
            onFavoriteClick,
            onDeleteClick
        )

        is CartScreenUiState.Empty -> Text("No item in cart")
    }
}

@Composable
fun ShowCartItems(
    cartItems: List<UiProduct>,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    LazyColumn {
        items(cartItems) { item ->
            CartItem(item = item, onFavoriteClick = onFavoriteClick, onDeleteClick = onDeleteClick)
        }
    }
}