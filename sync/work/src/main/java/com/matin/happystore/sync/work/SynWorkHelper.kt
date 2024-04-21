package com.matin.happystore.sync.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ForegroundInfo
import androidx.work.NetworkType

const val SYNC_NOTIFICATION_ID = 0
const val SYNC_NOTIFICATION_CHANNEL_ID = "SyncNotificationChannel"

// All sync work needs an internet connection
val SyncConstraints
    get() =
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

fun Context.syncForegroundInfo(): ForegroundInfo {
    return ForegroundInfo(
        SYNC_NOTIFICATION_ID,
        syncWorkNotification(),
    )
}

/**
 * Notification displayed on lower API levels when sync workers are being
 * run with a foreground service
 */
fun Context.syncWorkNotification(): Notification {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(
                SYNC_NOTIFICATION_CHANNEL_ID,
                "Sync",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Background task for HappyStore"
            }

        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
    }

    return NotificationCompat.Builder(this, SYNC_NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(com.matin.happystore.core.common.R.drawable.core_common_ic_happystore_notification)
        .setContentTitle("HappyStore").setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
}
