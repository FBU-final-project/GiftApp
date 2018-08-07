package e.rahmanapyrr.gift_app;

import com.parse.ParseClassName;
        import com.parse.ParseQuery;
        import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {

    private static final String KEY_BIRTHDAY = "birthdayString";

    public String getBirthday() { return getString(KEY_BIRTHDAY);}

    public void setBirthday(String birthday) { put(KEY_BIRTHDAY, birthday);}


    public static class Query extends ParseQuery<User> {

        public Query() {
            super(User.class);
        }

        public Query getTop(){
            setLimit(20);
            orderByDescending("createdAt");
            return this;
        }

        public Query withUser(){
            include("user");
            return this;
        }
    }
}
