package com.matin.happystore.core.model.ui

import com.matin.happystore.domain.model.Filter

data class UiFilter (
    val filter: Filter,
    val isSelected: Boolean
)