package student.example.signstudent;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SignActivity extends AppCompatActivity {
    private String course = null;
    private TextView _tvName,_tvTime,_tvLast,_tvDistance;
    public LocationClient mLocationClient = null;

    public BDLocationListener myListener = new MyLocationListener();
    private boolean isFirstLoc = true;
    private LatLng latLng,latLng2;
    private static final double EARTH_RADIUS = 6371393;
    private double distance;
    private boolean isPosition = false;
    private Button _btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_sign);

        Intent intent = getIntent();
        course = intent.getStringExtra("course");
        init();
        initMap();

    }

    private void init(){
        _tvName = (TextView)findViewById(R.id.tv_scname);
        _tvName.setText(course);
        _tvTime = (TextView)findViewById(R.id.tv_sctime);
        _tvLast = (TextView)findViewById(R.id.tv_sclast);

        String type = "sfetchSign";
        sendRequestWithHttpURLConection(type,course);
        final NumberData data = (NumberData) getApplication();
        final String number = data.getNumber();
        _btnSign = (Button)findViewById(R.id.btn_sign);
        _btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPosition){
                    String type = "ssign";
                    sendRequestWithHttpURLConection2(type,number,course);
//                    Intent intent = new Intent(SignActivity.this,LoadcourseActivity.class);
//                    startActivity(intent);
                    Toast.makeText(SignActivity.this,"签到成功！",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(SignActivity.this,"当前位置不能签到！",Toast.LENGTH_SHORT).show();


                }
            }
        });
        _tvDistance = (TextView)findViewById(R.id.tv_scdistance);
    }

    private void initMap(){
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (isFirstLoc){
                isFirstLoc = false;
            }else{
                distance = getDistance(latLng,latLng2);
                _tvDistance.setText("距离签到地点"+(int)distance+"米");
                Log.d("distance",(int)distance+"");
                if(distance < 1500.0){
                    isPosition = true;
                }else {
                    isPosition = false;
                }
            }

        }
    }

    private void sendRequestWithHttpURLConection2(final String type,final String number,final String course) {
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
                    json.put("snumber", number);
                    json.put("course", course);
                    String jsonstr = json.toString();
                    Log.d("json",jsonstr);
                    OutputStream out = connection.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                    bw.write(jsonstr);
                    bw.flush();
                    bw.close();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {}
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

    private void sendRequestWithHttpURLConection(final String type,final String course) {
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
                    json.put("course", course);
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
                        Log.d("rjson",rjson.toString());
                        String result = rjson.getString("success");
                        String position = rjson.getString("position");
                        String time = rjson.getString("time");
                        String timeLast = rjson.getString("timelast");

                        _tvTime.setText(time);
                        //_tvLast.setText(timeLast);
                        String []str = position.split(",");
                        latLng2 = new LatLng(Double.parseDouble(str[0]),Double.parseDouble(str[1]));
                        showResponse(time,timeLast);
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

    private void showResponse(final String time,final String timelast){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                    Date endTime = new Date(startTime.getTime()+60000*Integer.parseInt(timelast));
                    Date nowTime = new Date();
                    long a = nowTime.getTime();
                    long b = endTime.getTime();
                    int interval = (int)((b - a) / 1000);
                    CountDownTimer timer = new CountDownTimer(interval*1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            _tvLast.setText("距离签到结束还有" + millisUntilFinished / 1000 + "秒");
                        }

                        public void onFinish() {
//                            Intent intent = new Intent(SignActivity.this, LoadcourseActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                    };
                    //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
                    timer.start();
                    Log.d("interval",interval+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static double getDistance(LatLng pointA, LatLng pointB) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(pointA.latitude); // A经弧度
        double radiansAY = Math.toRadians(pointA.longitude); // A纬弧度
        double radiansBX = Math.toRadians(pointB.latitude); // B经弧度
        double radiansBY = Math.toRadians(pointB.longitude); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return EARTH_RADIUS * acos; // 最终结果
    }

}
