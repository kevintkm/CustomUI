package com.example.lenovo.timescroller.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.timescroller.Model.BaseRequest;
import com.example.lenovo.timescroller.Model.GetJoinDetailRequest;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.Util;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by kevin.tian on 2016/8/9.
 * 具体详情可参考http://blog.csdn.net/iispring/article/details/51661195
 */
public class MoudleLearningFragment extends Fragment {
    TextView result;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            result.setText(msg.obj.toString());
        }
    };

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moudle_layout, null);
        result = (TextView) view.findViewById(R.id.moudle_http_text);
        view.findViewById(R.id.moudle_httpget_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOkHttpGet();
            }
        });
        view.findViewById(R.id.moudle_httppost_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOkHttpPost();
            }
        });
        view.findViewById(R.id.moudle_httppostasync_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOkHttpAsyncGet();
            }
        });
        return view;
    }

    private void getOkHttpAsyncGet() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/gists/c2a7c39532239ff261be").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = mHandler.obtainMessage();
                message.obj = response.body().string();
                mHandler.sendMessage(message);
            }
        });
    }

    /**
     * @Description: 常见的http content type
     * 1.text/html
     * 2.text/plain
     * 3.text/css
     * 4.text/javascript
     * 5.application/x-www-form-urlencoded
     * 6.multipart/form-data
     * 7.application/json
     * 8.application/xml
     * @param ${tags}
     * @return ${return_type}
     * @throws
     */
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    private void getOkHttpPost() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseRequest baseRequest = new BaseRequest();
                GetJoinDetailRequest getJoin = new GetJoinDetailRequest();
                getJoin.setPage(1);
                getJoin.setShopType("1");
                getJoin.setGoodsId("100507444_292326");
                getJoin.setFindType(0);
                getJoin.setPage_size("10");
                baseRequest.setBaseAppType(Util.getSystemType());
                baseRequest.setBaseAppVersion("1.0.0");
                baseRequest.setSystemVersion(Build.VERSION.RELEASE);
                baseRequest.setAppIdentifier("com.weimob.indiana.dev");
                baseRequest.setParameterInput(getJoin);
                baseRequest.setTimeStamp("2016-08-12T11:39:41.761Z");
                baseRequest.set_sign_("8212D51FAB0B164CB8D0A786361ED035");
                OkHttpClient client = new OkHttpClient();
                Gson gson = new Gson();
                RequestBody body = RequestBody.create(JSON, gson.toJson(baseRequest));
                Log.d("=========", gson.toJson(baseRequest));
                Request request = new Request.Builder()
                        .url("http://api.dev.vd.cn/duobao/indexAdapter/getJoinDetail")
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Message message = mHandler.obtainMessage();
                    message.obj = response.body().string();
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void getOkHttpGet() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://www.baidu.com").build();
                try {
                    Response response = client.newCall(request).execute();
                    Message message = mHandler.obtainMessage();
                    message.obj = response.body().string();
                    mHandler.sendMessage(message);
                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
