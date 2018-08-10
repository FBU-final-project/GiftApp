package e.rahmanapyrr.gift_app.Friends;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import e.rahmanapyrr.gift_app.AppBaseActivity;
import e.rahmanapyrr.gift_app.R;


public class CurrentUserFriends extends AppBaseActivity {
    ArrayList<ParseUser> friends;
    CurrentUserFriendsAdapter adapter;
    RecyclerView rvcurrentFriends;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_friends);

        friends = new ArrayList<>();
        adapter = new CurrentUserFriendsAdapter(friends);
        rvcurrentFriends = findViewById(R.id.rvcurrentFriends);
        rvcurrentFriends.setLayoutManager(new LinearLayoutManager(this));
        rvcurrentFriends.setAdapter(adapter);
        populateTimeline();
    }


    public void populateTimeline() {
        ParseUser currentUserClass = ParseUser.getCurrentUser();

        final ParseRelation<ParseUser> friend_relations = currentUserClass.getRelation("FriendRelation");
        ParseQuery<ParseUser> friends_list = friend_relations.getQuery();
        friends_list.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, com.parse.ParseException e) {
                if (e == null) {
                    friends.clear();
                    friends.addAll(objects);
                    adapter.notifyDataSetChanged();
                    } else {
                    e.printStackTrace();
                }
            }
        });
    }

}

