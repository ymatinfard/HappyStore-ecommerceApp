package com.matin.happystore.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkProduct(
    val id: Int,
    val title: String?,
    val price: Double?,
    val category: String?,
    val description: String?,
    val image: String?,
    val rating: Rating?,
) {
    @Serializable
    data class Rating(
        val rate: Float?,
        val count: Int?,
    )
}
