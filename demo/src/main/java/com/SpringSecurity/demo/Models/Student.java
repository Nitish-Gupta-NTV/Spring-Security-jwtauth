package com.SpringSecurity.demo.Models;

public class Student {
    private int id;
    private String name;
    private String email;
    private int marks;

    public Student() {
    }

    public Student(int id, String Name, String email, int marks) {
        this.id = id;
        Name = Name;
        this.email = email;
        this.marks = marks;
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
        name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", marks='" + marks + '\'' +
                '}';
    }
}
