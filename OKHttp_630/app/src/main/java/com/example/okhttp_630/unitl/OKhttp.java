package com.example.okhttp_630.unitl;

import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 同步okhttp请求
 */
public class OKhttp {
    private static final Handler handlerUI = new Handler(Looper.getMainLooper());
    private  static OkHttpClient client = new OkHttpClient();

    //同步请求
    public static String sync_get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //异步请求
    public static void asyn_get(String url, ICallBack callback){
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handlerUI.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailed(e);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String resp = response.body().string();
                        handlerUI.post(new Runnable() {
                            @Override
                            public void run() {
                                   callback.success(resp);
                            }
                        });
                    }
                });
    }
    //post请求
    public static void post(String url, HashMap<String,String> params,ICallBack callBack){
        FormBody.Builder builder = new FormBody.Builder();
        if(params != null){
            for(String param:params.keySet()){
                builder.add(param,params.get(param));
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                handlerUI.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.success(resp);
                    }
                });
            }
        });
    }
}



















