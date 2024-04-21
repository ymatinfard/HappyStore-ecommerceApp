package com.matin.happystore.core.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("tbl_cart")
class InCartProductEntity(
    @PrimaryKey
    val productId: Int,
    val quantity: Int,
)

data class InCartProductFullEntity(
    @Embedded
    val inCartProduct: InCartProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: ProductEntity,
)
