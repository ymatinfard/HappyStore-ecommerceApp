package com.matin.happystore.core.database.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Upsert
    fun upsertProducts(productList: List<ProductEntity>)

    @Query("SELECT * FROM TBL_PRODUCT")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("DELETE FROM TBL_PRODUCT WHERE id IN (:ids)")
    fun deleteProducts(ids: List<Int>)
}
