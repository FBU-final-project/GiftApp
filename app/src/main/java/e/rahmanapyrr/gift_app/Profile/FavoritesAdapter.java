package e.rahmanapyrr.gift_app.Profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        viewHolder.giftIdea.setText(fav.getItem());
        int count = fav.getCounter();
        viewHolder.counter.setText(Integer.toString(count));
    }

    @Override
    public int getItemCount() {
        return myFav.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView giftIdea;
        public TextView counter;
        public ImageView upvote;
        public ImageView downvote;
        public Button buybutton;
        public String linkBase;
        int upclicks = 0;
        int downclicks = 0;

        public ViewHolder(View itemView){
            super(itemView);
            giftIdea = itemView.findViewById(R.id.itemTv);
            counter = itemView.findViewById(R.id.counterTv);
            upvote = itemView.findViewById(R.id.upArrowIv);
            downvote = itemView.findViewById(R.id.downArrowIv);
            buybutton = itemView.findViewById(R.id.profileBuyButton);
            linkBase = "https://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=";

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

            buybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    takeToBuy(view);
                }
            });

        }

        // Take the recommendation from a user's profile and converts it into the ending for a url search
        public String makeSearchUrl(){
            String recommendation = giftIdea.getText().toString();
            String[] recommendationList = recommendation.split(" ");
            String result = "";
            int size = recommendationList.length;
            if(size>1){
                for(int i=0; i<size-1; i++){
                    result += recommendationList[i];
                    result += "+";
                }
                result += recommendationList[size-1];
            } else {
                result += recommendationList[0];
                System.out.println(result);
            }
            return result;
        }

        // Uses users recommendation ID created with makeSearchUrl() and creates a Browser intent that sends
        // user to the search page for that specific item on Amazon
        public void takeToBuy(View view){
            String searchId = makeSearchUrl();
            // puts together the search link base with the actual item id created in makeSearchUrl()
            linkBase = linkBase+searchId;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkBase));
            context.startActivity(browserIntent);
        }




        @Override
        public void onClick(View view) {

        }
    }
}
