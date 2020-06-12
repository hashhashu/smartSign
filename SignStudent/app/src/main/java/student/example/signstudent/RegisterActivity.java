package student.example.signstudent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText _etSname,_etSex,_etSchool,_etSno,_etNumber,_etPsd;
    private Button _btnRegister;


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

                sendRequestWithHttpURLConection("sregister","sqf",1,"浙江工商大学","1902010007","15968188846","11");
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
                    URL url = new URL("http://192.168.43.70");
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
                    Toast.makeText(RegisterActivity.this, jsonstr, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(RegisterActivity.this, rjson.toString(), Toast.LENGTH_LONG).show();
                        String result = rjson.getString("success");
                        Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();

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
