package com.matin.happystore.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.designsystem.shimmerEffect

@Preview
@Composable
fun LoadingOrContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    contentAfterLoading: @Composable () -> Unit = {},
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