package com.matin.happystore.core.database.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matin.happystore.core.database.model.di.ProductsDao

@Database(entities = [ProductEntity::class], version = 1)
abstract class HappyStoreDatabase: RoomDatabase() {
    abstract fun productsDao() : ProductsDao
}