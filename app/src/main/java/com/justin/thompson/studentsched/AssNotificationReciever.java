package com.justin.thompson.studentsched;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Thompson on 7/7/17.
 */

public class AssNotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                new Intent("com.my.package.MY_UNIQUE_ACTION"),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp)
        {
            Log.d("myTag", "Alarm is already active");
        }*/
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context,MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                setSmallIcon(R.drawable.ic_sync_black_24dp).
                setContentIntent(pendingIntent).
                setContentTitle("Assessment Alert").
                setContentText("Today is your goal date").
                setAutoCancel(true);
        notificationManager.notify(100,builder.build());

    }
}
