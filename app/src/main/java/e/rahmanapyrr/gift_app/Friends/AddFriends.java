package e.rahmanapyrr.gift_app.Friends;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import e.rahmanapyrr.gift_app.AppBaseActivity;
import e.rahmanapyrr.gift_app.Calendar.CalendarActivity;
import e.rahmanapyrr.gift_app.R;
import e.rahmanapyrr.gift_app.models.User;

import e.rahmanapyrr.gift_app.FCMMessageHandler.*;

public class AddFriends extends AppBaseActivity {
    ArrayList<User> users;
    AddFriendsAdapter adapter;
    RecyclerView rvfriendNameOption;
    Bitmap.Config config;
    //    private SwipeRefreshLayout swipeContainer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        users = new ArrayList<>();



        adapter = new AddFriendsAdapter(users);
        rvfriendNameOption = findViewById(R.id.rvfriendNameOption);
        rvfriendNameOption.setLayoutManager(new LinearLayoutManager(this));
        rvfriendNameOption.setAdapter(adapter);

//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.timeline);
//
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                fetchTimelineAsync(0);
//            }
//        });
//
//        swipeContainer.setColorSchemeResources(
//                android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        populateTimeline();
    }


    public void fetchTimelineAsync(int page) {
        adapter.clear();
        populateTimeline();
//        swipeContainer.setRefreshing(false);
    }

    public void populateTimeline() {
//
        final User.Query userQuery = new User.Query();
//        userQuery.whereEqualTo("user", ParseUser.getCurrentUser());

        userQuery.orderByDescending("createdAt").findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> objects, ParseException e) {
                if (e == null) {
                    users.clear();
                    users.addAll(objects);
                    adapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });

    }


}

