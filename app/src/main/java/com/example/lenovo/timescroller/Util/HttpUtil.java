package com.example.lenovo.timescroller.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public class HttpUtil {
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static HttpUtil httpUtil;
    private static OkHttpClient client;
    private HttpCallBack callBack;

    static {
        client = new OkHttpClient().newBuilder().readTimeout(30, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS).build();
    }

    private Handler mHandler;

    private HttpUtil() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }


    public Response get(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void getAsync(String url, final HttpCallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onLoading();
            }
        });
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    final String result = response.body().string();
                    Log.d("=======",result);
                    if (response.isSuccessful()) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(result);
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Response post(String url, String json) {
        Response response = null;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void postAsync(String url, String json, final HttpCallBack callBack) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onLoading();
            }
        });
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSuccess(response.body().string());
            }
        });
    }

    public interface HttpCallBack {
        void onLoading();

        void onSuccess(String result);

        void onError(String error);
    }


}
