package com.matin.happystore.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.happystore.core.designsystem.icon.HappyStoreIcons
import com.matin.happystore.core.model.Filter
import com.matin.happystore.core.model.ui.UiFilter

@Composable
fun CategoryFilterRow(
    filters: List<UiFilter>,
    onFilterClick: (Filter) -> Unit,
) {
    LazyRow(contentPadding = PaddingValues(start = 8.dp, end = 8.dp)) {
        items(filters) { item ->
            FilterChip(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { onFilterClick(item.filter) },
                label = {
                    Text(text = item.filter.displayText)
                },
                selected = item.isSelected,
                leadingIcon =
                if (item.isSelected) {
                    {
                        Icon(
                            imageVector = HappyStoreIcons.Done,
                            contentDescription = "Filter icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}