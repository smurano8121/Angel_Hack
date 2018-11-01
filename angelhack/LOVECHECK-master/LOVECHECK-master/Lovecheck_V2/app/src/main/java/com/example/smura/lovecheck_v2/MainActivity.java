package com.example.smura.lovecheck_v2;

import java.io.IOException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Handler redrawHandler;
    String text = "";
    private TextView textView;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redrawHandler = new Handler();

        //ボタン設定
        Button button = findViewById(R.id.POST);
        textView = findViewById(R.id.MainText);
        //ボタンをクリックしたときの処理
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aaa = "http://192.168.201.23:3000/";
                OkHttpClient client = new OkHttpClient();
                MediaType MIMEType = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(MIMEType, "{\"Id\":\"1\", \"HeartBeat\":100}");
                Request request = new Request.Builder().url(aaa).post(body).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // nothing
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        text = response.body().string();
                        redrawHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView)findViewById(R.id.MainText)).setText(text);
                            }
                        });
                    }
                });
            }
        });
    }
}