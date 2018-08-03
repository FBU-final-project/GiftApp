package e.rahmanapyrr.gift_app.Profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import e.rahmanapyrr.gift_app.R;
import e.rahmanapyrr.gift_app.models.Favorites;
import e.rahmanapyrr.gift_app.models.User;

public class ProfileActivity extends AppCompatActivity {

    TextView myName;
    TextView myUsername;
    TextView myBirthday;
    RecyclerView feed;
    FavoritesAdapter favAdapter;
    ArrayList<Favorites> faves;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseObject.registerSubclass(Favorites.class);
        setContentView(R.layout.activity_profile);

        myName = findViewById(R.id.nameTv);
        myUsername = findViewById(R.id.usernameTv);
        myBirthday = findViewById(R.id.birthdayTv);

        feed = findViewById(R.id.profileRv);
        faves = new ArrayList<>();
        favAdapter = new FavoritesAdapter(faves);
        feed.setLayoutManager(new LinearLayoutManager(this));
        feed.setAdapter(favAdapter);

        String firstName = getIntent().getStringExtra("firstname");
        String lastName = getIntent().getStringExtra("lastname");
        String fullName = firstName + " " + lastName;
        myName.setText(fullName);

        String username = getIntent().getStringExtra("username");
        myUsername.setText(username);

        String birthday = getIntent().getStringExtra("birthday");
        myBirthday.setText(birthday);

        user = getIntent().getParcelableExtra("parseuser");

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
