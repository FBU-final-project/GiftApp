package e.rahmanapyrr.gift_app.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

        @ParseClassName("Favorites")
public class Favorites extends ParseObject{
            public static final String KEY_USER = "user";
    public static final String KEY_ITEM = "item";
    public static final String KEY_COUNTER = "counter";

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
        }

        public String getItem(){ return getString(KEY_ITEM);}

        public int getCounter(){ return getInt(KEY_COUNTER);}

        public static class Query extends ParseQuery<Favorites> {
        public Query(){
            super(Favorites.class);
            }

            public Favorites.Query getTop(){
                setLimit(20);
                return this;
            }

            public Favorites.Query withUser(){
                include("user");
                return this;
                }

            }
}
