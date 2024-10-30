package com.matin.happystore.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.matin.happystore.core.common.BottomBarVisibility
import com.matin.happystore.feature.search.component.HappyStoreSearchBar

typealias BackClickListener = () -> Unit

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    setBottomBarVisibility: (BottomBarVisibility) -> Unit = {},
    onBackClick: BackClickListener
) {
    setBottomBarVisibility(BottomBarVisibility.HIDDEN)
    val uiState = viewModel.uiState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        HappyStoreSearchBar(
            onSearch = { },
            onQueryChange = { query ->
                viewModel.intentToAction(
                    SearchScreenIntent.QueryChanged(
                        query
                    )
                )
            },
            onBackClick,
            suggestions = uiState.value.searchSuggestion,
        )
    }
}