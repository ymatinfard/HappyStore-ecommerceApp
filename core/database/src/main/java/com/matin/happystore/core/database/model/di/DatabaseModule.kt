package com.matin.happystore.core.database.model.di

import android.content.Context
import androidx.room.Room
import com.matin.happystore.core.database.model.HappyStoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context,
    ): HappyStoreDatabase = Room.databaseBuilder(context, HappyStoreDatabase::class.java, "happystore_db").build()
}
