package com.example.okhttp_630.unitl;

import okhttp3.Response;

public interface ICallBack {
    void success(String response);
    void onFailed(Throwable exp);
}
