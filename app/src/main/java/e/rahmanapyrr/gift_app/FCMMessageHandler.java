package e.rahmanapyrr.gift_app;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Notification;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import org.joda.time.*;
import java.time.temporal.ChronoUnit;

import e.rahmanapyrr.gift_app.Friends.AddFriends;

import static org.joda.time.Years.yearsBetween;

public class FCMMessageHandler extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseMsgService";
    ArrayList<ParseUser> friends;
    DateTime today = new DateTime();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "FROM" + remoteMessage.getFrom());

        // Verifica se a mensagem contém uma carga útil de dados.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data: ," + remoteMessage.getData());

            sendNotification(remoteMessage.getNotification().getBody());
//            showNotification(remoteMessage.getData().get("description"), remoteMessage.getData().get("description"));
        }

        // Verifica se a mensagem contem payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message body: " + remoteMessage.getNotification().getBody());
//            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        }
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

    private void sendBirthdaynotice() {
        friends = new ArrayList<>();
        ParseUser currentUserClass = ParseUser.getCurrentUser();

        final ParseRelation<ParseUser> friend_relations = currentUserClass.getRelation("FriendRelation");
        ParseQuery<ParseUser> friends_list = friend_relations.getQuery();

        friends_list.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    friends.clear();
                    friends.addAll(objects);
                } else {
                    e.printStackTrace();
                }
            }
        });

        for(ParseUser friend: friends){
            org.joda.time.LocalDate birthdate = (org.joda.time.LocalDate)friend.get("birthdayString");
            int days = Days.daysBetween(new DateTime(birthdate.toDate().getTime()), today).getDays();
//            LocalDate today = LocalDate.now();
//            LocalDate yesterday = today.minusDays(1);
// Duration oneDay = Duration.between(today, yesterday); // throws an exception
//            java.time.Duration.between(today.atStartOfDay(), yesterday.atStartOfDay()).toDays();
//            ChronoUnit.DAYS.between(birthdate, today.atStartOfDay());
                if(days == 14){
                sendNotification(friend.getUsername() + " 's Birthday is coming up soon!");

            }

            DateTime dateTime = new DateTime();
            System.out.println(dateTime);
            System.out.println(dateTime.minusDays(14));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        }
// Mon Aug 06 15:26:58 PDT 2018

    }

    private void sendBirthdayNotice(){

        friends = new ArrayList<>();
        final ParseRelation<ParseUser> friend_relations = ParseUser.getCurrentUser().getRelation("FriendRelation");
        ParseQuery<ParseUser> friends_list = friend_relations.getQuery();
        friends_list.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, com.parse.ParseException e) {
                if (e == null) {
                    friends.clear();
                    friends.addAll(objects);
                } else {
                    e.printStackTrace();
                }
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        DateTime today = new DateTime();
        for(ParseUser friend : friends){
            String strDate = friend.get("birthdayString").toString();
            Date date = null;
            try {
                date = sdf.parse(strDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            DateTime dateTime = new DateTime(date);
            Years age = yearsBetween(dateTime, today);
            DateTime next_birthday = dateTime.plusYears(age.getYears()+ 1);
            System.out.println("Two weeks before next birthday: " + next_birthday.minusDays(14));
        }


    }

}


