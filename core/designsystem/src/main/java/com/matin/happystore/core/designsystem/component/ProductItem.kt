import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matin.happystore.core.designsystem.LocalAnimatedVisibilityScope
import com.matin.happystore.core.designsystem.LocalSharedTransitionScope
import com.matin.happystore.core.designsystem.clipIfLengthy
import com.matin.happystore.core.designsystem.component.ButtonShop
import com.matin.happystore.core.designsystem.component.Description
import com.matin.happystore.core.designsystem.component.DynamicAsyncImage
import com.matin.happystore.core.designsystem.component.FavoriteIcon
import com.matin.happystore.core.designsystem.component.LoadingWheel
import com.matin.happystore.core.designsystem.component.RatingIndicator
import com.matin.happystore.core.designsystem.happyStoreBoundsTransform
import com.matin.happystore.core.designsystem.theme.AppTypography
import com.matin.happystore.core.model.ui.UiProduct

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductItem(
    item: UiProduct,
    onFavoriteClick: (Int) -> Unit = {},
    onProductClicked: (Int) -> Unit = {},
    onAddToCartClick: (Int) -> Unit = {},
    onRemoveFromCartClick: (Int) -> Unit = {},
    onImageClick: (Int) -> Unit = {},
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            ElevatedCard(
                modifier =
                Modifier
                    .defaultMinSize(minHeight = 180.dp)
                    .padding(top = 30.dp)
                    .clickable {
                        onProductClicked(item.product.id)
                    },
                colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Row {
                    Spacer(modifier = Modifier.width((PRODUCT_IMG_SIZE + 10).dp))
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            Text(
                                text = item.product.title.clipIfLengthy(),
                                style = AppTypography.bodyLarge,
                            )
                            Text(text = item.product.category, style = AppTypography.bodySmall)
                        }

                        RatingIndicator(
                            modifier =
                            Modifier
                                .padding(start = 6.dp)
                                .size(32.dp),
                            item.product.rating.rate,
                        )

                        FlowRow(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(bottom = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxRowHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${item.product.price}$", fontSize = 16.sp)
                            }
                            ButtonShop(
                                onClick = {
                                    if (item.isInCart) {
                                        onRemoveFromCartClick(item.product.id)
                                    } else {
                                        onAddToCartClick(item.product.id)
                                    }
                                },
                                isBadgeVisible = item.isInCart,
                            )
                        }
                    }
                }
            }

            ProductImage(
                modifier = Modifier.padding(start = 4.dp).size(PRODUCT_IMG_SIZE.dp),
                item = item,
                onFavoriteClick = onFavoriteClick,
                onImageClick,
            )
        }
        Description(description = item.product.description, visible = item.isExpended)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProductImage(
    modifier: Modifier,
    item: UiProduct,
    onFavoriteClick: (Int) -> Unit,
    onImageClick: (Int) -> Unit,
) {
    var showLoading by remember { mutableStateOf(false) }

    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalArgumentException("LocalSharedTransitionScope not provided")
    val animatedContentScope = LocalAnimatedVisibilityScope.current
        ?: throw IllegalArgumentException("LocalAnimatedVisibilityScope not provided")

    with(sharedTransitionScope) {
        ElevatedCard(
            shape = RoundedCornerShape(16.dp),
            modifier = modifier.size(160.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Box {
                DynamicAsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clickable {
                            onImageClick(item.product.id)
                        }
                        .sharedBounds(
                            rememberSharedContentState(key = item.product.id),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = happyStoreBoundsTransform,
                        ),
                    imageUrl = item.product.image, contentDescription = "image",
                )

                if (showLoading) {
                    LoadingOverlay()
                }

                FavoriteRow(
                    isFavorite = item.isFavorite,
                    productId = item.product.id,
                    onFavoriteClick = onFavoriteClick,
                    padding = 8.dp
                )
            }
        }
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingWheel(contentDesc = "loading photo")
    }
}

@Composable
fun FavoriteRow(
    isFavorite: Boolean,
    productId: Int,
    onFavoriteClick: (Int) -> Unit,
    padding: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        horizontalArrangement = Arrangement.End
    ) {
        FavoriteIcon(id = productId, isFavorite = isFavorite, onFavoriteClick = onFavoriteClick)
    }
}

const val PRODUCT_IMG_SIZE = 160