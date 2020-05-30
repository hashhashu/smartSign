package com.example.signinteacher1;

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

    public String getCourseTime() {
        return CourseTime;
    }




}
