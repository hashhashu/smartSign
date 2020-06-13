package com.example.signclass.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signclass.R;
import com.example.signclass.bean.Student;

import java.util.List;

public class StuAdapter extends RecyclerView.Adapter<StuAdapter.MyViewHolder> {

    private List<Student> StuList;
    public StuAdapter(List<Student> StuList) {
        this.StuList = StuList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stu_items,parent,false);
        MyViewHolder myViewHolder = new StuAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StuAdapter.MyViewHolder holder, int position) {
        Student student = StuList.get(position);
        holder.tv_sname.setText(student.getStuName());
        holder.tv_sno.setText(student.getStuNo());
    }


    @Override
    public int getItemCount() {
        return StuList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sname;
        TextView tv_sno;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_sname =itemView.findViewById(R.id.tv_sname);
            this.tv_sno = itemView.findViewById(R.id.tv_sno);
        }

    }
}
