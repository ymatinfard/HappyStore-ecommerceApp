package com.matin.happystore.feature.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.matin.happystore.core.common.BottomBarVisibility

@Composable
fun SearchScreen(viewModel: ViewModel, setBottomBarVisibility: (BottomBarVisibility) -> Unit) {
    setBottomBarVisibility(BottomBarVisibility.HIDDEN)
    Text("Search screen")
}