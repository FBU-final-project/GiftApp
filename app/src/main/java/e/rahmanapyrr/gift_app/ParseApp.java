package e.rahmanapyrr.gift_app;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // used whenever creating a new class like our posts in the instagram app
        //ParseObject.registerSubclass(Post.class);


        // RAHMANA IS REGISTERING ANOTHER SUBCLASS HERE AND WHEN SHE DOES I THINK THE CODEBASE GETS CONFUSED WHEN I TRY TO SIGNUP A NEW USER
        // SO WE NEED TO RENAME THIS OR DO SOMETHING BECAUSE IF I UNCOMMENT IT, IT EFFS EVERYTHING UP
        // ParseObject.registerSubclass(User.class);


        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("giftapp")
                .clientKey("fbuniversity")
                .server("http://fbu-giftapp.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
