package com.matin.happystore.core.database.model.di

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.matin.happystore.core.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Upsert
    fun upsertProducts(productList: List<ProductEntity>)

    @Query("SELECT * FROM tbl_product")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("DELETE FROM tbl_product WHERE id IN (:ids)")
    fun deleteProducts(ids: List<Int>)
}