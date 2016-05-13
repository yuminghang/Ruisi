package org.xidian.ruisi.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xidian.ruisi.MyApplication;
import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.DatumListAdapter;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.base.BaseActivity;
import org.xidian.ruisi.view.CircleImageView;

import java.io.IOException;

public class MyDatumActivity extends BaseActivity {
    private CircleImageView avatar;
    private ListView listView;
    private TextView tv_title;
    private String[] list = new String[8];
    private String html;
    private String mAvatarUrl;
    private String userName;
    private TextView userLevelView, userNameView;
    private Document doc;
    private Elements elements;
    private DatumListAdapter mListAdapter;
    private RelativeLayout back;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 收到消息后执行handler
            mListAdapter.notifyDataSetChanged();
            Glide.with(MyDatumActivity.this).load(mAvatarUrl).into(avatar);
            userNameView.setText(userName);
            userLevelView.setText(MyApplication.userGroup);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datum);
        initView();
        parseData();
    }

    private void initView() {
        userNameView = (TextView) findViewById(R.id.userName);
        userLevelView = (TextView) findViewById(R.id.userLevel);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        back = (RelativeLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的资料");
        mListAdapter = new DatumListAdapter(this, list);
        listView.setAdapter(mListAdapter);
    }

    private void parseData() {
        try {
            new Thread(runnable).start();  // 子线程
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getData();
            doc = null;
            try {
                doc = Jsoup.parse(html);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Elements elements = doc.select("div.userinfo");
            mAvatarUrl = elements.select("div.avatar_m").select("span").select("img").attr("src");
            userName = elements.select("h2.name").text().trim();
            Elements elements1 = elements.select("div.user_box").select("ul").select("li");
            for (int i = 0; i < elements1.size(); i++) {
                list[i] = elements1.get(i).select("span").text().trim();
            }
            handler.sendEmptyMessage(0);
        }
    };


    private void getData() {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request;
        if (MyApplication.myCookie != null) {
            request = new com.squareup.okhttp.Request.Builder()
                    .addHeader("Cookie", MyApplication.myCookie)
                    .url(Apis.DatumUrl)
                    .build();
        } else {
            request = new com.squareup.okhttp.Request.Builder()
//                    .addHeader("Cookie", MyApplication.myCookie)
                    .url(Apis.DatumUrl)
                    .build();
        }


        //发送请求获取响应
        try {
            Response response = okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                //打印服务端返回结果
                html = response.body().string();
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
