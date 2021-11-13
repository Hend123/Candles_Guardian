package com.example.candles_guardian.representation.ui.notifications


import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.candles_guardian.R
import java.util.*

class NotificationHelper(base: Context?, intent: Intent) : ContextWrapper(base) {
    private var mManager: NotificationManager? = null
    private lateinit var intent: Intent
    private var className: String? = null
    private var subjectName: String? = null
    private var typeName: String? = null
    private var date: String? = null
    private var details: String? = null
    private var pendingIntent: PendingIntent? = null
    private  var mPreferences: SharedPreferences
    private  var mPrefsEditor: SharedPreferences.Editor


    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        if (intent.getStringExtra("notificationType").equals("quizesResult")) {
            subjectName = intent.getStringExtra("subjectName")
            date = intent.getStringExtra("date")
            details = intent.getStringExtra("details")
        } else {
            className = intent.getStringExtra("className")
            subjectName = intent.getStringExtra("subjectName")
            typeName = intent.getStringExtra("typeName")
            date = intent.getStringExtra("date")
        }
        channel.enableLights(true)
        channel.setLightColor(Color.RED)
        channel.enableVibration(true)
        manager!!.createNotificationChannel(channel)
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }


    fun channelNotification(): NotificationCompat.Builder {
        val channelNot: NotificationCompat.Builder
        if (intent.getStringExtra("notificationType").equals("quizesResult")) {
            Log.v("notificationType", "quizesResult")
            channelNot = NotificationCompat.Builder(
                applicationContext,
                channelID
            ).setContentTitle("نتائج الامتحانات")
                .setContentText(subjectName)
                .setSmallIcon(R.drawable.ic_baseline_bedtime_24)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(details + " " + date)
                ).setContentIntent(pendingIntent)
        } else {
            Log.v("notificationType", "quizes")

            channelNot = NotificationCompat.Builder(
                applicationContext,
                channelID
            ).setContentTitle(typeName)
                .setContentText(subjectName)
                .setSmallIcon(R.drawable.ic_baseline_bedtime_24)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(className + " " + date)
                ).setContentIntent(pendingIntent)
        }
        return channelNot
    }

    companion object {
        const val channelID = "channelID"
        const val channelName = "Channel Name"
    }

    init {
        mPreferences = getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        mPrefsEditor = mPreferences.edit()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.intent = intent
            val requestCode = mPreferences.getInt("requestCode", 0)+1
                mPrefsEditor.putInt("requestCode", requestCode)
                mPrefsEditor.commit()
                pendingIntent = PendingIntent.getActivity(base,
                    requestCode, intent, 0)
                createChannel()




        }
    }
}