package jobtrends.jobsurvey.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.viewmodel.HomeViewModel

abstract class MyFirebaseMessagingService : FirebaseMessagingService {
    private val _tag: String?

    constructor() : super() {
        _tag = "MyFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val msg = "From: ${remoteMessage!!.from!!}"
        Log.d(_tag, msg)

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(_tag, "MessageModel data payload: ${remoteMessage.data}")

            if (true) {
                scheduleJob()
            } else {
                handleNow()
            }

        }
        if (remoteMessage.notification != null) {
            Log.d(_tag, "MessageModel Notification Body: ${remoteMessage.notification!!.body}")
        }

    }

    private fun scheduleJob() {
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val myJob = dispatcher.newJobBuilder().setService(MyJobService::class.java).setTag("my-job-tag")
                .build()
        dispatcher.schedule(myJob)
    }

    private fun handleNow() {
        Log.d(_tag, "Short lived task is done.")
    }

    private fun sendNotification(messageBody: String?) {
        val intent = Intent(this, HomeViewModel::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent
                .getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_logo_gray_500dp).setContentTitle("FCM MessageModel")
                .setContentText(messageBody).setAutoCancel(true).setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}