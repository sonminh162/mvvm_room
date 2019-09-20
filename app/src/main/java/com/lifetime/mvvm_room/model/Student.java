package com.lifetime.mvvm_room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Student implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_field")
    private int id;

    @ColumnInfo(name = "name_filed")
    private String name;

    @ColumnInfo(name = "address_field")
    private String address;

    @ColumnInfo(name = "subject_field")
    private String subject;

    @ColumnInfo(name = "birthday_field")
    private String birthday;

    @ColumnInfo(name = "finished")
    private boolean finished;

    @ColumnInfo(name = "gender")
    private String gender;

    public Student(String name, String address, String subject, String birthday, boolean finished, String gender) {
        this.name = name;
        this.address = address;
        this.subject = subject;
        this.birthday = birthday;
        this.finished = finished;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
