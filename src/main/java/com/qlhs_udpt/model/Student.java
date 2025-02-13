package com.qlhs_udpt.model;
import java.io.Serializable;
public class Student implements Serializable {
    private String major;
    private int id;
    private String name;
    private String gender;
    private int age;
    private String academicYear;
    private String hometown;
    public Student(int id, String name, String gender, int age, String major, String academicYear, String hometown) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
        this.academicYear = academicYear;
        this.hometown = hometown;
    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public String getMajor() {return major;}
    public void setMajor(String major) {this.major = major;}
    public String getAcademicYear() {return academicYear;}
    public void setAcademicYear(String academicYear) {this.academicYear = academicYear;}
    public String getHometown() {return hometown;}
    public void setHometown(String hometown) {this.hometown = hometown;}
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Gender: " + gender + ", Age: " + age + ", Major: " + major + ", AcademicYear" + academicYear + ", Hometown: " + hometown;
    }

}