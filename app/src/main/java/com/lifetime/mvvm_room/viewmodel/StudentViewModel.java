package com.lifetime.mvvm_room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lifetime.mvvm_room.model.Student;
import com.lifetime.mvvm_room.repository.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    private StudentRepository studentRepository;
    private LiveData<List<Student>> allStudents;
    private LiveData<List<Student>> searchResultList;
    private LiveData<List<Student>> AzResult;
    private LiveData<List<Student>> ZaResult;

    public StudentViewModel(@NonNull Application application) {
        super(application);

        studentRepository = new StudentRepository(application);
        allStudents = studentRepository.getAllStudent();
        searchResultList = studentRepository.getSearchResultList();
        AzResult = studentRepository.getResultSortAtoZ();
        ZaResult = studentRepository.getResultSortZtoA();
    }

    public void updateStudent(Student student){
        studentRepository.updateStudent(student);
    }

    public void insertStudent(Student student){
        studentRepository.insertStudent(student);
    }

    public LiveData<List<Student>> getAllStudents(){
        return allStudents;
    }

    public LiveData<Student> getStudentById(int id){
        return studentRepository.getStudentById(id);
    }

    public void deleteStudent(Student student){
        studentRepository.deleteStudent(student);
    }

    public Student getStudentByIdOriginal(int id){
        return studentRepository.getStudentByIdReturnOrigin(id);
    }

    public void searchStudentByName(String studentName){
        studentRepository.searchStudentByName(studentName);
    }

    public void sortAtoZ(){
        studentRepository.sortDataAtoZ();
    }

    public void sortZtoA(){
        studentRepository.sortDataZtoA();
    }

    public LiveData<List<Student>> getSearchResultList(){
        return searchResultList;
    }

    public LiveData<List<Student>> sortAToZResultList(){
        return AzResult;
    }

    public LiveData<List<Student>> sortZToAResultList(){
        return ZaResult;
    }
}
