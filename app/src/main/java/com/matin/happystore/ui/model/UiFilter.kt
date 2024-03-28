package com.matin.happystore.ui.model

import com.matin.happystore.domain.model.Filter

data class UiFilter (
    val filter: Filter,
    val isSelected: Boolean
)