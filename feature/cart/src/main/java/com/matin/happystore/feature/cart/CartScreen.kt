package com.matin.happystore.feature.cart

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.happystore.core.common.DataLoadingState
import com.matin.happystore.core.designsystem.component.CartItem
import com.matin.happystore.core.designsystem.component.DestinationBar
import com.matin.happystore.core.designsystem.component.SwipeToDismiss
import com.matin.happystore.core.designsystem.component.SwipeToDismissBackground
import com.matin.happystore.core.designsystem.nonSpatialExpressiveSpring
import com.matin.happystore.core.designsystem.spatialExpressiveSpring
import com.matin.happystore.core.model.InCartProduct

@Composable
fun CartScreen(viewModel: CartViewModel, onItemSelected: (Int) -> Unit) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        CartScreenContent(uiState.value, onFavoriteClick = {
        }, onDeleteClick = { inCartProduct ->
            viewModel.intentToAction(CartIntent.DeleteProduct(inCartProduct))
        }, onQuantityChange = { inCartProduct ->
            viewModel.intentToAction(CartIntent.QuantityChanged(inCartProduct))
        }, onItemSelected = onItemSelected)
        DestinationBar()
    }
}

@Composable
fun CartScreenContent(
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
        val itemAnimationSpecFade = nonSpatialExpressiveSpring<Float>()
        val itemPlacementSpec = spatialExpressiveSpring<IntOffset>()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .animateContentSize()
        ) {
            item {
                Spacer(
                    Modifier.windowInsetsTopHeight(
                        WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                    )
                )
            }
            items(cartItems, key = { it.product.id }) { item ->
                SwipeToDismiss(
                    modifier = Modifier.animateItem(
                        fadeInSpec = itemAnimationSpecFade,
                        fadeOutSpec = itemAnimationSpecFade,
                        placementSpec = itemPlacementSpec
                    ),
                    background = { progress -> SwipeToDismissBackground(progress) }
                ) {
                    CartItem(
                        item = item,
                        onFavoriteClick = onFavoriteClick,
                        onDeleteClick = {
                            onDeleteClick(item)
                        },
                        onQuantityChange = onQuantityChange,
                        onItemSelected,
                    )
                }
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
            Crossfade(
                targetState = totalPrice,
                label = "",
            ) {
                Text(text = "${cartItems.size} Items for $$it")
            }
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