package student.example.signstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoadcourseActivity extends AppCompatActivity {
    private ListView _lvCourse;
    private List<Course> courseList = new ArrayList<>();
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadcourse);
        init();
    }

    private void init(){
        _lvCourse = (ListView) findViewById(R.id.lv_course);
        adapter = new CourseAdapter(LoadcourseActivity.this,R.layout.course_item,courseList);

        String type = "sfetchCourse";
        final NumberData data = (NumberData) getApplication();
        String number = data.getNumber();
        if(number.equals("null")){
            Toast.makeText(LoadcourseActivity.this,"用户未登录!",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            //Toast.makeText(LoadcourseActivity.this,number,Toast.LENGTH_SHORT).show();
            sendRequestWithHttpURLConection(type,number);
        }
        _lvCourse.setAdapter(adapter);

        _lvCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(courseList.get(position).getIsOngoing().equals("true")&&courseList.get(position).getIsSigned().equals("false")){
                    Intent intent = new Intent(LoadcourseActivity.this,SignActivity.class);
                    intent.putExtra("course",courseList.get(position).getName());
                    startActivity(intent);
                }else {
                    Toast.makeText(LoadcourseActivity.this,"该课程无需签到或您已签到成功",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void sendRequestWithHttpURLConection(final String type,final String number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {

                    //获取HttpURLConnection实例，传入目标网络地址
                    URL url = new URL("http://112.124.24.195");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.connect();
                    JSONObject json = new JSONObject();
                    json.put("type", type);
                    json.put("number", number);
                    String jsonstr = json.toString();
                    OutputStream out = connection.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                    bw.write(jsonstr);
                    bw.flush();
                    bw.close();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream in = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                            Log.d("line",line);
                        }
                        in.close();
                        JSONObject rjson = new JSONObject(response.toString());
                        Log.d("json",rjson.toString());
                        String result = rjson.getString("success");
                        JSONArray cjson = rjson.getJSONArray("course");
                        for(int i = 0;i < cjson.length();i++){
                            JSONObject object = cjson.getJSONObject(i);
                            String name = object.getString("name");
                            String time = object.getString("time");
                            String start = object.getString("start");
                            String end = object.getString("end");
                            String isOn = object.getString("ongoing");
                            String isSign = object.getString("signed");
                            Course course = new Course(name,time,start,end,isOn,isSign);
                            courseList.add(course);
                            adapter.notifyDataSetChanged();
                        }
                        //showResponse(result);
//                        if(result.equals("true")){
//                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
//                        }
                        //  Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();


    }

}
