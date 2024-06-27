package com.matin.products.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
import com.matin.happystore.feature.products.R

@Composable
fun HappyStoreMainHeader(onSearchClick: () -> Unit) {
    Box {
        Image(
            modifier = Modifier.height(140.dp),
            painter = painterResource(id = R.drawable.header_background),
            contentDescription = null,
            contentScale = FixedScale(0.5f),
        )
        IconButton(
            onClick = { onSearchClick() }, modifier = Modifier
                .padding(top = 10.dp, start = 10.dp)
        ) {
            Icon(
                imageVector = HappyStoreIcons.Search,
                contentDescription = "Search Icon"
            )
        }
        HappyStoreLogo()
    }
}

@Composable
private fun HappyStoreLogo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.happystore_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}