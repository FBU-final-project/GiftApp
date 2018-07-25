package e.rahmanapyrr.gift_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView myName;
    TextView myUsername;
    TextView myBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myName = findViewById(R.id.nameTv);
        myUsername = findViewById(R.id.usernameTv);
        myBirthday = findViewById(R.id.birthdayTv);

        String firstName = getIntent().getStringExtra("firstname");
        String lastName = getIntent().getStringExtra("lastname");
        String fullName = firstName + " " + lastName;
        myName.setText(fullName);

        String username = getIntent().getStringExtra("username");
        myUsername.setText(username);

        String birthday = getIntent().getStringExtra("birthday");
        myBirthday.setText(birthday);

    }
}
