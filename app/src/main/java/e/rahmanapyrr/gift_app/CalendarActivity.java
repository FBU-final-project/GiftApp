package e.rahmanapyrr.gift_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    List<List<String>> events;
    Collection<Date> allDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        events = new ArrayList<List<String>>();
        allDates = new ArrayList<>();

        addEvent("8 12", "Grace's birthday");
        addEvent("2 10", "Konce's birthday");
        addEvent("3 10", "Konce's birthday");
        addEvent("4 10", "Konce's birthday");
        addEvent("5 10", "Konce's birthday");
        addEvent("6 10", "Konce's birthday");
        addEvent("7 10", "Konce's birthday");
        addEvent("8 10", "Konce's birthday");
        addEvent("9 10", "Konce's birthday");
        addEvent("10 10", "Konce's birthday");
        addEvent("11 10", "Konce's birthday");
        addEvent("12 10", "Konce's birthday");

        convertDate("8 12");
        System.out.println(allDates);


        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                  .withSelectedDate(today);

        datePicker.highlightDates(allDates);


        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                boolean event = false;
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                String selectedDate = "" + (calSelected.get(Calendar.MONTH) + 1)
                        + " " + calSelected.get(Calendar.DAY_OF_MONTH);

                for(List<String> s: events) {
                    if (s.get(0).equals(selectedDate)) {
                        event = true;
                        Collection<Date> d = new ArrayList<>();
                        d.add(date);
                        System.out.println(date);
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

    public void addEvent(String day, String name){
        List<String> temp = new ArrayList<>();
        temp.add(day);
        temp.add(name);
        events.add(temp);
    }

    public void convertDate(String day){
        String[] elements = day.split(" ");
        String monthNum = elements[0];
        System.out.println(monthNum);
        String dayz = elements[1];
        System.out.println(dayz);
        Date f = new Date(118, Integer.parseInt(monthNum) - 1, Integer.parseInt(dayz));
        System.out.println(f.toString());
        allDates.add(f);

    }
}
