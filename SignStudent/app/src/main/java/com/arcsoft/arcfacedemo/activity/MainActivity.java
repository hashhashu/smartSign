package com.arcsoft.arcfacedemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.arcsoft.arcfacedemo.R;

public class MainActivity extends AppCompatActivity {
    private Button _btnRe,_btnLg,_btnAdd,_btnShow;
    private String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NumberData data = (NumberData) getApplication();
        number = data.getNumber();
        Log.d("number",number);
        _btnLg = (Button)findViewById(R.id.btn_lg);
        _btnAdd=(Button)findViewById(R.id.btn_add);
        _btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddcourseActivity.class);
                startActivity(intent);
            }
        });
        _btnShow=(Button)findViewById(R.id.btn_show);
        _btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoadcourseActivity.class);
                startActivity(intent);
            }
        });
        _btnLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        _btnRe = (Button)findViewById(R.id.btn_re);
        _btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        if(!number.equals("null")){
            _btnAdd.setVisibility(View.VISIBLE);
            _btnShow.setVisibility(View.VISIBLE);
        }else {
            _btnAdd.setVisibility(View.INVISIBLE);
            _btnShow.setVisibility(View.INVISIBLE);
        }

    }
}
