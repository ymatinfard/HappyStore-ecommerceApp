package com.matin.happystore.utils

fun String.clipIfLengthy() = if (this.length > 20) this.take(20).plus("...") else this