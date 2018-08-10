package e.rahmanapyrr.gift_app;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;


public class GCMMessageHandler extends GcmListenerService {

    public static final int MESSAGE_NOTIFICATION_ID = 1234;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        sendNotification(message);
    }

    public void sendNotification(String body) {
        Intent intent = new Intent(this, CurrentUserFriends.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

       PendingIntent pendingIntent = PendingIntent.getActivity(this, 0/*Request code*/, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Firebase Cloud Messaging")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0/*ID of notify:*/, notifiBuilder.build());
    }
}
