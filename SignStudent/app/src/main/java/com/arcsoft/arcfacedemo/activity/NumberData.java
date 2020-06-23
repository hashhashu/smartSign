package com.arcsoft.arcfacedemo.activity;

import android.app.Application;

public class NumberData extends Application {
    private String number;
    private String course;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCourse(){return  course;}
    public void setCourse(String course) {this.course=course;}
    @Override
    public void onCreate(){
        number = "null";
        course = "null";
        super.onCreate();
    }
}