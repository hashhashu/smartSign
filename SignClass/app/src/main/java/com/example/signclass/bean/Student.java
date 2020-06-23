package com.example.signclass.bean;

public class Student {
    private String StuName;
    private String StuNo;

    public Student(String stuName, String stuNo) {
        StuName = stuName;
        StuNo = stuNo;
    }

    public String getStuName() {
        return StuName;
    }

    public String getStuNo() {
        return StuNo;
    }
}
