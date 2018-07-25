package e.rahmanapyrr.gift_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.List;

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
        ParseUser friend = CurrFriends.get(i);
        viewHolder.Username.setText(friend.getUsername());
    }

    @Override
    public int getItemCount() {
        return CurrFriends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView Username;
        public TextView birthday;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Username = (TextView)itemView.findViewById(R.id.currentFriendName);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                ParseUser user = CurrFriends.get(position);
                Intent i = new Intent(context, ProfileActivity.class);
                i.putExtra("username", user.getUsername());
                //i.putExtra("photo", user.getImage().getUrl());
                i.putExtra("firstname", user.getString("firstname"));
                i.putExtra("lastname", user.getString("lastname"));
                i.putExtra("birthday", user.getString("birthdayString"));
                /*
                if(post.getProfilePic() != null) {
                    i.putExtra("icon", post.getProfilePic().getUrl());
                }
                else{
                    i.putExtra("icon", "");
                }
                */
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
