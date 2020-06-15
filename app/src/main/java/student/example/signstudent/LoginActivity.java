package student.example.signstudent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    EditText _etNumber,_etPsd;
    Button _btnLogin;
    TextView _tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        _etNumber = (EditText) findViewById(R.id.et_username);
        _etPsd = (EditText)findViewById(R.id.et_password);

        _btnLogin = (Button)findViewById(R.id.btn_login);
        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "slogin";
                String number = _etNumber.getText().toString().trim();
                String psd = _etPsd.getText().toString().trim();
                sendRequestWithHttpURLConection(type,number,psd);
            }
        });

        _tvRegister = (TextView)findViewById(R.id.tv_register);
        _tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendRequestWithHttpURLConection(final String type,final String number,final String psd) {
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
                    json.put("password", psd);
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
                String number = _etNumber.getText().toString().trim();
                if(response.equals("true")){
                    final NumberData data = (NumberData) getApplication();
                    data.setNumber(number);
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新输入！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
