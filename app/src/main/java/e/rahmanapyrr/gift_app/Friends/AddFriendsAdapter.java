package e.rahmanapyrr.gift_app.Friends;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import e.rahmanapyrr.gift_app.Profile.ProfileActivity;
import e.rahmanapyrr.gift_app.R;
import e.rahmanapyrr.gift_app.models.User;

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.ViewHolder> {

    private List<User> Users;

    Context context;

    public AddFriendsAdapter(List<User> users) {
        this.Users = users;
    }

    @Override
    public AddFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View friendView = inflater.inflate(R.layout.item_friend_option, parent, false);

        return new AddFriendsAdapter.ViewHolder(friendView);
    }

    @Override
    public void onBindViewHolder(AddFriendsAdapter.ViewHolder holder, int position) {
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

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            Friend = (TextView) itemView.findViewById(R.id.friendNameOption);
            AddFriendbtn = (Button) itemView.findViewById(R.id.addFriendbtn);

            ///add friends///-----------------------------------
            AddFriendbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ParseUser user = Users.get(getAdapterPosition());
                    ParseUser currentUserClass = ParseUser.getCurrentUser();

                    ParseRelation<ParseUser> friends = currentUserClass.getRelation("FriendRelation");
                    friends.add(user);
                    currentUserClass.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d("Friend", "New friend added: " + user.getUsername());
                                } else {
                                Log.d("Friend", "Error: " + e.getMessage());
                                }

                                }
                                });
                    }

                                                ///button to add friends------------------------------------------
            });

        //when the user clicks on a row, show MovieDetailsActivity for the selected movie
                    itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                ParseUser user = Users.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, ProfileActivity.class);
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


