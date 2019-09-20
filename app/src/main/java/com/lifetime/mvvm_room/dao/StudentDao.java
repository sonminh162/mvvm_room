package com.lifetime.mvvm_room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lifetime.mvvm_room.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAll();

    @Insert
    void insert(Student student);

    @Delete
    void delete(Student student);

    @Update
    void update(Student student);

    @Query("DELETE FROM Student")
    void deleteAllStudents();

    @Query("SELECT * FROM Student WHERE id_field LIKE :studentId")
    LiveData<Student> getStudentById(int studentId);

    @Query("SELECT * FROM Student WHERE id_field LIKE :studentId")
    Student getStudentByIdReturnStudent(int studentId);

    @Query("SELECT * FROM Student ORDER BY name_filed DESC")
    List<Student> sortDataAtoZ();

    @Query("SELECT * FROM Student ORDER BY name_filed ASC")
    List<Student> sortDataZtoA();

    @Query("SELECT * FROM Student WHERE name_filed LIKE '%' || :studentName || '%'")
    List<Student> searchStudentByStudentName(String studentName);


}
