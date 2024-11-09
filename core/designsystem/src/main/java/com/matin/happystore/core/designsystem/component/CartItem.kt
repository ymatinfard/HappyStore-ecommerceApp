package com.matin.happystore.core.designsystem.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matin.happystore.core.designsystem.clipIfLengthy
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
import com.matin.happystore.core.model.InCartProduct

@Composable
fun CartItem(
    item: InCartProduct?,
    onFavoriteClick: (Int) -> Unit = {},
    onDeleteClick: (InCartProduct) -> Unit,
    onQuantityChange: (InCartProduct) -> Unit,
    onItemSelected: (Int) -> Unit = {},
) {
    if (item == null) {
        Text(text = "Product is no longer available")
        return
    }

    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp)
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
                    DynamicAsyncImage(
                        modifier = Modifier.clickable {
                            onItemSelected(item.product.id)
                        },
                        imageUrl = item.product.image,
                        contentDescription = "Product image",
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
                            Crossfade(
                                targetState = price,
                                label = "price",
                            ) {
                                Text(text = "$$it")
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            ItemQuantity(
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