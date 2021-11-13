package com.example.candles_guardian.representation.ui.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import java.util.*


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val r = Random()
        val i1 = r.nextInt(99)
        val notificationHelper =
            NotificationHelper(context,intent)
        val nb: NotificationCompat.Builder =
            notificationHelper.channelNotification()
        notificationHelper.manager?.notify(i1, nb.build())

    }
}