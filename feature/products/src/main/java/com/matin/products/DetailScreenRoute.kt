package com.matin.products

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.happystore.core.designsystem.component.DynamicAsyncImage
import com.matin.happystore.core.designsystem.component.ItemSpec
import com.matin.happystore.core.designsystem.component.TopAppBar
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.ui.clipIfLengthy
import com.matin.happystore.feature.products.R

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreenRoute(
    viewModel: DetailScreenViewModel,
    productId: Int,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    LaunchedEffect(productId) {
        viewModel.getItem(id = productId)
    }

    DetailScreenContent(
        viewmodel = viewModel,
        onBackClick = onBackClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreenContent(
    viewmodel: DetailScreenViewModel,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val uiState = viewmodel.detailScreenUiState.collectAsStateWithLifecycle()

    uiState.value.let { uiProduct ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            uiProduct.product?.wishlistIcon()?.let {
                TopAppBar(
                    title = uiProduct.product.product.title.clipIfLengthy(),
                    navigationIcon = Icons.Default.ArrowBack,
                    navigationIconContentDescription = "back",
                    actionIcon = it,
                    actionIconContentDescription = "add to wishlist",
                    onActionClick = {
                        //   viewmodel.selectItem(item.copy(isInWishlist = item.isInWishlist.not()))
                    },
                    onNavigationClick = {
                        onBackClick()
                    }
                )
            }
            uiProduct.product?.let {
                DetailScreenMainContent(
                    item = it, onAddToCartClick = {
                        //                    viewmodel.onAddToCartClick(item)
                    },
                    sharedTransitionScope,
                    animatedContentScope
                )
            }
        }
    }
}

fun UiProduct.wishlistIcon() =
    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreenMainContent(
    item: UiProduct,
    onAddToCartClick: (UiProduct) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                DynamicAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(10.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = item.product.id),
                            animatedVisibilityScope = animatedContentScope,
                        ),
                    imageUrl = item.product.image,
                    contentDescription = null,
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ItemSpec(item)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        onAddToCartClick(item)
                    },
                    Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(0),
                    enabled = item.isInCart.not()
                ) {
                    Text(
                        text = stringResource(R.string.feature_products_add_to_cart),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}