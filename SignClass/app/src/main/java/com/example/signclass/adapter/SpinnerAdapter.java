package com.example.signclass.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.signclass.R;
import com.example.signclass.bean.Course;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Course> item;

    public SpinnerAdapter(Activity activity, List<Course> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.fragment_sign, null);

        Course course;
        course = item.get(position);

        return convertView;
    }
}
