package com.shivani.rota;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    private static final int ADD_NOTE = 44;
    private TextView textView1, textView2;
    private Button button;
    private ImageView imageView1, imageView2;
    int month,day,year;
    String b;
    int dayOfMonth;
    private  MyRotaDay myRotaDay;
    String monthName;
    int numDays;
    private ArrayList arrayList;
    private String[] s1 = new String[]{"O","G","M1","M2","E1","E2","N1","N2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        textView1 = findViewById(R.id.fromDate);
        textView2 = findViewById(R.id.toDate);
        imageView1 = findViewById(R.id.imageFrom);
        imageView2 = findViewById(R.id.imageTo);
        button = findViewById(R.id.button);
        arrayList = new ArrayList();

        AddCalendarEvent(mCalendarView);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getDate(textView1);
                showDate(textView1);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getDate(textView2);
                //listView.setVisibility(View.GONE);
                showDate(textView2);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //listView.setVisibility( View.VISIBLE);
                try {
                    getDateDifference();
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                mCalendarView.setEvents(mEventDays);

            }
        });



    }

    private void showDate(final TextView textView){
        final Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                cal.set(year1, month1, day1);
                String dateString = sdf.format(cal.getTime());
                textView.setText(dateString);
//              textView.setText(day1 + " " + (month1 + 1 ) + " " + year1);
                System.out.println(">>>>>>>>>>. " + dateString);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void getDateDifference() throws OutOfDateRangeException {


//
//      adapter.clear();
//      adapter.notifyDataSetChanged();
//
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat myDate = new SimpleDateFormat("dd");
        SimpleDateFormat myMonth = new SimpleDateFormat("MMMM", Locale.ENGLISH);

        String dateBeforeString = textView1.getText().toString();
        String dateAfterString = textView2.getText().toString();


        try {
            Date dateBefore = myFormat.parse(dateBeforeString);
            Date dateAfter = myFormat.parse(dateAfterString);

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(dateAfter);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            //monthOfYear = calendar.get(Calendar.MONTH);

            monthName = myMonth.format(dateAfter);
            numDays = calendar.getActualMaximum(Calendar.DATE);

            System.out.println(">>>>>>>>> float: date final day "+ monthName );
            System.out.println(">>>>>>>>> float: number of day in month "+ numDays );

            long difference = dateAfter.getTime() - dateBefore.getTime();
            float daysBetween = (difference / (1000*60*60*24));
            /* You can also convert the milliseconds to days using this method
             * float daysBetween =
             *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
             */



            for (int i = (dayOfMonth-1); i<numDays; i++) {
                float remind = daysBetween % 8;
                int c = Math.round(remind);

                if (c == 7) {
                    b = s1[0];
                    arrayList.add( b);
                    myRotaDay = new MyRotaDay(mCalendarView.getSelectedDate(),b);
                    mEventDays.add(myRotaDay);

                } else {

                    b = s1[c + 1];
                    arrayList.add( b);
                    myRotaDay = new MyRotaDay(mCalendarView.getSelectedDate(),b);
                    mEventDays.add(myRotaDay);

                }

                daysBetween++;
                System.out.println(">>>>>>>>>Number : "+ mEventDays);
//
//
//
            }
            System.out.println(">>>>>>>>>>. " + arrayList);
            System.out.println(">>>>>>>>> float: "+ b);
            System.out.println(">>>>>>>>>Number of Days between dates: "+daysBetween);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //MyRotaDay myRotaDay = new MyRotaDay(mCalendarView.getCurrentPageDate(),R.drawable.ic_event_note_black_24dp);
        mCalendarView.setDate(myRotaDay.getCalendar());
        mEventDays.add(myRotaDay);
        mCalendarView.setEvents(mEventDays);

        //setRota(dayOfMonth,numDays);
    }

    public void AddCalendarEvent(View view) {
        Calendar calendarEvent = Calendar.getInstance();
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", calendarEvent.getTimeInMillis());
        i.putExtra("allDay", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
        i.putExtra("title", "Calendar Event");
        startActivity(i);
    }

}
