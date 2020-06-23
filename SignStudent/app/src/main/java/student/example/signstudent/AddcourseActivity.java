package student.example.signstudent;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddcourseActivity extends AppCompatActivity {
    EditText _etCname,_etCteacher;
    Button _btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        init();
    }

    private void init(){
        _etCname = (EditText)findViewById(R.id.et_cname);
        _etCteacher = (EditText)findViewById(R.id.et_cteacher);

        _btnAdd = (Button)findViewById(R.id.btn_add);
        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "saddCourse";
                String cname = _etCname.getText().toString().trim();
                String cteacher = _etCteacher.getText().toString().trim();
                final NumberData data = (NumberData) getApplication();
                String number = data.getNumber();
                Log.d("number",number);
                if(number.equals("null")){
                    Toast.makeText(AddcourseActivity.this,"用户未登录!",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    //Toast.makeText(AddcourseActivity.this,number,Toast.LENGTH_SHORT).show();
                    sendRequestWithHttpURLConection(type,number,cname,cteacher);
                }

            }
        });

    }

    private void sendRequestWithHttpURLConection(final String type,final String number,final String cname,final String cteacher) {
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
                    json.put("course", cname);
                    json.put("teacher",cteacher);
                    String jsonstr = json.toString();
                    Log.d("json",jsonstr);
                    OutputStream out = connection.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                    bw.write(jsonstr);
                    bw.flush();
                    bw.close();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        InputStream in = connection.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        in.close();

                        JSONObject rjson = new JSONObject(response.toString());
                        String result = rjson.getString("success");
                        Log.d("result",result);
                        showResponse(result);
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

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(response.equals("true")){
                    Toast.makeText(AddcourseActivity.this,"添加成功！",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(AddcourseActivity.this,"添加失败！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
