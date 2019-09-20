package com.lifetime.mvvm_room.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifetime.mvvm_room.R;
import com.lifetime.mvvm_room.adapter.StudentAdapter;
import com.lifetime.mvvm_room.model.Student;
import com.lifetime.mvvm_room.viewmodel.StudentViewModel;

import java.util.List;

import static com.lifetime.mvvm_room.constant.Constants.ADDRESS;
import static com.lifetime.mvvm_room.constant.Constants.DATE;
import static com.lifetime.mvvm_room.constant.Constants.GENDER;
import static com.lifetime.mvvm_room.constant.Constants.NAME;
import static com.lifetime.mvvm_room.constant.Constants.SUBJECT;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_STUDENT_ID = 101;
    boolean aToZSortNextTime = true;

    private RecyclerView recyclerView;

    private StudentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.studentRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StudentAdapter adapter = new StudentAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        viewModel.sortAToZResultList().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.setStudents(students);
            }
        });

        viewModel.sortZToAResultList().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.setStudents(students);
            }
        });

        viewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.setStudents(students);
            }
        });

        viewModel.getSearchResultList().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.setStudents(students);
            }
        });

        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddStudentActivity.class), ADD_STUDENT_ID);
            }
        });

        findViewById(R.id.floating_button_sort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aToZSortNextTime) {
                    viewModel.sortAtoZ();
                    aToZSortNextTime = false;
                } else {
                    viewModel.sortZtoA();
                    aToZSortNextTime = true;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean valid_first = requestCode == ADD_STUDENT_ID && resultCode == RESULT_OK;
        if (valid_first) {
            boolean valid = data != null;
            if (valid) {
                String name = data.getStringExtra(NAME);
                String address = data.getStringExtra(ADDRESS);
                String subject = data.getStringExtra(SUBJECT);
                String date = data.getStringExtra(DATE);
                String gender = data.getStringExtra(GENDER);
                viewModel.insertStudent(new Student(name, address, subject, date, true, gender));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewModel.searchStudentByName(s);
                return false;
            }
        });
        return true;
    }
}
