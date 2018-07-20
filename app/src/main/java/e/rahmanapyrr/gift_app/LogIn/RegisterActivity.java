package e.rahmanapyrr.gift_app.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
                final Intent intent = new Intent(RegisterActivity.this, RegisterNameActivity.class);

                String username = createUsername.getText().toString();
                String password = createPassword.getText().toString();
                intent.putExtra("username", username);
                intent.putExtra("password", password);

                startActivity(intent);
                finish();
            }
        });


    }




    private void signUp(String username, String password){
        // Create a Parse User
        ParseUser user = new ParseUser();

        // Set the core Properties
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    // Hooray! Let them use the app now
                    Log.d("SignupActivity", "Congrats, user registered successfully!");
                    // Send user back to login screen

                } else {
                    // Signup didn't succeed. Look at the ParseException to figure out what went wrong
                    Log.e("SignupActivity", "Registration failed");
                    e.printStackTrace();
                }
            }
        });
    }



    public void goBack(){
        // closes the activity and returns to login screen
        this.finish();
    }



}
