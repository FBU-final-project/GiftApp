package e.rahmanapyrr.gift_app.Friends;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import e.rahmanapyrr.gift_app.R;
import e.rahmanapyrr.gift_app.models.Post;
import e.rahmanapyrr.gift_app.models.User;


public class AddFriends extends AppCompatActivity{
    ArrayList<User> users;
    AddFriendsAdapter adapter;
    RecyclerView rvfriendNameOption;
    Bitmap.Config config;
//    private SwipeRefreshLayout swipeContainer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_option_list);

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

    public void goBack(View view){
        Intent i = new Intent(AddFriends.this, CurrentUserFriends.class);
        startActivity(i);
        finish();

    }

    public static class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.ViewHolder> {

            private List<User> Users;

            Context context;

            public AddFriendsAdapter(List<User> users) {
                this.Users = users;
            }

            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);

                View friendView = inflater.inflate(R.layout.item_friend_option, parent, false);

                return new ViewHolder(friendView);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                // get data
                ParseUser user = Users.get(position);

                //String myTime = TimeFormat.getTimeDifference(user.getCreatedAt().toString());
    //            holder.time.setText(myTime);
                //holder.tvDate.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));

                holder.Friend.setText(user.getUsername());
            }

            @Override
            public int getItemCount() {
                return Users.size();
            }

        public void AddFriendFunc(View view) {
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                public TextView Friend;
                public Button AddFriendbtn;


                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    Friend = (TextView)itemView.findViewById(R.id.friendNameOption);
                    AddFriendbtn = (Button) itemView.findViewById(R.id.addFriendbtn);

    ///add friends///-----------------------------------
                   AddFriendbtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           final ParseUser user = Users.get(getAdapterPosition());

                           final ArrayList<Post> posts;
                           posts = new ArrayList<>();

                           final Post.Query postQuery = new Post.Query();
                           postQuery.whereEqualTo("user", ParseUser.getCurrentUser());

                           postQuery.orderByDescending("createdAt").findInBackground(new FindCallback<Post>() {
                               @Override
                               public void done(List<Post> objects, ParseException e) {
                                   if (e == null) {
                                       posts.addAll(objects);
                                       Post currentUserClass = posts.get(0);

                                       try {
                                           System.out.println("Hey" + currentUserClass.getUser().fetchIfNeeded().getUsername());
                                       } catch (ParseException e1) {
                                           e1.printStackTrace();
                                       }
                                       System.out.println("hey " + user.getUsername());

                                       currentUserClass.addUnique("friends_array", user);
                                       currentUserClass.saveEventually();

                                       ParseRelation<ParseUser> friends = currentUserClass.getRelation("FriendRelation");
                                       friends.add(user);
                                       currentUserClass.saveInBackground(new SaveCallback() {
                                           @Override
                                           public void done(ParseException e) {
                                               if (e == null) {
                                                   Log.d("dept", "saved");
                                               } else {
                                                   Log.d("dept", "Error: " + e.getMessage());
                                               }

                                           }
                                       });

                                   } else {
                                       e.printStackTrace();
                                   }
                               }
                           });

                       }
                   });
     ///button to add friends------------------------------------------


                    itemView.setOnClickListener(this);
                }

                //when the user clicks on a row, show MovieDetailsActivity for the selected movie
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    // make sure the position is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        // get the movie at the position, this won't work if the class is static
                        ParseUser user = Users.get(position);
                        // create intent for the new activity
                        Intent intent = new Intent(context, DetailActivity.class);
                        // serialize the movie using parceler, use its short name as a key

                        intent.putExtra("username", user.getUsername());
                        context.startActivity(intent);
                    }

                }
            }

            // Clean all elements of the recycler
            public void clear() {
                Users.clear();
                notifyDataSetChanged();
            }

            // Add a list of items -- change to type used
            public void addAll(List<User> list) {
                Users.addAll(list);
                notifyDataSetChanged();
            }

        }
}