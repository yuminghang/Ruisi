package org.xidian.ruisi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xidian.ruisi.MyApplication;
import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.MessageListAdapter;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.base.BaseActivity;
import org.xidian.ruisi.bean.MyMessageData;

import java.io.IOException;
import java.util.ArrayList;

public class MyMessageActivity extends BaseActivity {
    private ListView listView;
    private TextView tv_title;
    ArrayList<MyMessageData> list = new ArrayList<MyMessageData>();
    private String html;
    private Document doc;
    private Elements elements;
    private MessageListAdapter mListAdapter;
    private RelativeLayout back;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 收到消息后执行handler
            mListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initView();
        parseData();
    }

    private void initView() {
        back = (RelativeLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的消息");
        mListAdapter = new MessageListAdapter(this, list);
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
            elements = doc.select("div.pmbox").select("ul").select("li");
            for (int i = 0; i < elements.size(); i++) {
                MyMessageData bean = new MyMessageData();
                bean.avatar = elements.get(i).select("div.avatar_img").select("img").attr("src");
                bean.url = elements.get(i).select("a").attr("href");
                bean.talker = elements.get(i).select("a").select("div.cl").select("span").get(0).text().trim();
                bean.time = elements.get(i).select("a").select("div.cl").select("span").get(1).text().trim();
//                elements.get(i).select("a").select("div.c1.grey").select("span.time").remove();
                bean.message = elements.get(i).select("a").select("div.cl").select("span").get(2).text().trim();
                list.add(bean);
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
                    .url(Apis.MessageUrl)
                    .build();
        } else {
            request = new com.squareup.okhttp.Request.Builder()
//                    .addHeader("Cookie", MyApplication.myCookie)
                    .url(Apis.MessageUrl)
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
