package com.matin.happystore.feature.cart

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
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.ui.CartItem
import com.matin.happystore.core.ui.TotalCartItemsPrice

@Composable
fun CartScreen(viewModel: CartViewModel) {
    val inCartProductsState = viewModel.cartScreenUiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        HandleCartScreen(inCartProductsState.value, onFavoriteClick = { favoriteItemId ->
            viewModel.updateFavoriteIds(favoriteItemId)
        }, onDeleteClick = { inCartProduct ->
            viewModel.removeItem(inCartProduct)
        }, onQuantityChange = { inCartProduct ->
            viewModel.onQuantityChanged(inCartProduct)
        })
    }
}

@Composable
fun HandleCartScreen(
    inCartProductsState: CartScreenUiState,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (InCartProduct) -> Unit,
    onQuantityChange: (InCartProduct) -> Unit,
) {
    when (inCartProductsState.loadingState) {
        is DataLoadingState.Loaded ->
            ShowCartItems(
                inCartProductsState.inCartProducts,
                onFavoriteClick,
                onDeleteClick,
                onQuantityChange,
            )

        is DataLoadingState.Error -> Text("No item in cart")
        DataLoadingState.Loading -> Text(text = "Loading...")
    }
}

@Composable
fun ShowCartItems(
    cartItems: List<InCartProduct>,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (InCartProduct) -> Unit,
    onQuantityChange: (InCartProduct) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
                .background(color = MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { item ->
                CartItem(
                    item = item,
                    onFavoriteClick = onFavoriteClick,
                    onDeleteClick = onDeleteClick,
                    onQuantityChange = onQuantityChange,
                )
            }
        }

        TotalCartItemsPrice(cartItems)
    }
}
