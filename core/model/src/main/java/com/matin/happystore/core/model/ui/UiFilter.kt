package com.matin.happystore.core.model.ui

import com.matin.happystore.core.model.Filter

data class UiFilter (
    val filter: Filter,
    val isSelected: Boolean
)