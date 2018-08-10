package e.rahmanapyrr.gift_app.Profile;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import e.rahmanapyrr.gift_app.R;
import e.rahmanapyrr.gift_app.models.Favorites;
import e.rahmanapyrr.gift_app.models.User;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ProfileActivity extends AppCompatActivity {

    TextView myName;
    TextView myUsername;
    TextView myBirthday;
    RecyclerView feed;
    FavoritesAdapter favAdapter;
    EditText newItem;
    Button addButton;
    ImageView profilePic;
    ArrayList<Favorites> faves;
    User user;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseObject.registerSubclass(Favorites.class);
        setContentView(R.layout.activity_profile);

        myName = findViewById(R.id.nameTv);
        myUsername = findViewById(R.id.usernameTv);
        myBirthday = findViewById(R.id.birthdayTv);
        newItem = findViewById(R.id.addItemEt);
        addButton = findViewById(R.id.addBtn);
        profilePic = findViewById(R.id.iconIv);

        feed = findViewById(R.id.profileRv);
        faves = new ArrayList<>();
        favAdapter = new FavoritesAdapter(faves);
        feed.setLayoutManager(new LinearLayoutManager(this));
        feed.setAdapter(favAdapter);

        String firstName = getIntent().getStringExtra("firstname");
        String lastName = getIntent().getStringExtra("lastname");
        String fullName = firstName + " " + lastName;
        myName.setText(fullName);

        final String username = getIntent().getStringExtra("username");
        myUsername.setText(username);

        String birthday = getIntent().getStringExtra("birthday");
        myBirthday.setText(birthday);

        user = getIntent().getParcelableExtra("parseuser");

        final ParseFile pic = user.getParseFile("ProfilePic");
        if(pic != null) {
            Glide.with(this).load(pic.getUrl())
                    .apply(bitmapTransform(new CircleCrop()))
                    .into(profilePic);
        }

        populateRV();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newThing = newItem.getText().toString();
                if (newThing.equals("")) {
                    Toast.makeText(ProfileActivity.super.getBaseContext(), "Type an item to add to their goodie bag", Toast.LENGTH_LONG).show();
                    return;
                }
                final ParseRelation<Favorites> fav_relations = user.getRelation("FavoritesRelation");
                ParseQuery<Favorites> fav_list = fav_relations.getQuery();
                fav_list.findInBackground(new FindCallback<Favorites>() {
                    @Override
                    public void done(List<Favorites> objects, ParseException e) {
                        for(Favorites f: objects){
                            if(f.getItem() == null || f.getItem().equals("")){
                                f.setItem(newThing);
                                favAdapter.notifyDataSetChanged();
                                f.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e==null){
                                            Log.d("ProfileActivity", "New item added to goodie bag");
                                            fetchTimelineAsync();
                                        }
                                        else{
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                return;
                            }
                        }
                    }
                });
            }
        });
    }

    public void fetchTimelineAsync(){
        favAdapter.clear();
        populateRV();
    }

    public void populateRV(){
        ParseUser currentUser = user;
        final ParseRelation<Favorites> fav_relations = currentUser.getRelation("FavoritesRelation");
        ParseQuery<Favorites> fav_list = fav_relations.getQuery();
        fav_list.orderByDescending("counter");
        fav_list.findInBackground(new FindCallback<Favorites>() {
            @Override
            public void done(List<Favorites> objects, com.parse.ParseException e) {
                if (e == null) {
                    faves.clear();
                    faves.addAll(objects);
                    favAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
