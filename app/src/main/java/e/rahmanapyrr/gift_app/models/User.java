package e.rahmanapyrr.gift_app.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_USER = "user";
    public static final String KEY_FIRST = "firstname";
    public static final String KEY_LAST = "lastname";

    public String getUsername() {
        return getString(KEY_USERNAME);
    }

    public void setUsername(ParseFile username) {
        put(KEY_USERNAME, username);
    }

    public String getFirstname() {
        return getString(KEY_FIRST);
    }

    public String getLastname() {
        return getString(KEY_LAST);
    }

    private static final String KEY_BIRTHDAY = "birthdayString";

    public String getBirthday() {
        return getString(KEY_BIRTHDAY);
    }

    public void setBirthday(String birthday) {
        put(KEY_BIRTHDAY, birthday);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }


    public User() {
    }

    public static class Query extends ParseQuery<User> {
        public Query() {
            super(User.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }

    }
}

