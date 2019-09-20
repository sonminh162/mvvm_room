package com.lifetime.mvvm_room.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lifetime.mvvm_room.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.lifetime.mvvm_room.constant.Constants.ADDRESS;
import static com.lifetime.mvvm_room.constant.Constants.DATE;
import static com.lifetime.mvvm_room.constant.Constants.GENDER;
import static com.lifetime.mvvm_room.constant.Constants.NAME;
import static com.lifetime.mvvm_room.constant.Constants.SUBJECT;

public class AddStudentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String DATE_FORMAT = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
    private EditText editTextName,editTextAddress,editTextSubject,edtDate;
    private Spinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextSubject = findViewById(R.id.editTextSubject);
        edtDate = findViewById(R.id.editTextDate);
        spinner = findViewById(R.id.create_gender);

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });

        createSpinner();

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }
    
    private void save(){
        final String sName = editTextName.getText().toString().trim();
        final String sAddress = editTextAddress.getText().toString().trim();
        final String sSubject = editTextSubject.getText().toString().trim();
        final String birthday = edtDate.getText().toString().trim();
        final String sSpinner = spinner.getSelectedItem().toString().trim();

        if(sName.isEmpty()){
            editTextName.setError("Task required");
            editTextName.requestFocus();
            return;
        }

        if(sAddress.isEmpty()){
            editTextAddress.setError("Desc required");
            editTextAddress.requestFocus();
        }

        if(sSubject.isEmpty()){
            editTextAddress.setError("Finish by required");
            editTextAddress.requestFocus();
            return;
        }

        if(birthday.isEmpty()){
            edtDate.setError("Birthday required");
            edtDate.requestFocus();
            return;
        }

        boolean invalidDate = !birthday.matches(DATE_FORMAT);
        if(invalidDate){
            edtDate.setError("Format date");
            edtDate.requestFocus();
            return;
        }

        if(sSpinner.equals("Gender")){
            TextView errorText = (TextView)spinner.getSelectedView();
            errorText.setError("required");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("required");
            spinner.requestFocus();
            return;
        }
        Intent result = new Intent();
        result.putExtra(NAME,sName);
        result.putExtra(ADDRESS,sAddress);
        result.putExtra(SUBJECT,sSubject);
        result.putExtra(DATE,birthday);
        result.putExtra(GENDER,sSpinner);
        setResult(RESULT_OK,result);
        finish();
    }

    private void pickDate(){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //i: year i1: month i2:day
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void createSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
