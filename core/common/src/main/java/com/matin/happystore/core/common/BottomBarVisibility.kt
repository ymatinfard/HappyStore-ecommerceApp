package com.matin.happystore.core.common

enum class BottomBarVisibility {
    VISIBLE,
    HIDDEN,
    ;

    fun isVisible(): Boolean = this == VISIBLE
}
