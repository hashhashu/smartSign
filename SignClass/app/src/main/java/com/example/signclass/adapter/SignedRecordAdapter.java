package com.example.signclass.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signclass.R;
import com.example.signclass.bean.SignedRecord;

import java.util.List;

public class SignedRecordAdapter extends RecyclerView.Adapter<SignedRecordAdapter.MyViewHolder> {
    private List<SignedRecord> recordList;
    public SignedRecordAdapter(List<SignedRecord> courseList) {
        this.recordList = courseList;
    }
    private View.OnClickListener mOnItemClickListener;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.signedrecord_items,parent,false);
        MyViewHolder myViewHolder = new SignedRecordAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SignedRecord record = recordList.get(position);
        holder.tv_location.setText("已签到:"+record.getSigned()+"/未签到:"+record.getNotsigned());
        holder.tv_time.setText(record.getSignedTime());


    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_location;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tv_time =itemView.findViewById(R.id.tv_time);
            this.tv_location = itemView.findViewById(R.id.tv_location);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
