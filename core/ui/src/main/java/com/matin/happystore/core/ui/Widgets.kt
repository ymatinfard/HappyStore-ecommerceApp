package com.matin.happystore.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
import com.matin.happystore.core.designsystem.theme.AppTypography
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.InCartProduct
import com.matin.happystore.core.model.ui.UiFilter
import com.matin.happystore.core.model.ui.UiProduct

@Composable
fun ProductItem(
    item: UiProduct,
    onFavoriteClick: (Int) -> Unit,
    onProductClicked: (Int) -> Unit,
    onAddToCartClick: (Int) -> Unit,
    onRemoveFromCartClick: (Int) -> Unit,
) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 6.dp),
    ) {
        ElevatedCard(
            modifier =
            Modifier
                .height(170.dp)
                .clickable {
                    onProductClicked(item.product.id)
                },
            elevation =
            CardDefaults.cardElevation(
                defaultElevation = 1.dp,
            ),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
        ) {
            Row {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier =
                    Modifier
                        .padding(8.dp)
                        .size(160.dp),
                ) {
                    AsyncImage(
                        model = item.product.image,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                    )
                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        FavoriteIcon(id = item.product.id, item.isFavorite, onFavoriteClick)
                    }
                }
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
        DescriptionText(description = item.product.description, visible = item.isExpended)
    }
}

@Composable
private fun FavoriteIcon(
    id: Int,
    isFavorite: Boolean,
    onFavoriteClick: (Int) -> Unit,
) {
    Image(
        imageVector = if (isFavorite) HappyStoreIcons.Favorites else HappyStoreIcons.FavoriteBorder,
        contentDescription = null,
        modifier =
        Modifier
            .clip(
                CircleShape,
            )
            .background(color = MaterialTheme.colorScheme.outline)
            .padding(3.dp)
            .clickable { onFavoriteClick(id) },
        colorFilter = ColorFilter.tint(if (isFavorite) Color.Red else Color.White),
    )
}

@Composable
fun RatingIndicator(
    modifier: Modifier,
    rate: Float,
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        CircularProgressIndicator(progress = { rate / 5f })
        Text(text = rate.toString(), fontSize = 12.sp)
    }
}

@Composable
fun DescriptionText(
    description: String,
    visible: Boolean,
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter =
        slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } +
                expandVertically(
                    expandFrom = Alignment.Top,
                ) +
                fadeIn(
                    initialAlpha = 0.3f,
                ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        Text(text = description, modifier = Modifier.padding(6.dp))
    }
}

@Composable
fun CategoryFilterChips(
    filters: List<UiFilter>,
    onFilterClick: (Filter) -> Unit,
) {
    LazyRow(contentPadding = PaddingValues(start = 8.dp, end = 8.dp)) {
        items(filters) { item ->
            FilterChip(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { onFilterClick(item.filter) },
                label = {
                    Text(text = item.filter.displayText)
                },
                selected = item.isSelected,
                leadingIcon =
                if (item.isSelected) {
                    {
                        Icon(
                            imageVector = HappyStoreIcons.Done,
                            contentDescription = "Filter icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}

@Composable
fun ShoppingButton(
    onClick: () -> Unit,
    isBadgeVisible: Boolean = false,
) {
    Box(contentAlignment = Alignment.CenterStart) {
        Button(
            onClick = { onClick() },
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(
                imageVector = HappyStoreIcons.ShoppingCart,
                contentDescription = null,
            )
        }
        AnimatedVisibility(visible = isBadgeVisible, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                imageVector = HappyStoreIcons.Check,
                modifier =
                Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(color = Color.White),
                tint = MaterialTheme.colorScheme.inversePrimary,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun CartItem(
    item: InCartProduct?,
    onFavoriteClick: (Int) -> Unit = {},
    onDeleteClick: (InCartProduct) -> Unit,
    onQuantityChange: (InCartProduct) -> Unit,
) {
    if (item == null) {
        Text(text = "Product is no longer available")
        return
    }

    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 6.dp),
    ) {
        Column(
            modifier =
            Modifier
                .height(170.dp),
        ) {
            Row {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier =
                    Modifier
                        .padding(8.dp)
                        .size(170.dp),
                ) {
                    AsyncImage(
                        model = item.product.image,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    item.product.title.let {
                        Text(
                            text = it.clipIfLengthy(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                    }

                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        val price = item.product.price.times(item.quantity.toBigDecimal())
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "$$price")
                            Spacer(modifier = Modifier.height(6.dp))
                            CartItemQuantity(
                                item,
                                onQuantityChange,
                            )
                        }

                        Icon(
                            imageVector = HappyStoreIcons.Delete,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier =
                            Modifier.clickable {
                                onDeleteClick(item)
                            },
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier =
                Modifier
                    .height(3.dp)
                    .padding(start = 8.dp, end = 8.dp),
                color = Color.Gray,
            )
        }
    }
}

@Composable
fun CartItemQuantity(
    inCartProduct: InCartProduct,
    onQuantityChange: (InCartProduct) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Row(modifier = Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = HappyStoreIcons.ArrowUp,
                contentDescription = null,
                modifier =
                Modifier.clickable {
                    val newQuantity = inCartProduct.quantity + 1
                    onQuantityChange(inCartProduct.copy(quantity = newQuantity))
                },
            )
            Text(
                text = inCartProduct.quantity.toString(),
                modifier =
                Modifier
                    .padding(start = 4.dp, end = 4.dp),
                fontSize = 16.sp,
            )
            Icon(
                imageVector = HappyStoreIcons.ArrowDown,
                modifier =
                Modifier.clickable {
                    if (inCartProduct.quantity == 1) return@clickable
                    val newQuality = inCartProduct.quantity - 1
                    onQuantityChange(inCartProduct.copy(quantity = newQuality))
                },
                contentDescription = null,
            )
        }
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

@Preview
@Composable
fun ShowProductListShimmerOrContent(
    isLoading: Boolean = true,
    contentAfterLoading: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    if (isLoading) {
        LazyColumn {
            items(6) {
                ProductShimmerItem(modifier = modifier)
            }
        }
    } else {
        contentAfterLoading()
    }
}

@Composable
private fun ProductShimmerItem(modifier: Modifier = Modifier) {
    Row(
        modifier =
        modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .height(140.dp),
    ) {
        Box(
            modifier =
            Modifier
                .size(140.dp)
                .shimmerEffect(),
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier =
                Modifier
                    .height(26.dp)
                    .padding(start = 6.dp)
                    .fillMaxWidth()
                    .shimmerEffect(),
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Box(
                    modifier =
                    Modifier
                        .width(52.dp)
                        .height(32.dp)
                        .shimmerEffect(),
                )
            }
        }
    }
}
