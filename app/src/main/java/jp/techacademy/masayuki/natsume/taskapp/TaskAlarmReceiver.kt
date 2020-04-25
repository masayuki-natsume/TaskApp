package jp.techacademy.masayuki.natsume.taskapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.Notification
import android.app.PendingIntent
import android.graphics.BitmapFactory
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import androidx.core.app.NotificationCompat
import io.realm.Realm


class TaskAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // SDKバージョンが26以上の場合、チャネルを設定する必要がある
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = "Channel description"
            notificationManager.createNotificationChannel(channel)
        }

        // 通知の設定を行う
        val builder = NotificationCompat.Builder(context, "default")
        builder.setSmallIcon(R.drawable.small_icon) //ｱｲｺﾝのﾘｿｰｽを設定
        builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.large_icon)) //Bitmap指定
        builder.setWhen(System.currentTimeMillis()) //いつ表示の指定
        builder.setDefaults(Notification.DEFAULT_ALL) //音ﾊﾞｲﾌﾞﾗｲﾄ指定
        builder.setAutoCancel(true)                  //trueがﾀｯﾌﾟで消え falseはｺｰﾄﾞ上で消す

        // EXTRA_TASK から Task の id を取得して、 id から Task のインスタンスを取得する
        val taskId = intent!!.getIntExtra(EXTRA_TASK, -1)
        val realm = Realm.getDefaultInstance()
        val task = realm.where(Task::class.java).equalTo("id", taskId).findFirst()

        // タスクの情報を設定する
        builder.setTicker(task!!.title)       // 5.0以降は表示されない
        builder.setContentTitle(task.title)   //ｱｲｺﾝの横に太文字列を指定
        builder.setContentText(task.contents) //ContentTitleの下の文字列を指定

        // 通知をタップしたらアプリを起動するようにする
        val startAppIntent = Intent(context, MainActivity::class.java)
        startAppIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
        val pendingIntent = PendingIntent.getActivity(context, 0, startAppIntent, 0)
        builder.setContentIntent(pendingIntent) //ﾀｯﾌﾟした時起動するIntentを指定

        // 通知を表示する
        notificationManager.notify(task!!.id, builder.build())
        realm.close()
    }
}