package com.arcsoft.arcfacedemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private EditText _etSname,_etSex,_etSchool,_etSno,_etNumber,_etPsd;
    private Button _btnRegister;
    private ImageView _imgRt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        _etSname = (EditText)findViewById(R.id.et_sname);
        _etSex = (EditText)findViewById(R.id.et_sex);
        _etSchool = (EditText)findViewById(R.id.et_school);
        _etSno = (EditText)findViewById(R.id.et_son);
        _etNumber = (EditText)findViewById(R.id.et_number);
        _etPsd = (EditText)findViewById(R.id.et_password);
        _imgRt = (ImageView)findViewById(R.id.img_rt1);
        _imgRt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        _btnRegister = (Button)findViewById(R.id.btn_register);
        _btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this,"11111",Toast.LENGTH_LONG).show();
                String name = _etSname.getText().toString().trim();
                String ssex = _etSex.getText().toString().trim();
                String school = _etSchool.getText().toString().trim();
                String no = _etSno.getText().toString().trim();
                String number = _etNumber.getText().toString().trim();
                String psd = _etPsd.getText().toString().trim();
                int sex;
                if(ssex.equals("男"))
                    sex = 1;
                else
                    sex = 0;
                String type = "sregister";
                sendRequestWithHttpURLConection(type,name,sex,school,no,number,psd);
            }
        });
    }

    private void sendRequestWithHttpURLConection(final String type,final String name,final int sex,final String school,final String no,final String number,final String psd) {
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
                    json.put("name", name);
                    json.put("sex", sex);
                    json.put("school", school);
                    json.put("sid", no);
                    json.put("number", number);
                    json.put("password", psd);
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
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        in.close();

                        JSONObject rjson = new JSONObject(response.toString());
                        String result = rjson.getString("success");
                        showResponse(result);
                        Log.d("result",result);
//                        if(result.equals("true")){
//                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"该手机号以注册，请从新输入！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
