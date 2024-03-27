package com.matin.happystore.di

import com.matin.happystore.ui.redux.ApplicationState
import com.matin.happystore.ui.redux.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationStateModule {

    @Provides
    @Singleton
    fun provideApplicationState(): Store<ApplicationState> = Store(ApplicationState())
}