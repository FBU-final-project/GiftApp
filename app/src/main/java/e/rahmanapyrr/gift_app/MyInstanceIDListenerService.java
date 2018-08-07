package e.rahmanapyrr.gift_app;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.internal.FirebaseAppHelper;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import static android.support.constraint.Constraints.TAG;

public class MyInstanceIDListenerService extends FirebaseInstanceIdService {
   private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Fetch updated Instance ID token and notify of changes
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

//        String uid = FirebaseInstanceId.getInstance().getToken();
//
//        FirebaseDatabase.getInstance().getReference().child("users/" + uid + "/FirebaseToken")
//                .setValue(refreshedToken);
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        Log.d("TOKEN", String.valueOf(token));

    }
}