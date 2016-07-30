package com.codepath.articleatlas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by melissahuang on 7/29/16.
 */
public class SettingsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        EditText beginDate = (EditText) findViewById(R.id.etBeginDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat stringFormat = new SimpleDateFormat("yyyyMMdd");
        date = stringFormat.format(c.getTime());

        beginDate.setText(dateFormat.format(c.getTime()));
    }

    public void saveFilters(View v) {
        // fetch calendar date if !null
        if (date != null) {
            // put the date into the intent
        }

        Spinner sortSpinner = (Spinner) findViewById(R.id.sortSpinner);
        String sortString = sortSpinner.getSelectedItem().toString().toLowerCase();

        List<String> newsList = new ArrayList<String>();
        CheckBox checkArts = (CheckBox) findViewById(R.id.cbArts);
        if (checkArts.isChecked()) {
            newsList.add("Arts");
        }
        CheckBox checkFashion = (CheckBox) findViewById(R.id.cbFashion);
        if (checkFashion.isChecked()) {
            newsList.add("Fashion & Style");
        }
        CheckBox checkSports = (CheckBox) findViewById(R.id.cbSports);
        if (checkSports.isChecked()) {
            newsList.add("Sports");
        }
    }
}
