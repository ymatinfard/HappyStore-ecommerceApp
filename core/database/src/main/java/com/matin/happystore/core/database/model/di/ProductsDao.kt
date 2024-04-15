package com.matin.happystore.core.database.model.di

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matin.happystore.core.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(productList: List<ProductEntity>)

    @Query("SELECT * FROM tbl_product")
    fun getProducts(): Flow<List<ProductEntity>>
}