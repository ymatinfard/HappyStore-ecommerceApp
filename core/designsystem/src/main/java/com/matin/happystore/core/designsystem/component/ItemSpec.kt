package com.matin.happystore.core.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.model.ui.UiProduct

@Composable
fun ItemSpec(item: UiProduct, currency: String = "Euro") {
    Text(text = item.product.title, style = MaterialTheme.typography.titleLarge)
    Text(
        maxLines = DESCRIPTION_MAX_LINES,
        text = item.product.description,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.tertiary
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "${item.product.price.setScale(2)} $currency",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.tertiary
    )
}

const val DESCRIPTION_MAX_LINES = 5