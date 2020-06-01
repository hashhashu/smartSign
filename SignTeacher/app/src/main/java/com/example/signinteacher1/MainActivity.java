package com.example.signinteacher1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        List<Course> courseList = new ArrayList<>();
        Course course1 = new Course("软件理论与工程","周二/1234");
        Course course2 = new Course("大数据","周三/345");
        courseList.add(course1);
        courseList.add(course2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        CourseAdapter myAdapter = new CourseAdapter(courseList);
        mRecyclerView.setAdapter(myAdapter);
    }
}
