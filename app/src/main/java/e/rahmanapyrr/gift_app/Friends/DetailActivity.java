package e.rahmanapyrr.gift_app.Friends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import e.rahmanapyrr.gift_app.R;

public class DetailActivity extends AppCompatActivity{

    private ImageView photo;
    private TextView caption;
    private TextView username;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        photo = (ImageView)findViewById(R.id.image_detail);
        caption = (TextView) findViewById(R.id.description_detail);
        username = (TextView) findViewById(R.id.username_detail);
        time = (TextView) findViewById(R.id.time_detail);

        String myCaption = getIntent().getStringExtra("description");
        caption.setText(myCaption);

        String myName = getIntent().getStringExtra("username");
        username.setText(myName);

        String myUrl = getIntent().getStringExtra("photo");
        Glide.with(this).load(myUrl)
                .into(photo);

        String myTime = getIntent().getStringExtra("time");
        time.setText(myTime);
    }
}
