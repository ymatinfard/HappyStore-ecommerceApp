package com.matin.happystore

import android.app.Application
import com.matin.happystore.sync.work.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HappyStoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()
            Sync.init(this)
    }
}