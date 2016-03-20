package com.ecust.ecusthelper.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.ecust.ecusthelper.R;
import com.ecust.ecusthelper.interfacer.IHttp;
import com.ecust.ecusthelper.net.common.HttpCallback;
import com.ecust.ecusthelper.net.common.HttpRequest;
import com.ecust.ecusthelper.net.common.HttpResponse;
import com.ecust.ecusthelper.net.httpconnecter.HttpFactory;
import com.ecust.ecusthelper.util.ConvertUtil;

public class MainActivity extends AppCompatActivity {

    //Todo:轮循、CordinderLayout、视差动画


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton

        new Thread(new Runnable() {
            @Override
            public void run() {
                IHttp http = HttpFactory.newHttpUrlConnection();
                http.getSynchronousData("http://www.baidu.com", new HttpCallback() {
                    @Override
                    public void onResponse(HttpRequest request, HttpResponse response) {
                        Log.i("aaa", response.getResponseBody());
                    }

                    @Override
                    public void onError(HttpRequest request, Exception e) {

                    }
                });


                http.getSynchronousData("http://news.ecust.edu.cn/uploads/top_news/first_image/33/03.jpg",
                        new HttpCallback() {
                            @Override
                            public void onResponse(HttpRequest request, HttpResponse response) {
                                try {
                                    byte[] bytes = ConvertUtil.inputStream2bytes(response.getInputStream());
                                    final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);


                                    final ImageView iv = (ImageView) findViewById(R.id.iv);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            iv.setImageBitmap(bitmap);
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(HttpRequest request, Exception e) {

                            }
                        });
            }
        }).start();


    }


}
