package e.rahmanapyrr.gift_app.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import e.rahmanapyrr.gift_app.R;

public class RegisterNameActivity extends AppCompatActivity {

    private Button continueNameButton;
    private String username;
    private String password;
    private EditText etFirstName;
    private EditText etLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);


        continueNameButton = findViewById(R.id.bRegisterName);


        continueNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RegisterNameActivity.this, RegisterBirthdayActivity.class);

                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();

                intent.putExtra("firstname", firstName);
                intent.putExtra("lastname", lastName);

                intent.putExtra("username", username);
                intent.putExtra("password", password);

                startActivity(intent);
                finish();
            }
        });


    }
}
