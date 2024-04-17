package com.matin.happystore.core.database.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matin.happystore.core.database.model.di.ProductsDao

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(BigDecimalTypeConvertor::class)
abstract class HappyStoreDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}