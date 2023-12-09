package com.dicoding.habitapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.preference.PreferenceManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.detail.DetailHabitActivity
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import com.dicoding.habitapp.utils.NOTIFICATION_CHANNEL_ID

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val habitId = inputData.getInt(HABIT_ID, 0)
    private val habitTitle = inputData.getString(HABIT_TITLE)
    private val messageNotification = applicationContext.getString(R.string.notify_content)

    override fun doWork(): Result {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = prefManager.getBoolean(applicationContext.getString(R.string.pref_key_notify), false)

        //TODO 12 : If notification preference on, show notification with pending intent
        // Jika preferensi notifikasi aktif, tampilkan notifikasi
        if (shouldNotify) {
            showNotification()
        }

        return Result.success()
    }

    // Menampilkan notifikasi
    private fun showNotification() {
        // Membuat PendingIntent untuk membuka DetailHabitActivity saat notifikasi diklik
        val pendingIntent : PendingIntent? = getPendingIntent(habitId)
        val managerCompat = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Membuat saluran notifikasi (Notification Channel) untuk Android versi Oreo ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = managerCompat.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
            if (channel == null) {
                val newChannel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "notification",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                managerCompat.createNotificationChannel(newChannel)
            }
        }

        val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(habitTitle)
            .setContentText(messageNotification)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        builder.setAutoCancel(true)

        managerCompat.notify(habitId, builder.build())
    }

    private fun getPendingIntent(id: Int): PendingIntent? {
        val intent = Intent(applicationContext, DetailHabitActivity::class.java).apply {
            putExtra(HABIT_ID, id)
        }
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }
    }
}