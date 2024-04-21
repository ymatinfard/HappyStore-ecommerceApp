package com.matin.happystore.core.database.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProductEntity::class, InCartProductEntity::class], version = 2)
@TypeConverters(BigDecimalTypeConvertor::class)
abstract class HappyStoreDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao

    abstract fun cartDao(): CartDao
}
