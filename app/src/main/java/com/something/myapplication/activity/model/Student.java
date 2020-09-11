package com.something.myapplication.activity.model;

public class Student {

    //model class defining the data and its type.
    //this includes construcors and getters and setters
    private	int	rollno;
    private	String name;
    private	String course;

    public Student(String name, String course) {
        this.name = name;
        this.course = course;
    }

    public Student(int rollno, String name, String course) {
        this.rollno = rollno;
        this.name = name;
        this.course = course;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
