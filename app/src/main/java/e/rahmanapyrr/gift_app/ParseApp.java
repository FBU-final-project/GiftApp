package e.rahmanapyrr.gift_app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.parse.Parse;
import com.parse.ParseObject;

import e.rahmanapyrr.gift_app.Friends.CurrentUserFriends;
import e.rahmanapyrr.gift_app.models.User;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // used whenever creating a new class like our posts in the instagram app
        ParseObject.registerSubclass(User.class);


        // RAHMANA IS REGISTERING ANOTHER SUBCLASS HERE AND WHEN SHE DOES I THINK THE CODEBASE GETS CONFUSED WHEN I TRY TO SIGNUP A NEW USER
        // SO WE NEED TO RENAME THIS OR DO SOMETHING BECAUSE IF I UNCOMMENT IT, IT EFFS EVERYTHING UP
        // ParseObject.registerSubclass(User.class);


        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("giftapp")
                .clientKey("fbuniversity")
                .server("http://fbu-giftapp.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);

        // Configure the channel
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("myChannelId", "My Channel", importance);
        channel.setDescription("Reminders");
// Register the channel with the notifications manager
        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(channel);

        createNotification(8,"Hey!!", "open the app");
    }

    private void createNotification(int nId, String title, String body) {

        Intent intent = new Intent(this, CurrentUserFriends.class);
        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this, "channelId")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(nId, mBuilder.build());
    }



    public void registerUP(){
        ParseObject.registerSubclass(User.class);
    }




}
