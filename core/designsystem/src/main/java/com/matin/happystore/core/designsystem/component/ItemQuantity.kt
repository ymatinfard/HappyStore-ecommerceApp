package com.matin.happystore.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
import com.matin.happystore.core.model.InCartProduct

@Composable
fun ItemQuantity(
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