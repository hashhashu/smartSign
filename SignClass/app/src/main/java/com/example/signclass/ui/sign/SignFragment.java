package com.example.signclass.ui.sign;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import com.example.signclass.DBOpenHelper;
import com.example.signclass.MainActivity;
import com.example.signclass.R;
import com.example.signclass.SignActivity;
import com.example.signclass.SignDetail;
import com.example.signclass.adapter.SignedRecordAdapter;
import com.example.signclass.bean.Course;
import com.example.signclass.bean.SignedRecord;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SignFragment extends Fragment {

    private SignViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private Button btn_sign;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private List<String> allItems;
    List<SignedRecord> recordList = new ArrayList<>();
    SignedRecordAdapter myAdapter ;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
    String name = "";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Course test = (Course) msg.obj;
            allItems.add(test.getCourseName());
            adapter.notifyDataSetChanged();
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            SignedRecord test = (SignedRecord) msg.obj;
            recordList.add(test);
            myAdapter.notifyDataSetChanged();
        }
    };

    public static SignFragment newInstance() {
        return new SignFragment();
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Step 4 of 4: Finally call getTag() on the view.
            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            //Log.d("jincheng-cnth", Integer.toString(position));
            Intent intent = new Intent(getActivity(), SignDetail.class);
            intent.putExtra("cnth",position);
            intent.putExtra("cname",(String) spinner.getSelectedItem());
            startActivity(intent);
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign, container, false);
        spinner = root.findViewById(R.id.spinner);
        mRecyclerView = root.findViewById(R.id.recyclerView_sign);
        btn_sign = root.findViewById(R.id.btn_sign);

        allItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, allItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        try {
            name = getArguments().getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!TextUtils.isEmpty(name)) {
            //连接数据库进行操作需要在主线程操作
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection conn = null;
                    conn =(Connection) DBOpenHelper.getConn();
                    String sql = "select cname,ctime from course where cname = '"+name+"' UNION ALL select cname,ctime from course where cname <> '"+name+"' ";
                    Statement st;
                    allItems.clear();
                    try {
                        st = (Statement) conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        while (rs.next()){
                            Course test = new Course(rs.getString(1),rs.getString(2));
                            Message msg = new Message();
                            msg.obj = test;
                            handler.sendMessage(msg);
                            //courseList.add(test);
                        }
                        st.close();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else
        {
            //连接数据库进行操作需要在主线程操作
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection conn = null;
                    conn =(Connection) DBOpenHelper.getConn();
                    String sql = "select cname,ctime from course ";
                    Statement st;
                    allItems.clear();
                    try {
                        st = (Statement) conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        while (rs.next()){
                            Course test = new Course(rs.getString(1),rs.getString(2));
                            Message msg = new Message();
                            msg.obj = test;
                            handler.sendMessage(msg);
                            //courseList.add(test);
                        }
                        st.close();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignActivity.class);
                intent.putExtra("coursename",(String) spinner.getSelectedItem());
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                SignedRecord record1 = new SignedRecord("2020/05/02 08:30",20,5);
//                SignedRecord record2 = new SignedRecord("2020/05/09 08:30",28,5);
//                recordList.add(record1);
//                recordList.add(record2);
//                Log.d("hlhupload", "in" );
                mRecyclerView.setAdapter(myAdapter);
                //连接数据库进行操作需要在主线程操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        conn =(Connection) DBOpenHelper.getConn();
                        //UNDO sql select
                        String sql = "select coursesign.csigntime,coursesign.csigned,coursesign.cnotsigned,coursesign.cnth  from coursesign,course where coursesign.cno = course.cno and course.cname = '"+(String) spinner.getSelectedItem()+"' order by cnth desc ";
                        Log.d("jincheng-sql", sql );
                        Statement st;
                        recordList.clear();
                        try {
                            st = (Statement) conn.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                            rs.last();
                            Log.d("jincheng-row", String.valueOf(rs.getRow()));
                            if(rs.getRow() == 0) {
                                SignedRecord test = new SignedRecord("",null,null);
                                Message msg = new Message();
                                msg.obj = test;
                                handler2.sendMessage(msg);
                            }
                            else{
                                rs.beforeFirst();
                                while (rs.next()){
                                    SignedRecord test = new SignedRecord(rs.getString(1),rs.getInt(2),rs.getInt(3));
                                    Message msg = new Message();
                                    msg.obj = test;
                                    handler2.sendMessage(msg);
                                    //courseList.add(test);
                                }
                            }

                            st.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mRecyclerView.setLayoutManager(layoutManager);
        myAdapter = new SignedRecordAdapter(recordList);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(onItemClickListener);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignViewModel.class);
        // TODO: Use the ViewModel
    }



}
