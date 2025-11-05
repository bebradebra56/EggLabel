package com.egsi.labsi.sog.ioegje.presentation.notificiation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import com.egsi.labsi.sog.EggLabelActivity
import com.egsi.labsi.sog.R
import com.egsi.labsi.sog.ioegje.presentation.app.EggLabelApplication
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val EGG_LABEL_CHANNEL_ID = "egg_label_notifications"
private const val EGG_LABEL_CHANNEL_NAME = "EggLabel Notifications"
private const val EGG_LABEL_NOT_TAG = "EggLabel"

class EggLabelPushService : FirebaseMessagingService(){
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Обработка notification payload
        remoteMessage.notification?.let {
            if (remoteMessage.data.contains("url")) {
                eggLabelShowNotification(it.title ?: EGG_LABEL_NOT_TAG, it.body ?: "", data = remoteMessage.data["url"])
            } else {
                eggLabelShowNotification(it.title ?: EGG_LABEL_NOT_TAG, it.body ?: "", data = null)
            }
        }

        // Обработка data payload
        if (remoteMessage.data.isNotEmpty()) {
            eggLabelHandleDataPayload(remoteMessage.data)
        }
    }

    private fun eggLabelShowNotification(title: String, message: String, data: String?) {
        val eggLabelNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Создаем канал уведомлений для Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                EGG_LABEL_CHANNEL_ID,
                EGG_LABEL_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            eggLabelNotificationManager.createNotificationChannel(channel)
        }

        val eggLabelIntent = Intent(this, EggLabelActivity::class.java).apply {
            putExtras(bundleOf(
                "url" to data
            ))
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val eggLabelPendingIntent = PendingIntent.getActivity(
            this,
            0,
            eggLabelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val eggLabelNotification = NotificationCompat.Builder(this, EGG_LABEL_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.egg_label_ic_noti)
            .setAutoCancel(true)
            .setContentIntent(eggLabelPendingIntent)
            .build()

        eggLabelNotificationManager.notify(System.currentTimeMillis().toInt(), eggLabelNotification)
    }

    private fun eggLabelHandleDataPayload(data: Map<String, String>) {
        data.forEach { (key, value) ->
            Log.d(EggLabelApplication.EGG_LABEL_MAIN_TAG, "Data key=$key value=$value")
        }
    }
}