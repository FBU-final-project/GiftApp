package e.rahmanapyrr.gift_app.LogIn;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.DateFormat;
import java.util.Calendar;

import e.rahmanapyrr.gift_app.R;

public class RegisterBirthdayActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private Spinner dateSpinner;
    private Button finishButton;
    private Button dateSelectorButton;
    private TextView selectedDate;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_birthday);


        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        firstname = getIntent().getStringExtra("firstname");
        lastname = getIntent().getStringExtra("lastname");


        System.out.println("USERNAME: " + username);
        System.out.println("PASSWORD: " + password);
        System.out.println("FIRST NAME: " + firstname);
        System.out.println("LAST NAME: " + lastname);


        dateSpinner = (Spinner)findViewById(R.id.sBirthdate);
        finishButton = findViewById(R.id.registerFinish);
        dateSelectorButton = findViewById(R.id.bDateSelector);
        selectedDate = findViewById(R.id.tvCurrentDate);



        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*final Intent intent = new Intent(RegisterBirthdayActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
                */

                SignUp(username, password, firstname, lastname, selectedDate.getText().toString());

            }
        });



        View.OnTouchListener Button_OnTouch = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    showDatePickerDialog(view);
                }
                return true;
            }
        };

        View.OnKeyListener Button_OnKey = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DPAD_CENTER){
                    showDatePickerDialog(view);
                    return true;
                } else {
                    return false;
                }
            }
        };

        dateSelectorButton.setOnTouchListener(Button_OnTouch);
        dateSelectorButton.setOnKeyListener(Button_OnKey);


        // FOR THE SPINNER


        View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                if (event.getAction() == MotionEvent.ACTION_UP){
                    showDatePickerDialog(v);
                }
                return true;
            }
        };

        View.OnKeyListener Spinner_OnKey = new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
                    showDatePickerDialog(v);
                    return true;
                } else {
                    return false;
                }
            }
        };

        dateSpinner.setOnTouchListener(Spinner_OnTouch);
        dateSpinner.setOnKeyListener(Spinner_OnKey);




    }


    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Date");
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        System.out.println(currentDateString);
        selectedDate.setText(currentDateString);
    }








    private void SignUp(String username, String password, String firstname, String lastname, String birthday){
        // Create a Parse User
        ParseUser user = new ParseUser();

        // Set the core properties
        user.setUsername(username);
        user.setPassword(password);
        user.put("firstname", firstname);
        user.put("lastname", lastname);
        user.put("birthdayString", selectedDate.getText().toString());

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    // Hooray! User registered
                    Log.d("RegisterActivity", "Congrats you're Registered!");

                    final Intent intent = new Intent(RegisterBirthdayActivity.this, LogInActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterBirthdayActivity.super.getBaseContext(), "REGISTERED!", Toast.LENGTH_SHORT);
                    finish();


                } else {
                    // Signup didn't succeed
                    Log.e("RegisterActivity", "Registration FAILED!");
                    Toast.makeText(RegisterBirthdayActivity.super.getBaseContext(), "FAIL", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        });

    }





}
