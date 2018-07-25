package e.rahmanapyrr.gift_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class CurrentUserFriends extends AppCompatActivity {
    ArrayList<ParseUser> friends;
    CurrentUserFriendsAdapter adapter;
    RecyclerView rvcurrentFriends;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);

        friends = new ArrayList<>();

        adapter = new CurrentUserFriendsAdapter(friends);
        rvcurrentFriends = findViewById(R.id.rvContent);
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


    public void goAddFriends(View view) {
        Intent i = new Intent(CurrentUserFriends.this, AddFriends.class);
        startActivity(i);
        finish();
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.navigation, menu);
//    return true;
//}
}
