package com.arcsoft.arcfacedemo.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arcsoft.arcfacedemo.R;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {
    private int resourcesID;
    public CourseAdapter(Context context, int resource, List<Course> objects){
        super(context,resource,objects);
        resourcesID = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //eturn super.getView(position, convertView, parent);
        Course course = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourcesID,parent,false);
        TextView _tvName = view.findViewById(R.id.tv_cname);
        TextView _tvTime = view.findViewById(R.id.tv_time);
        TextView _tvIson = view.findViewById(R.id.tv_ison);
        TextView _tvIssign = view.findViewById(R.id.tv_issign);
        _tvName.setText(course.getName());
        _tvTime.setText("周"+course.getTime()+ "  第"+course.getStart()+"节"+"--第"+course.getEnd()+"节");
        _tvIson.setText(course.getIsOngoing());
        _tvIssign.setText(course.getIsSigned());
        return view;
    }
}
