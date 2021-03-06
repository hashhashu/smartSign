package com.example.signclass;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signclass.adapter.StuAdapter;
import com.example.signclass.bean.Course;
import com.example.signclass.bean.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SignDetail extends AppCompatActivity {
    private RecyclerView mRecyclerViewSigned;
    private RecyclerView mRecyclerViewUnsigned;
    private ArrayAdapter<String> adapter;
    List<Student> studentListSigned = new ArrayList<>();
    List<Student> studentListUnsigned = new ArrayList<>();
    LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
    LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
    StuAdapter myAdapterSigned = new StuAdapter(studentListSigned);
    StuAdapter myAdapterUnsigned = new StuAdapter(studentListUnsigned);
    int count = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Student test = (Student) msg.obj;
            studentListSigned.add(test);
            myAdapterSigned.notifyDataSetChanged();
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Student test = (Student) msg.obj;
            studentListUnsigned.add(test);
            myAdapterUnsigned.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_detail);

        mRecyclerViewSigned = findViewById(R.id.rv_signed);
        mRecyclerViewUnsigned = findViewById(R.id.rv_unsigned);


        //连接数据库进行操作需要在主线程操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) DBOpenHelper.getConn();
                int num = Integer.parseInt(getIntent().getStringExtra("cnth"));
                count = Integer.parseInt(getIntent().getStringExtra("count"));
                String ss = String.valueOf(count - num);
                String sqlSigned = "select student.sname,student.sno from student where student.snumber in (select snumber from studentsign where cnth = "+ ss +" and cno = (select cno from course where cname = '"+getIntent().getStringExtra("cname")+"')) ";
                String sqlUnsigned = "select student.sname,student.sno from student,sc where student.snumber=sc.snumber  and sc.cno = (select cno from course where cname = '"+getIntent().getStringExtra("cname")+"') and student.sno not in (select student.sno from student where student.snumber in (select snumber from studentsign where cnth = "+ss+" and cno = (select cno from course where cname = '"+getIntent().getStringExtra("cname")+"'))) ";

                Statement st;
                studentListSigned.clear();
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sqlSigned);
                    while (rs.next()){
                        Student test = new Student(rs.getString(1),rs.getString(2));
                        Message msg = new Message();
                        msg.obj = test;
                        handler.sendMessage(msg);
                        //courseList.add(test);
                    }
                    rs = st.executeQuery(sqlUnsigned);
                    while (rs.next()){
                        Student test = new Student(rs.getString(1),rs.getString(2));
                        Message msg = new Message();
                        msg.obj = test;
                        handler2.sendMessage(msg);
                        //courseList.add(test);
                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        mRecyclerViewSigned.setLayoutManager(layoutManager1);

        mRecyclerViewSigned.setAdapter(myAdapterSigned);

        mRecyclerViewUnsigned.setLayoutManager(layoutManager2);

        mRecyclerViewUnsigned.setAdapter(myAdapterUnsigned);
        //Log.d("jincheng-cnth", "chengggong");
    }
}
