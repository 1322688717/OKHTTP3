package com.example.okhttp_630;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okhttp_630.unitl.ICallBack;
import com.example.okhttp_630.unitl.OKhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 同步okhttp请求实现项目
 */
public class MainActivity extends AppCompatActivity {
    String url = "http://101.132.121.132:8080//Monitor/app/user/login.do?login_p1=fanchao&password=444";
    private Button btn_Sycn,btn_post,btn_Asycn;
    private TextView tv_url;
    private Handler handlerUI = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        initview();

        //同步get请求
        initgetSycn();

        //异步get请求
        initgetAsync();

        //post请求
        intitpost();
        
    }

    private void intitpost() {
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OKhttp.post(url, null, new ICallBack() {
                    @Override
                    public void success(String response) {
                        tv_url.setTextSize(26);
                        tv_url.setText(response);
                    }

                    @Override
                    public void onFailed(Throwable exp) {
                        Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initgetAsync() {
        btn_Asycn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OKhttp.asyn_get(url, new ICallBack() {
                    @Override
                    public void success(String response) {
                        tv_url.setTextSize(26);
                        tv_url.setText(response);
                    }

                    @Override
                    public void onFailed(Throwable exp) {
                        Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initgetSycn() {
        btn_Sycn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String i = OKhttp.sync_get(url);
                        handlerUI.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_url.setTextSize(26);
                                tv_url.setText(i);
                            }
                        });
                    }
                }).start();
            }
        });
    }
    private void initview() {
        btn_Sycn = findViewById(R.id.btn_Sycn_get);
        btn_Asycn = findViewById(R.id.btn_Async_get);
        tv_url = findViewById(R.id.tv_url);
        btn_post = findViewById(R.id.btn_post);
    }
}
