package vn.edu.vtn.quanlybanhang.util.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.util.Date;

import vn.edu.vtn.quanlybanhang.R;
import vn.edu.vtn.quanlybanhang.main.MainActivity;

public class NotificationHelper {
    private static final String FCM_CHANNEL_ID = "vn.edu.vtn.quanlybanhang";
    private static final String FCM_CHANNEL_NAME = "VTN Channel";
    private NotificationManager manager;

    public static void setNotification(Context context, String body, String title) {
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String Description = "This is my channel";

        int NOTIFICATION_ID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(mChannel);
            }

        }


        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setColor(context.getResources().getColor(android.R.color.holo_red_dark));

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        if (notificationManager != null) {

            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
