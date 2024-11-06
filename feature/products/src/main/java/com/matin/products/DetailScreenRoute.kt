package com.matin.products

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.happystore.core.designsystem.component.DynamicAsyncImage
import com.matin.happystore.core.designsystem.component.ItemSpec
import com.matin.happystore.core.designsystem.component.TopAppBar
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.designsystem.LocalAnimatedVisibilityScope
import com.matin.happystore.core.designsystem.LocalSharedTransitionScope
import com.matin.happystore.core.designsystem.clipIfLengthy
import com.matin.happystore.feature.products.R
import kotlin.math.roundToInt

@Composable
fun DetailScreenRoute(
    viewModel: DetailScreenViewModel,
    productId: Int,
    onBackClick: () -> Unit
) {
    LaunchedEffect(productId) {
        viewModel.getItem(id = productId)
    }

    DetailScreenContent(
        viewmodel = viewModel,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(
    viewmodel: DetailScreenViewModel,
    onBackClick: () -> Unit
) {
    val uiState = viewmodel.detailScreenUiState.collectAsStateWithLifecycle()

    uiState.value.let { uiProduct ->
        Column(
            modifier = Modifier
                .padding(WindowInsets.navigationBars.asPaddingValues())
        ) {
            TopAppBar(
                title = uiProduct.product.product.title.clipIfLengthy(),
                navigationIcon = Icons.Default.ArrowBack,
                navigationIconContentDescription = "back",
                actionIcon = uiProduct.product.wishlistIcon(),
                actionIconContentDescription = "add to wishlist",
                onActionClick = {},
                onNavigationClick = {
                    onBackClick()
                }
            )
            DetailScreenMainContent(
                item = uiProduct.product, onAddToCartClick = {
                    //viewmodel.onAddToCartClick(item)
                }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreenMainContent(
    item: UiProduct,
    onAddToCartClick: (UiProduct) -> Unit = {},
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalArgumentException("No shared transition scope provided")
    val animatedContentScope = LocalAnimatedVisibilityScope.current
        ?: throw IllegalArgumentException("No animated visibility scope provided")
    val configuration = LocalConfiguration.current
    val screenHorizontalPadding =
        if (isTablet()) (configuration.screenWidthDp * 0.2).roundToInt().dp else 16.dp

    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 16.dp, horizontal = screenHorizontalPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DynamicAsyncImage(
                modifier = Modifier
                    .size(360.dp)
                    .padding(10.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = item.product.id),
                        animatedVisibilityScope = animatedContentScope,
                    ),
                imageUrl = item.product.image,
                contentDescription = null,
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ItemSpec(item)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        onAddToCartClick(item)
                    },
                    modifier = Modifier.fillMaxWidth(),
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

fun UiProduct.wishlistIcon() =
    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder

@Composable
fun isTablet(): Boolean {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    return screenWidthDp >= 600.dp
}