package e.rahmanapyrr.gift_app;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // used whenever creating a new class like our posts in the instagram app
        //ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("giftapp")
                .clientKey("fbuniversity")
                .server("http://fbu-giftapp.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
