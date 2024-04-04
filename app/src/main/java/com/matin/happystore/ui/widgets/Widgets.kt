package com.matin.happystore.ui.widgets

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.matin.happystore.domain.model.Filter
import com.matin.happystore.ui.BottomNavigationScreens
import com.matin.happystore.ui.model.UiFilter
import com.matin.happystore.ui.model.UiProduct
import com.matin.happystore.ui.util.clipIfLengthy


@Composable
fun ProductItem(
    item: UiProduct,
    onFavoriteClick: (Int) -> Unit,
    onProductClicked: (Int) -> Unit,
    onAddToCartClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 6.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .height(170.dp)
                .clickable {
                    onProductClicked(item.product.id)
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Row {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(160.dp)
                ) {
                    AsyncImage(
                        model = item.product.image,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Image(
                            imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    CircleShape
                                )
                                .background(color = MaterialTheme.colorScheme.secondary)
                                .padding(3.dp)
                                .clickable { onFavoriteClick(item.product.id) },
                            colorFilter = ColorFilter.tint(if (item.isFavorite) Color.Red else Color.LightGray)
                        )
                    }

                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.padding(top = 12.dp)) {
                        Text(
                            text = item.product.title.clipIfLengthy(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(text = item.product.category)
                    }

                    RatingIndicator(
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .size(32.dp), item.product.rating.rate
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${item.product.price}$", fontSize = 16.sp)
                        ShoppingButton(
                            onClick = { onAddToCartClick(item.product.id) },
                            badgeIsVisible = item.isInCart
                        )
                    }
                }
            }
        }
        DescriptionText(description = item.product.description, visible = item.isExpended)
    }
}

@Composable
fun RatingIndicator(modifier: Modifier, rate: Float) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        CircularProgressIndicator(progress = { rate / 5f })
        Text(text = rate.toString(), fontSize = 12.sp)
    }
}

@Composable
fun DescriptionText(description: String, visible: Boolean) {
    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Text(text = description, modifier = Modifier.padding(6.dp))
    }
}

@Composable
fun CategoryFilterChips(filters: List<UiFilter>, onFilterClick: (Filter) -> Unit) {
    LazyRow(contentPadding = PaddingValues(start = 8.dp, end = 8.dp)) {
        items(filters) { item ->
            FilterChip(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { onFilterClick(item.filter) },
                label = {
                    Text(text = item.filter.displayText)
                },
                selected = item.isSelected,
                leadingIcon = if (item.isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Filter icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
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
fun HappyStoreBottomNavigation(
    navController: NavController,
    selectedTabIndex: MutableState<Int>,
    bottomNavItems: List<BottomNavigationScreens>,
    inCartProductsCount: Int,
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = selectedTabIndex.value == index,
                onClick = {
                    selectedTabIndex.value = index
                    navController.navigate(bottomNavItem.route)
                },
                icon = {
                    when {
                        (inCartProductsCount > 0) && bottomNavItem is BottomNavigationScreens.Cart -> {
                            BadgedBox(badge = {
                                Badge {
                                    Text(text = "$inCartProductsCount")
                                }
                            }) {
                                Icon(imageVector = bottomNavItem.icon, contentDescription = null)
                            }
                        }

                        else -> Icon(imageVector = bottomNavItem.icon, contentDescription = null)
                    }
                }
            )
        }
    }
}

@Composable
fun ShoppingButton(onClick: () -> Unit, badgeIsVisible: Boolean = false) {
    Box(contentAlignment = Alignment.CenterStart) {
        Button(
            onClick = { onClick() },
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null
            )
        }
        if (badgeIsVisible) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(color = Color.White),
                tint = Color.Green,
                contentDescription = null
            )
        }
    }
}

@Composable
fun CartItem(
    item: UiProduct,
    onFavoriteClick: (Int) -> Unit = {},
    onDeleteClick: (Int) -> Unit,
    onQuantityChange: (productId: Int, quantity: Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .height(170.dp),
        ) {
            Row {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(170.dp)
                ) {
                    AsyncImage(
                        model = item.product.image,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Image(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    CircleShape
                                )
                                .background(color = Color.LightGray)
                                .padding(2.dp)
                                .clickable { onFavoriteClick(item.product.id) },
                            colorFilter = ColorFilter.tint(if (item.isFavorite) Color.Red else Color.DarkGray)
                        )
                    }

                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.product.title.clipIfLengthy(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val price = item.product.price * item.inCartQuantity.toBigDecimal()
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "$${price}")
                            Spacer(modifier = Modifier.height(6.dp))
                            CartItemQuantity(
                                item.inCartQuantity,
                                item.product.id,
                                onQuantityChange
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.clickable {
                                onDeleteClick(item.product.id)
                            }
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .height(3.dp)
                    .padding(start = 8.dp, end = 8.dp),
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CartItemQuantity(
    quantity: Int,
    productId: Int,
    onQuantityChange: (productId: Int, quantity: Int) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(modifier = Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onQuantityChange(productId, quantity + 1)
                })
            Text(
                text = quantity.toString(),
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                modifier = Modifier.clickable {
                    if (quantity == 1) return@clickable
                    onQuantityChange(productId, quantity - 1)
                },
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
fun TotalCartItemsPrice(cartItems: List<UiProduct> = emptyList()) {
    val totalPrice = cartItems.sumOf { it.product.price * it.inCartQuantity.toBigDecimal() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "${cartItems.size} Items for $$totalPrice")
        }

        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Checkout")
        }
    }
}
