package com.matin.happystore.core.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "tbl_product")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: BigDecimal,
    val category: String,
    val description: String,
    val image: String,
    @Embedded
    val rating: Rating,
) {
    @Entity("rating_tbl")
    data class Rating (
        val rate: Float,
        val count: Int
    )
}
