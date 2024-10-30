import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.matin.happystore.core.designsystem.component.DynamicAsyncImage
import com.matin.happystore.core.designsystem.component.LoadingWheel
import com.matin.happystore.core.designsystem.theme.AppTypography
import com.matin.happystore.core.model.Product
import com.matin.happystore.core.model.ui.UiProduct
import com.matin.happystore.core.ui.DescriptionText
import com.matin.happystore.core.ui.RatingIndicator
import com.matin.happystore.core.ui.ShoppingButton
import com.matin.happystore.core.ui.clipIfLengthy
import com.matin.happystore.core.ui.components.FavoriteIcon

@Composable
fun ProductItem(
    item: UiProduct,
    onFavoriteClick: (Int) -> Unit = {},
    onProductClicked: (Int) -> Unit = {},
    onAddToCartClick: (Int) -> Unit = {},
    onRemoveFromCartClick: (Int) -> Unit = {},
) {
    Column {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 6.dp),
        ) {
            ElevatedCard(
                modifier =
                Modifier
                    .height(170.dp)
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
                    Spacer(modifier = Modifier.width(190.dp))
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

                        Row(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("${item.product.price}$", fontSize = 16.sp)
                            ShoppingButton(
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

            ProductCard(item = item, onFavoriteClick = onFavoriteClick)
        }
        DescriptionText(description = item.product.description, visible = item.isExpended)
    }
}

@Composable
fun ProductCard(item: UiProduct, onFavoriteClick: (Int) -> Unit) {
    var showLoading by remember { mutableStateOf(false) }
    val padding = 6.dp
    val cardModifier = Modifier
        .padding(start = 12.dp, bottom = 12.dp)
        .size(160.dp)

    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = cardModifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box {
            DynamicAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding), imageUrl = item.product.image, contentDescription = "image"
            )

            if (showLoading) {
                LoadingOverlay()
            }

            FavoriteRow(
                isFavorite = item.isFavorite,
                productId = item.product.id,
                onFavoriteClick = onFavoriteClick,
                padding = padding
            )
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


@Preview
@Composable
fun ProductItemPreview() {
    ProductItem(item = UiProduct(product = Product(), false, false, false))
}