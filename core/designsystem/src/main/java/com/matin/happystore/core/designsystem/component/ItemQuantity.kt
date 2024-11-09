package com.matin.happystore.core.designsystem.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
import com.matin.happystore.core.model.InCartProduct

@Composable
fun ItemQuantity(
    inCartProduct: InCartProduct,
    onQuantityChange: (InCartProduct) -> Unit,
) {
    Row(modifier = Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
        GradientTintedIconButton(
            imageVector = HappyStoreIcons.ArrowUp,
            onClick = {
                val newQuantity = inCartProduct.quantity + 1
                onQuantityChange(inCartProduct.copy(quantity = newQuantity))
            },
            contentDescription = "increase quantity"
        )
        Crossfade(
            targetState = inCartProduct.quantity,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = it.toString(),
                modifier = Modifier.widthIn(min = 24.dp),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
        }
        GradientTintedIconButton(
            imageVector = HappyStoreIcons.ArrowDown,
            onClick = {
                if (inCartProduct.quantity != 1) {
                    val newQuality = inCartProduct.quantity - 1
                    onQuantityChange(inCartProduct.copy(quantity = newQuality))
                }
            },
            contentDescription = "decrease quantity"
        )
    }
}