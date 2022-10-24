package com.gzeinnumer.newmaterialdaterangepickerondialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker1();
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker2();
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker3();
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker4();
            }
        });
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker5();
            }
        });
    }

    private void initDatePicker1() {
        //step 1
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();

        //disable date before today
        calendarConstraints.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        //set date focus
        builder.setSelection(today);

        builder.setCalendarConstraints(calendarConstraints.build());
        final MaterialDatePicker materialDatePicker = builder.build();

        //step 2
        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        //step 3
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                TextView tv = findViewById(R.id.tv1);
                tv.setText("Tanggal yang dipilih (getHeaderText) : "+materialDatePicker.getHeaderText());
            }
        });

        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Tidak jadi memilih", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initDatePicker2(){
        //step 1
        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();

        //core
        //set open at
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.MARCH);
        long march = calendar.getTimeInMillis();
        calendarConstraints.setOpenAt(march);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");

        builder.setCalendarConstraints(calendarConstraints.build());
        final MaterialDatePicker materialDatePicker = builder.build();

        //step 2
        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Tidak jadi memilih", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDatePicker3() {
        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();

        //core
        //disable weekenday
        calendarConstraints.setValidator(new DateValidatorWeekdays());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");

        builder.setCalendarConstraints(calendarConstraints.build());
        final MaterialDatePicker materialDatePicker = builder.build();

        //step 2
        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void initDatePicker4(){
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        Calendar f = Calendar.getInstance();
        f.add(Calendar.MONTH, 0);
        Calendar e = Calendar.getInstance();
        e.add(Calendar.MONTH,0);

        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();
        calendarConstraints.setStart(f.getTimeInMillis());
        calendarConstraints.setEnd(e.getTimeInMillis());

        builder.setCalendarConstraints(calendarConstraints.build());
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();
        materialDatePicker.show(getSupportFragmentManager(),"date_picker_tag");

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String awal = formater.format(new Date(selection.first));
                String akhir = formater.format(new Date(selection.second));
                Toast.makeText(getApplicationContext(), awal +" - "+akhir, Toast.LENGTH_SHORT).show();
                TextView tv = findViewById(R.id.tv4);
                tv.setText("Tanggal yang dipilih (Start End) : "+awal +" - "+akhir);

            }
        });
        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Tidak jadi memilih", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDatePicker5(){
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        Calendar f = Calendar.getInstance();
        f.add(Calendar.DATE, -5);
        Calendar e = Calendar.getInstance();
        e.add(Calendar.DATE,0);

        CalendarConstraints.DateValidator dateValidatorMin = DateValidatorPointForward.from(f.getTimeInMillis());
        CalendarConstraints.DateValidator dateValidatorMax = DateValidatorPointBackward.before(e.getTimeInMillis());

        ArrayList<CalendarConstraints.DateValidator> listValidators = new ArrayList<>();
        listValidators.add(dateValidatorMin);
        listValidators.add(dateValidatorMax);
        listValidators.add(new DateValidatorWeekdays());

        CalendarConstraints.DateValidator validators = CompositeDateValidator.allOf(listValidators);

        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();
        calendarConstraints.setValidator(validators);


        builder.setCalendarConstraints(calendarConstraints.build());
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();
        materialDatePicker.show(getSupportFragmentManager(),"date_picker_tag");

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String awal = formater.format(new Date(selection.first));
                String akhir = formater.format(new Date(selection.second));
                Toast.makeText(getApplicationContext(), awal +" - "+akhir, Toast.LENGTH_SHORT).show();
//                TextView tv = findViewById(R.id.tv4);
//                tv.setText("Tanggal yang dipilih (Start End) : "+awal +" - "+akhir);

            }
        });
        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Tidak jadi memilih", Toast.LENGTH_SHORT).show();
            }
        });
    }
}