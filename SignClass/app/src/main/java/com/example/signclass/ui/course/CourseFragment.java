package com.example.signclass.ui.course;

import androidx.lifecycle.ViewModelProviders;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.signclass.R;
import com.example.signclass.SignActivity;
import com.example.signclass.adapter.Myadapter;
import com.example.signclass.bean.Course;
import com.example.signclass.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    private CourseViewModel mViewModel;
    private RecyclerView mRecyclerView;

    private Button btn_ok;//1
    private Button btn_no;//1.1






    public static CourseFragment newInstance() {
        return new CourseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        //
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_course);     ////////////////////////////////////


        View root = inflater.inflate(R.layout.fragment_course, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerView2);/////////////////////////////////////


        List<Course> courseList=new ArrayList<>();
        Course course1=new Course("a",1);//////////////////////////
        courseList.add(course1);



        //解决办法。在setAdapter()之前先设置LayoutManager就好啦。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        Myadapter myadapter=new Myadapter(courseList);
        mRecyclerView.setAdapter(myadapter);

        //  View root = inflater.inflate(R.layout.fragment_course, container, false);
        btn_ok = root.findViewById(R.id.btn_ok);//2
        btn_no = root.findViewById(R.id.btn_no);//2.1

        //按钮点击事件3
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(getContext(),"您已同意申请！",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //按钮点击事件3.1
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(getContext(),"您已拒绝申请！",Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        return root;
    }

    private void setContentView(int activity_main) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        // TODO: Use the ViewModel

    }

}
