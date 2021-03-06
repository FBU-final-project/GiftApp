package e.rahmanapyrr.gift_app;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationIntentService extends IntentService {
        // abbreviated tag name
        private static final String TAG = "RegIntentService";
        public RegistrationIntentService() {
            super(TAG);
        }
        public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
        public static final String GCM_TOKEN = "gcmToken";

        @Override
        protected void onHandleIntent(Intent intent) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            // Make a call to Instance API
            InstanceID instanceID = InstanceID.getInstance(this);
            String senderId = getResources().getString(R.string.gcm_defaultSenderId);
            try {
                // request token that will be used by the server to send push notifications
                String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                sharedPreferences.edit().putString(GCM_TOKEN, token).apply();
                Log.d(TAG, "GCM Registration Token: " + token);

                // pass along this data
                sendRegistrationToServer(token);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to complete token refresh", e);
                // If an exception happens while fetching the new token or updating our registration data
                // on a third-party server, this ensures that we'll attempt the update at a later time.
                sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
            }
        }

        private void sendRegistrationToServer(String token) {
            // Add custom implementation, as needed.
            // if registration sent was successful, store a boolean that indicates whether the generated token has been sent to server
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
        }
}

