package com.sopt.famfam

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import android.util.Log
import com.sopt.famfam.activity.ChatActivity
import com.sopt.famfam.activity.MainActivity
import org.json.JSONObject
import java.lang.Exception
import java.net.URLDecoder

class FIreBaseMessagingService : FirebaseMessagingService() {


    private val channelId = "channel"
    internal var channelName = "alert"

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "From: " + remoteMessage!!.from!!)

        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            if (true) {
            } else {
                handleNow()
            }
        }
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body!!)
            sendNotification(remoteMessage.notification!!.body,remoteMessage.notification!!.tag)
        }
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendNotification(messageBody: String?, tag : String?) {
        Log.d("fire",messageBody+" "+tag)
        var message=""
        var type = 0
        try {
            var json = JSONObject(messageBody)
            message = json.getString("message")
            type = json.getInt("type");
        }catch (e : Exception){
            message=messageBody!!;
        }
            var intent = Intent(this, MainActivity::class.java)

        if(type==1)
        {
            intent = Intent(this, ChatActivity::class.java)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("팸팸")
            .setContentText(URLDecoder.decode(message, "UTF-8"))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
    companion object {
        private val TAG = "MyFirebaseMsgService"
    }
}

