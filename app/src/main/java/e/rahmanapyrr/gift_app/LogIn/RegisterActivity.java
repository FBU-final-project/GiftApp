package e.rahmanapyrr.gift_app.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import e.rahmanapyrr.gift_app.R;

public class RegisterActivity extends AppCompatActivity {


    private Button continueButton;
    private EditText createUsername;
    private EditText createPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        continueButton = findViewById(R.id.registerContinue);
        createUsername = findViewById(R.id.registerUsername);
        createPassword = findViewById(R.id.registerPassword);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidCredentials();
            }
        });


    }


    private boolean checkValidCredentials() {

        if (createUsername.getText().toString().matches("") || createPassword.getText().toString().matches("")) {
            Toast.makeText(RegisterActivity.super.getBaseContext(), "Enter Valid username/password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            final Intent intent = new Intent(RegisterActivity.this, RegisterNameActivity.class);
            String username = createUsername.getText().toString();
            String password = createPassword.getText().toString();
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
            return true;
        }
    }


    public void goBack() {
        // closes the activity and returns to login screen
        this.finish();
    }


}
