package e.rahmanapyrr.gift_app;

import android.os.Bundle;
<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
=======
>>>>>>> 05f05729f97979758613495f3298fa799ea00b3b
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
<<<<<<< HEAD
=======

>>>>>>> 05f05729f97979758613495f3298fa799ea00b3b
    }
}
