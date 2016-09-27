package com.wodm.android.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.wodm.R;
import com.wodm.android.CartoonApplication;
import com.wodm.android.ui.Main2Activity;

/**
 * Created by moon1 on 2016/5/9.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        //((CartoonApplication)((AppActivity)(context)).getApplication()).exitApp();
        ((CartoonApplication)context.getApplicationContext()).exitApp();
        manager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            //例如这个id就是你传过来的
            String id = intent.getStringExtra("id");
            //MainActivity是你点击通知时想要跳转的Activity
            Intent playIntent = new Intent(context, Main2Activity.class);
            playIntent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("title").setContentText("提醒内容").setSmallIcon(R.drawable.icon_more).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true).setSubText("二级text");
            manager.notify(1, builder.build());
    }
}
