package e.rahmanapyrr.gift_app.Calendar;

import android.os.Bundle;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import e.rahmanapyrr.gift_app.AppBaseActivity;
import e.rahmanapyrr.gift_app.R;
import e.rahmanapyrr.gift_app.models.User;

public class CalendarActivity extends AppBaseActivity {

    private static final String AUTHORITY = "e.rahmanapyrr.gift_app";

    List<List<String>> events;
    Collection<Date> allDates;
    ArrayList<User> allUsers;
    Date today;
    CalendarPickerView datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //list of events to add to Calendar with toasts
        events = new ArrayList<List<String>>();
        //list of dates to highlight in the Calendar
        allDates = new ArrayList<>();
        //list of all Users gotten from the ParseQuery
        allUsers = new ArrayList<>();

        today = new Date();
        System.out.println(today);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                .withSelectedDate(today);

        getParseEvents();

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                boolean event = false;
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                String selectedDate = "" + (calSelected.get(Calendar.MONTH) + 1)
                        + " " + calSelected.get(Calendar.DAY_OF_MONTH);

                System.out.println(events);
                for(List<String> s: events) {
                    if (s.get(0).equals(selectedDate)) {
                        event = true;
                        Collection<Date> d = new ArrayList<>();
                        d.add(date);
                        datePicker.highlightDates(d);
                        Toast.makeText(CalendarActivity.this, s.get(1), Toast.LENGTH_LONG).show();
                    }
                }
                if(!event) {
                    Toast.makeText(CalendarActivity.this, "No events on this day", Toast.LENGTH_SHORT).show();
                }

                }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    //adds event to calendar
    public void addEvent(String day, String name){
        List<String> temp = new ArrayList<>();
        temp.add(day);
        temp.add(name);
        events.add(temp);
    }

    //converts date from "1 10" format to "Jan 10 2018" format
    //adds this event to the allDates ArrayList which will be added to calendar
    public void convertDate(String day, Date today){
        String[] elements = day.split(" ");
        String monthNum = elements[0];
        int month = Integer.parseInt(monthNum);
        String dayz = elements[1];
        int days = Integer.parseInt(dayz);
        Date event;
        if(today.getMonth() > month) {
            event = new Date(119, month - 1, days);
        }
        else if(today.getMonth() == (month-1) && today.getDate() > days){
            event = new Date(119, month - 1, days);
        }
        else{
            event = new Date(118, month - 1, days);
        }
        allDates.add(event);
        datePicker.highlightDates(allDates);
    }

    //converts date from "Mar 4, 2005" format to "3 4" format
    public String convertToSimple(String date){
        String[] elements = date.split(" ");
        String month = elements[0];
        String dayz = elements[1];
        dayz = dayz.substring(0, dayz.length()-1);
        String newMonth = "";
        switch(month){
            case("Jan"):
                newMonth = "1";
                break;
            case("Feb"):
                newMonth = "2";
                break;
            case("Mar"):
                newMonth = "3";
                break;
            case("Apr"):
                newMonth = "4";
                break;
            case("May"):
                newMonth = "5";
                break;
            case("Jun"):
                newMonth = "6";
                break;
            case("Jul"):
                newMonth = "7";
                break;
            case("Aug"):
                newMonth = "8";
                break;
            case("Sep"):
                newMonth = "9";
                break;
            case("Oct"):
                newMonth = "10";
                break;
            case("Nov"):
                newMonth = "11";
                break;
            case("Dec"):
                newMonth = "12";
                break;
            default:
                newMonth = "";
        }
        return newMonth +" " + dayz;
    }





    public void getParseEvents() {
        final User.Query userQuery = new User.Query();
        userQuery.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> objects, ParseException e) {
                if (e == null) {
                    allUsers.clear();
                    allUsers.addAll(objects);
                    for(int i = 0; i < allUsers.size(); i++){
                        ParseUser temp = allUsers.get(i);
                        String birth = temp.getString("birthdayString");
                        String name = temp.getString("firstname");
                        String x = convertToSimple(birth);
                        convertDate(x, today);
                        addEvent(x, name + "'s birthday");
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
