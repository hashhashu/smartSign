package com.example.signclass.ui.home;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.signclass.DBOpenHelper;
import com.example.signclass.R;
import com.example.signclass.adapter.CourseAdapter;
import com.example.signclass.bean.Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private List<Course> courseList = new ArrayList<>();
    FragmentManager fm;
    CourseAdapter myAdapter = new CourseAdapter(courseList);

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String course;
            String time;
            Course test = (Course) msg.obj;
//            course = test.getCourseName();
//            time = test.getCourseTime();
//            Course courset = new Course(course,time);
            courseList.add(test);
            myAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Step 4 of 4: Finally call getTag() on the view.
            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            ConstraintLayout layout = (ConstraintLayout) mRecyclerView.getChildAt(position);
            Bundle bundle = new Bundle();
            TextView tvv = layout.findViewById(R.id.tv_content);
            bundle.putString("name", tvv.getText().toString());
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_navigation_home_to_navigation_sign,bundle);
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerView);
        fm = getFragmentManager();

        //连接数据库进行操作需要在主线程操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = null;
                conn =(Connection) DBOpenHelper.getConn();
                String sql = "select cname,ctime,cstart,cend from course ";
                Statement st;
                courseList.clear();
                try {
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()){
                        Course test = new Course(rs.getString(1),"星期"+rs.getString(2)+"  "+"第"+rs.getString(3)+"-"+rs.getString(4)+"节");
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        //layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(onItemClickListener);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
