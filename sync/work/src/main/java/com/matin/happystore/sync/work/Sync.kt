package com.matin.happystore.sync.work

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager

internal const val SYNC_WORK_NAME = "syncWorkName"

object Sync {

    fun init(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(SYNC_WORK_NAME, ExistingWorkPolicy.KEEP, SyncWorker.startUpSyncWork())
        }
    }
}