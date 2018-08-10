package e.rahmanapyrr.gift_app.Friends;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

import e.rahmanapyrr.gift_app.ProfileActivity;
import e.rahmanapyrr.gift_app.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class CurrentUserFriendsAdapter extends RecyclerView.Adapter<CurrentUserFriendsAdapter.ViewHolder> {

    private List<ParseUser> CurrFriends;
    Context context;

    public CurrentUserFriendsAdapter(List<ParseUser> friends) {
        this.CurrFriends = friends;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View friendView = inflater.inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(friendView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ParseUser user = CurrFriends.get(i);
        ParseUser friend = CurrFriends.get(i);

        viewHolder.Username.setText(friend.get("firstname") + " " + friend.get("lastname"));

        final ParseFile pic = user.getParseFile("ProfilePic");
        if(pic != null){
            Glide.with(context).load(pic.getUrl())
                    .apply(bitmapTransform(new CircleCrop()))
                    .into(viewHolder.profilePic);
        }
    }

    @Override
    public int getItemCount() {
        return CurrFriends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView Username;
        public TextView birthday;
        public ImageView profilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Username = (TextView)itemView.findViewById(R.id.currentFriendName);

            profilePic = itemView.findViewById(R.id.profile1);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION){
            ParseUser user = CurrFriends.get(position);
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("username", (user.get("firstname") + " " + user.get("lastname")));
                //i.putExtra("photo", user.getImage().getUrl());
                i.putExtra("firstname", user.getString("firstname"));
                i.putExtra("lastname", user.getString("lastname"));
                i.putExtra("birthday", user.getString("birthdayString"));

                context.startActivity(i);
            }
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        CurrFriends.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<ParseUser> list) {
        CurrFriends.addAll(list);
        notifyDataSetChanged();
    }
}
