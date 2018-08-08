package e.rahmanapyrr.gift_app.Profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.List;

import e.rahmanapyrr.gift_app.R;
import e.rahmanapyrr.gift_app.models.Favorites;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{

    List<Favorites> myFav;
    Context context;

    public FavoritesAdapter(List<Favorites> favorites) {
        this.myFav = favorites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View favView = inflater.inflate(R.layout.single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(favView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Favorites fav = myFav.get(i);
        viewHolder.item.setText(fav.getItem());
        int count = fav.getCounter();
        viewHolder.counter.setText(Integer.toString(count));
    }

    @Override
    public int getItemCount() {
        return myFav.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView item;
        public TextView counter;
        public ImageView upvote;
        public ImageView downvote;
        int upclicks = 0;
        int downclicks = 0;

        public ViewHolder(View itemView){
            super(itemView);
            item = itemView.findViewById(R.id.itemTv);
            counter = itemView.findViewById(R.id.counterTv);
            upvote = itemView.findViewById(R.id.upArrowIv);
            downvote = itemView.findViewById(R.id.downArrowIv);

            upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Favorites fav = myFav.get(position);
                    if(upclicks == 0 && downclicks == 0) {
                        upvote.setImageResource(R.drawable.upfull);
                        CharSequence temp = counter.getText();
                        int currentCount = Integer.parseInt(temp.toString());
                        currentCount++;
                        counter.setText(Integer.toString(currentCount));
                        fav.upVote();
                        fav.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    Log.d("FavoritesAdapter", "Upvote successful");
                            }
                            else{
                                    e.printStackTrace();
                                }
                        }

                    });
                        upclicks++;
                    }
                    else if(upclicks == 0 && downclicks == 1){
                        downvote.setImageResource(R.drawable.downempty);
                        CharSequence temp = counter.getText();
                        int currentCount = Integer.parseInt(temp.toString());
                        currentCount++;
                        counter.setText(Integer.toString(currentCount));
                        fav.upVote();
                        fav.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    Log.d("FavoritesAdapter", "Upvote successful");
                                }
                                else{
                                    e.printStackTrace();
                                }
                            }

                        });
                        downclicks = 0;
                    }
                }
            });

            downvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Favorites fav = myFav.get(position);
                    if(downclicks == 0 && upclicks == 0){
                        downvote.setImageResource(R.drawable.downfull);
                        CharSequence temp = counter.getText();
                        int currentCount = Integer.parseInt(temp.toString());
                        currentCount--;
                        counter.setText(Integer.toString(currentCount));
                        fav.downVote();
                        fav.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    Log.d("FavoritesAdapter", "Downvote successful");
                                }
                                else{
                                    e.printStackTrace();
                                }
                            }

                        });
                        downclicks++;
                    }
                    else if(downclicks == 0 && upclicks == 1){
                        upvote.setImageResource(R.drawable.upempty);
                        CharSequence temp = counter.getText();
                        int currentCount = Integer.parseInt(temp.toString());
                        currentCount--;
                        counter.setText(Integer.toString(currentCount));
                        fav.downVote();
                        fav.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    Log.d("FavoritesAdapter", "Downvote successful");
                                }
                                else{
                                    e.printStackTrace();
                                }
                            }

                        });
                        upclicks = 0;
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void clear() {
        myFav.clear();
        notifyDataSetChanged();
    }
}
