package com.matin.happystore.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.designsystem.R
import com.matin.happystore.core.model.ui.UiProduct

@Preview
@Composable
fun ItemSpec(item: UiProduct = UiProduct.empty(), currency: String = "Euro") {
    var seeMore by remember { mutableStateOf(true) }
    Column {
        Text(text = item.product.title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${item.product.price.setScale(2)} $currency",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            maxLines = if (seeMore) DESCRIPTION_MAX_LINES else Int.MAX_VALUE,
            text = item.product.description,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = Modifier
                .clickable { seeMore = !seeMore }
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = if (seeMore) stringResource(R.string.core_designsystem_see_more) else stringResource(
                R.string.core_designsystem_see_less
            ),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

const val DESCRIPTION_MAX_LINES = 4