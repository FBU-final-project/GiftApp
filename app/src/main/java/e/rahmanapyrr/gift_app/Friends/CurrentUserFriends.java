package e.rahmanapyrr.gift_app.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import e.rahmanapyrr.gift_app.Calendar.CalendarActivity;
import e.rahmanapyrr.gift_app.R;
import e.rahmanapyrr.gift_app.models.Post;


public class CurrentUserFriends extends AppCompatActivity {
    ArrayList<ParseUser> friends;
    CurrentUserFriendsAdapter adapter;
    RecyclerView rvcurrentFriends;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_friends);

        dl = (DrawerLayout) findViewById(R.id.activityCurrentFriends);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id)
                {
                    case R.id.navAddFriends:
                        Toast.makeText(CurrentUserFriends.this, "Add Friends", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CurrentUserFriends.this, AddFriends.class);
                        startActivity(i);
                        finish();
                    case R.id.navCalender:
                        Toast.makeText(CurrentUserFriends.this, "Calender", Toast.LENGTH_SHORT).show();
                        Intent o = new Intent(CurrentUserFriends.this, CalendarActivity.class);
                        startActivity(o);
                        finish();
                    case R.id.navCurrentFriends:
                        Toast.makeText(CurrentUserFriends.this, "My Friends", Toast.LENGTH_SHORT).show();
                    default:
                        return true;
                }
            }
        });

        friends = new ArrayList<>();

        adapter = new CurrentUserFriendsAdapter(friends);
        rvcurrentFriends = findViewById(R.id.rvcurrentFriends);
        rvcurrentFriends.setLayoutManager(new LinearLayoutManager(this));
        rvcurrentFriends.setAdapter(adapter);
        populateTimeline();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    public void populateTimeline() {
//        final Post.Query postQuery = new Post.Query();
//        postQuery.whereEqualTo("user", ParseUser.getCurrentUser());
////        final ArrayList<Post> posts;
////        posts = new ArrayList<>();
//        postQuery.orderByDescending("createdAt").findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> objects, com.parse.ParseException e) {
//                if (e == null) {
//                    final ArrayList<Post> posts;
//                    posts = new ArrayList<>();
//                    posts.addAll(objects);
//                    Post currentUserClass = posts.get(0);
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

