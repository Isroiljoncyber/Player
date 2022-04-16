package com.example.player.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.player.R
import com.example.player.Service.NotificationActionService
import com.example.player.data.entity.MusicModel

abstract class CreateNotification {

    companion object {
        val CHANNAL_ID = "CH1"
        val NEXT = "action_next"
        val PREVIUS = "action_previus"
        val PLAY = "action_play"


        var notification: Notification? = null

        @SuppressLint("WrongConstant", "ResourceAsColor")
        fun createNotification(context: Context, model_object: MusicModel, playbutton: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManagerCompat = NotificationManagerCompat.from(context)
                val mediaSessionCompat = MediaSessionCompat(context, "tag")
                val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.qw)

                //------------------ Previus -----------------------
                var pendingpreovius: PendingIntent?
                var drw_previus: Int
                //            if (pos==0)
//            {
//                pendingpreovius=null;
//                drw_previus= 0;
//            } else
                run {
                    val intentprevius =
                        Intent(context, NotificationActionService::class.java).setAction(PREVIUS)
                    pendingpreovius = PendingIntent.getBroadcast(
                        context,
                        0,
                        intentprevius,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    drw_previus = R.drawable.ic_previus_24_black
                }

                // -------------------Paly/Pause ----------------------
                val intentplay =
                    Intent(context, NotificationActionService::class.java).setAction(PLAY)
                val pendingplay = PendingIntent.getBroadcast(
                    context,
                    0,
                    intentplay,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                //--------------------- Next --------------------------
                var pendingnext: PendingIntent?
                var drw_next: Int
                //            if (pos==size)
//            {
//                pendingnext=null;
//                drw_next= 0;
//            } else
                run {
                    val intentnext =
                        Intent(context, NotificationActionService::class.java).setAction(NEXT)
                    pendingnext = PendingIntent.getBroadcast(
                        context,
                        0,
                        intentnext,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    drw_next = R.drawable.ic_next_24_black
                }
                notification = Notification.Builder(context, CHANNAL_ID)
                    .setContentTitle(model_object.title)
                    .setContentText(model_object.artist)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.ic_music_note_24, 2)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .addAction(drw_previus, "Previous", pendingpreovius)
                    .addAction(playbutton, "Play", pendingplay)
                    .addAction(drw_next, "Next", pendingnext)
                    .setStyle(
                        Notification.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                    )
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build()
                notificationManagerCompat.notify(1, notification!!)
            }
        }
    }

}