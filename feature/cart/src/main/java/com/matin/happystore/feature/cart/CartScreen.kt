package com.matin.happystore.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.designsystem.component.CartItem
import com.matin.happystore.core.model.InCartProduct

@Composable
fun CartScreen(viewModel: CartViewModel, onItemSelected: (Int) -> Unit) {
    val inCartProductsState = viewModel.cartScreenUiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        HandleCartScreen(inCartProductsState.value, onFavoriteClick = {
        }, onDeleteClick = { inCartProduct ->
            viewModel.intentToAction(CartIntent.DeleteProduct(inCartProduct))
        }, onQuantityChange = { inCartProduct ->
            viewModel.intentToAction(CartIntent.QuantityChanged(inCartProduct))
        }, onItemSelected = onItemSelected)
    }
}

@Composable
fun HandleCartScreen(
    inCartProductsState: CartScreenUiState,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (InCartProduct) -> Unit,
    onQuantityChange: (InCartProduct) -> Unit,
    onItemSelected: (Int) -> Unit,
) {
    when (inCartProductsState.loadingState) {
        is DataLoadingState.Loaded ->
            CartItems(
                inCartProductsState.inCartProducts,
                onFavoriteClick,
                onDeleteClick,
                onQuantityChange,
                onItemSelected,
            )

        is DataLoadingState.Error -> Text(stringResource(R.string.feature_cart_no_item_in_cart))
        DataLoadingState.Loading -> Text(text = stringResource(R.string.feature_cart_loading))
    }
}

@Composable
fun CartItems(
    cartItems: List<InCartProduct>,
    onFavoriteClick: (Int) -> Unit,
    onDeleteClick: (InCartProduct) -> Unit,
    onQuantityChange: (InCartProduct) -> Unit,
    onItemSelected: (Int) -> Unit,
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { item ->
                CartItem(
                    item = item,
                    onFavoriteClick = onFavoriteClick,
                    onDeleteClick = onDeleteClick,
                    onQuantityChange = onQuantityChange,
                    onItemSelected,
                )
            }
        }

        TotalCartItemsPrice(cartItems)
    }
}

@Composable
fun TotalCartItemsPrice(cartItems: List<InCartProduct> = emptyList()) {
    val totalPrice = cartItems.sumOf { it.product.price * it.quantity.toBigDecimal() }

    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(text = "Total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "${cartItems.size} Items for $$totalPrice")
        }

        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        ) {
            Text(text = "Checkout")
        }
    }
}
