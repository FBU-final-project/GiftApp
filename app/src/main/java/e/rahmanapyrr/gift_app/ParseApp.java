package e.rahmanapyrr.gift_app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.service.autofill.SaveCallback;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;

import e.rahmanapyrr.gift_app.Friends.CurrentUserFriends;
import e.rahmanapyrr.gift_app.models.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // used whenever creating a new class like our posts in the instagram app
        ParseObject.registerSubclass(User.class);

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        //REGISTERING ANOTHER SUBCLASS HERE,  I THINK THE CODEBASE GETS CONFUSED WHEN I TRY TO SIGNUP A NEW USER
        // SO WE NEED TO RENAME THIS OR DO SOMETHING BECAUSE IF I UNCOMMENT IT, IT EFFS EVERYTHING UP
        // ParseObject.registerSubclass(User.class);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("giftapp")
                .clientKey("fbuniversity")
                .server("http://fbu-giftapp.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);


    ParsePush.subscribeInBackground("", new com.parse.SaveCallback() {
        @Override
        public void done(ParseException e) {
            // callback to confirm subscription

            if (e == null) {
                Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
            } else {
                Log.e("com.parse.push", "failed to subscribe for push", e);
            }
        }
    });


    final ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if(installation.getDeviceToken()==null) {
        installation.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
    }
        installation.saveInBackground();
}}
