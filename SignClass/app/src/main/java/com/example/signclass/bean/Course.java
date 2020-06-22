package com.example.signclass.bean;

public class Course {
    private String CourseName;
    private String CourseTime;

    public Course(String CourseName,String CourseTime){
        this.CourseName = CourseName;
        this.CourseTime = CourseTime;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void SetCourseName(String courseName){ this.CourseName =courseName;}

    public String getCourseTime() {
        return CourseTime;
    }

    private String name;
    private int num;

    public Course(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


}
