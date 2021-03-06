package com.example.signclass.adapter;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signclass.R;
import com.example.signclass.SignDetail;
import com.example.signclass.bean.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder>  {

    private List<Course> courseList;

    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }
    private View.OnClickListener mOnItemClickListener;


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.tv_content.setText(course.getCourseName());
        holder.tv_date.setText(course.getCourseTime());
    }



    //setter方法
    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;
        TextView tv_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_content =itemView.findViewById(R.id.tv_content);
            this.tv_date = itemView.findViewById(R.id.tv_date);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

    }
}
