package com.matin.products.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
import com.matin.happystore.feature.products.R

@Preview
@Composable
fun HappyStoreMainHeader() {
    Icon(
        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        imageVector = HappyStoreIcons.Search,
        contentDescription = "Search Icon"
    )
    HappyStoreLogo()
}

@Composable
private fun HappyStoreLogo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.happystore_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}