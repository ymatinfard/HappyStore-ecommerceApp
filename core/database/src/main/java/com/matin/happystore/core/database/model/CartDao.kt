package com.matin.happystore.core.database.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT productId FROM TBL_CART")
    fun getInCartProductIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(inCartProductEntity: InCartProductEntity)

    @Delete
    suspend fun removeFromCart(inCartProductEntity: InCartProductEntity)

    @Transaction
    @Query("SELECT * FROM TBL_CART")
    fun getInCartProductsFullDetail(): Flow<List<InCartProductFullEntity>>

    @Update
    suspend fun updateQuantity(inCartProductEntity: InCartProductEntity)
}
