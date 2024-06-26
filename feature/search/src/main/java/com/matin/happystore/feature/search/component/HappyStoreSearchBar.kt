package com.matin.happystore.feature.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
import com.matin.happystore.feature.search.BackClickListener

typealias QueryListener = (String) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HappyStoreSearchBar(
    onSearch: QueryListener,
    onQueryChange: QueryListener,
    onBackClick: BackClickListener,
    suggestions: List<String>
) {
    var query by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { onBackClick() }, modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = HappyStoreIcons.Back,
                    contentDescription = "Back",
                )
            }
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = query,
                onQueryChange = {
                    query = it
                    onQueryChange(it)
                },
                onSearch = {
                    println("onSearch trigered: $it")
                },
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = HappyStoreIcons.Search,
                        contentDescription = "search"
                    )
                },
                placeholder = { Text(text = "search...", color = Color.Gray) }
            ) {}
        }

        if (suggestions.isNotEmpty()) {
            SearchSuggestion(suggestions) { selectedSuggestion ->
                query = selectedSuggestion
            }
        }
    }
}

@Composable
fun SearchSuggestion(suggestions: List<String>, selectedSuggestion: QueryListener) {
    Column {
        Text(
            text = "Suggestions:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(suggestions) { resultText ->
                ListItem(
                    headlineContent = { Text(resultText) },
                    supportingContent = { Text("Additional info") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier =
                    Modifier
                        .clickable {
                            selectedSuggestion(resultText)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}