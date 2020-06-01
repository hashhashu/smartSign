package com.example.signclass.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signclass.R;
import com.example.signclass.adapter.CourseAdapter;
import com.example.signclass.bean.Course;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerView);

        List<Course> courseList = new ArrayList<>();
        Course course1 = new Course("软件理论与工程","周二/1234");
        Course course2 = new Course("大数据","周三/345");
        courseList.add(course1);
        courseList.add(course2);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        //layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        CourseAdapter myAdapter = new CourseAdapter(courseList);
        mRecyclerView.setAdapter(myAdapter);

        return root;
    }
}
