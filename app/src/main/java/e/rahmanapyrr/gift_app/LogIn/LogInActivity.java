package e.rahmanapyrr.gift_app.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import e.rahmanapyrr.gift_app.Friends.AddFriends;
import e.rahmanapyrr.gift_app.R;

public class LogInActivity extends AppCompatActivity {


    private EditText usernameLogin;
    private EditText passwordLogin;
    private TextView registerText;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        usernameLogin = findViewById(R.id.etLoginUsername);
        passwordLogin = findViewById(R.id.etLoginPassword);
        registerText = findViewById(R.id.tvRegisterButton);
        loginButton = findViewById(R.id.bLogin);


        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
               // finish();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = usernameLogin.getText().toString();
                final String password = passwordLogin.getText().toString();

                login(username, password);
            }
        });


    }


    private void login(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login Successful!");

                    Toast.makeText(LogInActivity.super.getBaseContext(), "LOGIN SUCCESS!", Toast.LENGTH_LONG).show();

                    System.out.println("Working BISADFJOIAF");


//                    final Intent intent = new Intent(LogInActivity.this, LinkActivity.class);
//                    startActivity(intent);
//                    finish();



                    final Intent intent = new Intent(LogInActivity.this, AddFriends.class);
                    startActivity(intent);
                    finish();

                } else {
                    Log.e("LoginActivity", "Login Failure");
                    Toast.makeText(LogInActivity.super.getBaseContext(), "Login Failure", Toast.LENGTH_SHORT).show();
                    System.out.println("FAILINGGGG");
                    e.printStackTrace();
                }
            }
        });


    }


    private void register() {
        final Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


}
