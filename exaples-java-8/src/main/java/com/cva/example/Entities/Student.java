package com.cva.example.Entities;

public class Student extends Person {

    private String studentId;
    
    public Student(String name, String lastName, Integer age) {
        super(name, lastName, age);
    }
    public Student(String name, String lastName, Integer age, String studentId) {
        super(name, lastName, age);
        this.studentId = studentId;
    }

    
    @Override
    public String toString() {
        return "Student []";
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    
}
