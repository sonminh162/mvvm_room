package com.lifetime.mvvm_room.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lifetime.mvvm_room.R;
import com.lifetime.mvvm_room.model.Student;
import com.lifetime.mvvm_room.viewmodel.StudentViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateStudentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String DATE_FORMAT = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
    private EditText editTextName,editTextAddress,editTextSubject,editTextDate;
    private CheckBox checkBoxFinished;
    private Spinner spinner;

    StudentViewModel studentViewModel;
    Student studentReceived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        editTextName = findViewById(R.id.update_name);
        editTextAddress = findViewById(R.id.update_address);
        editTextSubject = findViewById(R.id.update_subjects);
        editTextDate = findViewById(R.id.update_birthday);

        checkBoxFinished = findViewById(R.id.checkBoxFinished);

        spinner = findViewById(R.id.update_gender);

        //----- spinner area

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        final int malePosition = adapter.getPosition("Male");
        final int femalePosition = adapter.getPosition("Female");

        //----- spinner area

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });

        studentReceived = (Student) getIntent().getSerializableExtra("student");

        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        studentViewModel.getStudentById(studentReceived.getId()).observe(this, new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                loadTask(student);
                boolean male = student.getGender().equals("Male");
                if(male){
                    spinner.setSelection(malePosition);
                }else{
                    spinner.setSelection(femalePosition);
                }
            }
        });

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudent(studentViewModel.getStudentByIdOriginal(studentReceived.getId()));
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentViewModel.deleteStudent(studentViewModel.getStudentByIdOriginal(studentReceived.getId()));
            }
        });
    }

    private void updateStudent(Student student){
        final String sName = editTextName.getText().toString().trim();
        final String sAddress = editTextAddress.getText().toString().trim();
        final String sSubject = editTextSubject.getText().toString().trim();
        final String sDate = editTextDate.getText().toString().trim();
        final String sSpinner = spinner.getSelectedItem().toString();
        final boolean sChecked = checkBoxFinished.isChecked();

        if(sName.isEmpty()) {
            editTextName.setError("Task required");
            editTextName.requestFocus();
            return;
        }

        if(sAddress.isEmpty()){
            editTextAddress.setError("Desc required");
            editTextAddress.requestFocus();
            return;
        }

        if(sSubject.isEmpty()){
            editTextSubject.setError("Finish by required");
            editTextSubject.requestFocus();
            return;
        }

        if(sDate.isEmpty()){
            editTextDate.setError("Birthday required");
            editTextDate.requestFocus();
            return;
        }

        boolean invalidDate = !sDate.matches(DATE_FORMAT);
        if(invalidDate){
            editTextDate.setError("Format date");
            editTextDate.requestFocus();
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

        student.setName(sName);
        student.setAddress(sAddress);
        student.setSubject(sSubject);
        student.setBirthday(sDate);
        student.setFinished(sChecked);
        student.setGender(sSpinner);

        studentViewModel.updateStudent(student);
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
                editTextDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void loadTask(Student student) {
        editTextName.setText(student.getName());
        editTextAddress.setText(student.getAddress());
        editTextSubject.setText(student.getSubject());
        editTextDate.setText(student.getBirthday());
        checkBoxFinished.setChecked(student.isFinished());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
