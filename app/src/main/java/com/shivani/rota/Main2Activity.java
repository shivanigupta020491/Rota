package com.shivani.rota;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView textViewFrom, textViewTo, calendarYear, calendarMonth;
    private Button button;
    private ImageView imageView1, imageView2, imageLeft, imageRight;
    int month, day, year;
    String b;
    LinearLayout calendarLayout;
    Spinner spinner;
    ArrayList arrayListSpinner;
    String spinnerItem;
    Date dateBefore;
    Date dateAfter;
    int dayValue, monthValue;
    String currentDate;
    int dayOfMonth;
    private MyRotaDay myRotaDay;
    private AlertDialog.Builder builder;
    String monthName, yearName, dayName, nextMonthName;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Date firstDaySelectMont, firstDayNextMont, firstDayPreviousMonth;
    int numDays, nextMonthValue, previousMonthValue;
    private ArrayList<MyData> arrayList;
    private ArrayList<String> dateList;
    private ArrayList<String> shiftList;
    private String[] s1 = new String[]{"O", "G", "M1", "M2", "E1", "E2", "N1", "N2"};
    private String[] eventList = new String[]{"Birthday", "Anniversary", "Other"};
    private Integer[] eventImgeList = new Integer[]{R.drawable.icons8_birthday_cake_64, R.drawable.icons8_wedding_gift_64, R.drawable.ic_favorite_border_black_24dp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
        initiationView();
        setListeners();

    }

    private void initView() {

        textViewFrom = findViewById(R.id.fromDate1);
        textViewTo = findViewById(R.id.toDate1);
        imageView1 = findViewById(R.id.imageFrom1);
        imageView2 = findViewById(R.id.imageTo1);
        button = findViewById(R.id.button1);
        calendarLayout = findViewById(R.id.calendarLayout);
        calendarMonth = findViewById(R.id.monthOfYear);
        //calendarYear = findViewById(R.id.yearOfYear);
        recyclerView = findViewById(R.id.mRecyclerView);
        imageLeft = findViewById(R.id.prevMonthBtn);
        imageRight = findViewById(R.id.nextMonthBtn);
        spinner = findViewById(R.id.yearSpinner);

    }

    private void initiationView(){
        arrayList = new ArrayList<>();
        shiftList = new ArrayList<>();
        dateList = new ArrayList<>();
        arrayListSpinner = new ArrayList();
        builder = new AlertDialog.Builder(this);


        DateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        SimpleDateFormat myYear = new SimpleDateFormat("yyyy");

        Calendar cal = Calendar.getInstance();
        currentDate = sdf.format(cal.getTime());
        System.out.println(">>>>>>> CURRENT DATE " + sdf.format(cal.getTime()));

        try {
            Date localDate = sdf.parse(currentDate);
            yearName = myYear.format(localDate);
            int year = Integer.parseInt(yearName);

            int p = (year - 10);
            int q = (year + 10);
            for(int k = p; k < q; k++){
                arrayListSpinner.add(k);

            }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayListSpinner);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);

            spinner.setOnItemSelectedListener(this);


            System.out.println(">>>>>>>>> float: date final year list " + year + " "+ arrayListSpinner);


        }catch ( Exception e){
            e.printStackTrace();
        }



    }

    private void setListeners() {
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageLeft.setOnClickListener(this);
        imageRight.setOnClickListener(this);
        button.setOnClickListener(this);
        //spinner.setOnItemSelectedListener(this);

    }


    private void showDate(final TextView textView) {
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
        }, year, month, day);
        datePickerDialog.show();
    }

    private void getDateDifference() throws OutOfDateRangeException {

        shiftList.clear();
        dateList.clear();
        calendarLayout.setVisibility(View.VISIBLE);

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat myYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat myMonth = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat myDay = new SimpleDateFormat("EEEE");


        String dateBeforeString = textViewFrom.getText().toString();
        String dateAfterString = textViewTo.getText().toString();


        try {
            dateBefore = myFormat.parse(dateBeforeString);
            dateAfter = myFormat.parse(currentDate);

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(dateAfter);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            //monthOfYear = calendar.get(Calendar.MONTH);

            monthName = myMonth.format(dateAfter);
             //yearName = myYear.format(dateAfter);

            //arrayListSpinner.add(yearName);

            // LocalDate firstOfMonth = dateAfter.getDate("1" );
            numDays = calendar.getActualMaximum(Calendar.DATE);

            firstDaySelectMont = new Date(dateAfter.getYear(), dateAfter.getMonth(), 1);
            dayName = myDay.format(firstDaySelectMont);

            System.out.println(">>>>>>>>> float: date final day " + monthName);
            System.out.println(">>>>>>>>> float: date final day of week " + dayName);
            System.out.println(">>>>>>>>> float: number of day in month " + numDays);
            System.out.println(">>>>>>>>> float: date final day 1 " + firstDaySelectMont);

            long difference = firstDaySelectMont.getTime() - dateBefore.getTime();
            float daysBetween = (difference / (1000 * 60 * 60 * 24));
            /* You can also convert the milliseconds to days using this method
             * float daysBetween =
             *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
             */
            System.out.println(">>>>>>>>> float: day between " + daysBetween);

            calendarMonth.setText(monthName);
            //calendarYear.setText(yearName);

            checkMonthInt();
            checkWeekInt();
            checkMonthName();


            System.out.println(">>>>>>>>> float: day value " + dayValue);

            // loop for get data of selected date

//            for (int i = (dayOfMonth-1); i<numDays; i++) {
//                float remind = daysBetween % 8;
//                int c = Math.round(remind);
//
//                if (c == 7) {
//                    b = s1[0];
//                    shiftList.add(b);
//                    dateList.add(Integer.toString(dayOfMonth));
//
//
//                } else {
//                    b = s1[c + 1];
//                    shiftList.add(b);
//                    dateList.add(Integer.toString(dayOfMonth));
//                }

            for (int j = 0; j < 7; j++) {
                if (j == dayValue) {

                    // loop for get data from selected date mont 01 date
                    for (int i = 1; i < (numDays + 1); i++) {
                        float remind = daysBetween % 8;
                        int c =  Math.abs(Math.round(remind));

                        if (c == 7) {
                            b = s1[0];
                            shiftList.add(b);
                            dateList.add(Integer.toString(i));


                        } else {
                            b = s1[c + 1];
                            shiftList.add(b);
                            dateList.add(Integer.toString(i));
                        }
                        daysBetween++;
                        dayOfMonth++;

                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerAdapter = new RecyclerAdapter(this, shiftList, dateList);
                        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerAdapter.notifyDataSetChanged();

                    }

                } else {
                    shiftList.add("");
                    dateList.add("");
                }

                System.out.println(">>>>>>>>>Number : " + shiftList);
                System.out.println(">>>>>>>>>Number : " + dateList);


            }

            System.out.println(">>>>>>>>> float: " + b);
            System.out.println(">>>>>>>>>Number of Days between dates: " + daysBetween);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.imageFrom1: {
                showDate(textViewFrom);
                //calendarLayout.setVisibility(View.GONE);
                break;
            }
            case R.id.imageTo1: {
                showDate(textViewTo);
                break;
            }

            case R.id.button1: {
                try {

                    getDateDifference();
                    //recyclerAdapter.notifyDataSetChanged();
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
                break;
            }

            case R.id.prevMonthBtn: {
                previusMonthData();
               // Toast.makeText(this, "Previous", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.nextMonthBtn: {
                nextMonthData();
               // Toast.makeText(this, "next", Toast.LENGTH_SHORT).show();
                break;
            }
        }

    }

    private void checkMonthInt() {

        if (monthName.equalsIgnoreCase("January")) {
            monthValue = 0;

        } else if (monthName.equalsIgnoreCase("February")) {
            monthValue = 1;

        } else if (monthName.equalsIgnoreCase("March")) {
            monthValue = 2;

        } else if (monthName.equalsIgnoreCase("April")) {
            monthValue = 3;

        } else if (monthName.equalsIgnoreCase("May")) {
            monthValue = 4;

        } else if (monthName.equalsIgnoreCase("June")) {
            monthValue = 5;

        } else if (monthName.equalsIgnoreCase("July")) {
            monthValue = 6;

        } else if (monthName.equalsIgnoreCase("August")) {
            monthValue = 7;

        } else if (monthName.equalsIgnoreCase("September")) {
            monthValue = 8;

        } else if (monthName.equalsIgnoreCase("October")) {
            monthValue = 9;

        } else if (monthName.equalsIgnoreCase("November")) {
            monthValue = 10;

        } else if (monthName.equalsIgnoreCase("December")) {
            monthValue = 11;

        }
    }

    private void checkWeekInt() {
        if (dayName.equalsIgnoreCase("SUNDAY")) {
            dayValue = 0;

        } else if (dayName.equalsIgnoreCase("MONDAY")) {
            dayValue = 1;

        } else if (dayName.equalsIgnoreCase("TUESDAY")) {
            dayValue = 2;

        } else if (dayName.equalsIgnoreCase("WEDNESDAY")) {
            dayValue = 3;

        } else if (dayName.equalsIgnoreCase("THURSDAY")) {
            dayValue = 4;

        } else if (dayName.equalsIgnoreCase("FRIDAY")) {
            dayValue = 5;

        } else if (dayName.equalsIgnoreCase("SATURDAY")) {
            dayValue = 6;
        }


    }

    private void checkMonthName() {

        if (monthValue == 0) {
            nextMonthName = "January";

        } else if (monthValue == 1) {
            nextMonthName = "February";

        } else if (monthValue == 2) {
            nextMonthName = "March";

        } else if (monthValue == 3) {
            nextMonthName = "April";

        } else if (monthValue == 4) {
            nextMonthName = "May";

        } else if (monthValue == 5) {
            nextMonthName = "June";

        } else if (monthValue == 6) {
            nextMonthName = "July";

        } else if (monthValue == 7) {
            nextMonthName = "August";

        } else if (monthValue == 8) {
            nextMonthName = "September";

        } else if (monthValue == 9) {
            nextMonthName = "October";

        } else if (monthValue == 10) {
            nextMonthName = "November";

        } else if (monthValue == 11) {
            nextMonthName = "December";

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void nextMonthData() {
        int a;

        if(monthValue == 11){
            monthValue = 0;
            a = monthValue;
        }else {
             a = monthValue + 1;

        }
        dateDifferenceMethod(a);

    }

    private void previusMonthData() {
        int a;
        if(monthValue == 0){
            monthValue = 11;
            a = monthValue;
        }else {
            a = monthValue - 1;
        }
        dateDifferenceMethod(a);

    }

    private void dateDifferenceMethod(int a ) {

        shiftList.clear();
        dateList.clear();

        recyclerAdapter.notifyDataSetChanged();

       // a = monthValue + 1;
        System.out.println(">>>>>>>>>Next month name:value " + a);
        System.out.println(">>>>>>>>>Next month name:value " + monthValue + " " + yearName);

        //Date lastDay = new Date(dateAfter.getYear(), (monthValue), numDays);
        //System.out.println(">>>>>>>>>Next month last name:value " + lastDay);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        firstDayNextMont = new Date(dateAfter.getYear(), (a), 1);
        calendar.setTime(firstDayNextMont);

        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(">>>>>>>>>Next month no of day " + days);
        System.out.println(">>>>>>>>>Next month date " + firstDayNextMont);

        SimpleDateFormat myDay = new SimpleDateFormat("EEEE");
        String dayNameNext = myDay.format(firstDayNextMont);

        long difference1 = firstDayNextMont.getTime() - dateBefore.getTime();
        float daysBetween1 = (difference1 / (1000 * 60 * 60 * 24));

        System.out.println(">>>>>>>>>select month date " + daysBetween1 + "" + dayNameNext);

        dayName = dayNameNext;

        for (int i = 0; i < 12; i++) {
            if (i == a) {
                monthValue = a;
                checkMonthName();
                checkWeekInt();

                calendarMonth.setText(nextMonthName);
                System.out.println(">>>>>>>>>Next month name: " + dayValue);

                for (int j = 0; j < 7; j++) {

                    if (j == dayValue) {

                        for (int k = 1; k < (days + 1); k++) {

                            float remind1 = daysBetween1 % 8;
                            int value =  Math.abs(Math.round(remind1));

                            if (value == 7) {
                                b = s1[0];
                                shiftList.add(b);
                                dateList.add(Integer.toString(k));
                            } else {

                                b = s1[value + 1];
                                shiftList.add(b);
                                dateList.add(Integer.toString(k));
                            }
                            daysBetween1++;
                            //days++;

                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerAdapter = new RecyclerAdapter(this, shiftList, dateList);
                            recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
                            recyclerView.setAdapter(recyclerAdapter);
                            recyclerAdapter.notifyDataSetChanged();


                        }

                    } else {
                        shiftList.add("");
                        dateList.add("");

                    }

                }

                System.out.println(">>>>>>>>> float next: " + b);
                System.out.println(">>>>>>>>>Number of Days between dates next: " + daysBetween1);

            }

        }

    }

    private void showPopup(){

        ArrayList<MyData> data = new ArrayList<>();
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setLayout(800, 1400);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pop_up_spinner, null);
        alertDialog.getWindow().setContentView(dialogView);

        Spinner spinnerPopUp =  dialogView.findViewById(R.id.spinnerPopup);
        Button okBtn = dialogView.findViewById(R.id.okBtn);
        Button cancelBtn = dialogView.findViewById(R.id.cancelBtn);

       MyData myData = new MyData();

       for (int i = 0; i < eventList.length +1; i++ ){
           myData.setText(eventList[i]);
           myData.setImage(eventImgeList[i]);
           data.add(myData);
       }


        spinnerAdapter spinnerAdap = new spinnerAdapter(this,data);
        spinnerPopUp.setAdapter(spinnerAdap);

        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        spinnerPopUp.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerItem = spinner.getSelectedItem().toString();
        System.out.println(">>>>>>>>>>>.spiner item" + spinnerItem);
        TextView textView = (TextView) view;
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) adapterView.getChildAt(0)).setTextSize(20);
        //((TextView) adapterView.getChildAt(0)).setText(yearName);

        //(TextView)adapterView.getChildAt(0).setT(Color.RED);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
