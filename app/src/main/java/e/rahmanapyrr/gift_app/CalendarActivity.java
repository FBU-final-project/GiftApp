package e.rahmanapyrr.gift_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    List<List<String>> birthdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        birthdays = new ArrayList<List<String>>();
        List<String> n = new ArrayList<>();
        n.add("9 21");
        n.add("Konce's birthday");
        List<String> b = new ArrayList<>();
        b.add("7 29");
        b.add("Grace's birthday");
        System.out.println(n);
        System.out.println(b);
        birthdays.add(n);
        birthdays.add(b);
        System.out.println(birthdays);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime())
                .withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                boolean event = false;
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                String selectedDate = "" + (calSelected.get(Calendar.MONTH) + 1)
                        + " " + calSelected.get(Calendar.DAY_OF_MONTH);
                        //+ " " + calSelected.get(Calendar.YEAR);

                for(List<String> s: birthdays) {
                    if (s.get(0).equals(selectedDate)) {
                        event = true;
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
}
