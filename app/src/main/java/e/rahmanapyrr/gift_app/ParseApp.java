package e.rahmanapyrr.gift_app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import e.rahmanapyrr.gift_app.models.User;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(User.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("giftapp")
                .clientKey("fbuniversity")
                .server("http://fbu-giftapp.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }

}
