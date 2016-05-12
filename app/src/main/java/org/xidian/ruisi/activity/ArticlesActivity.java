package org.xidian.ruisi.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.ArticlesActivity_ListAdapter;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.bean.ArticleListData;
import org.xidian.ruisi.base.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticlesActivity extends BaseActivity {

    private String url;
    private String mTitle;
    private WebView webView;
    private TextView title;
    private RelativeLayout top;
    private ListView mListView;
    List<ArticleListData> datas = new ArrayList<ArticleListData>();
    private Document doc;
    private Elements elements;
    long clickTime;
    private ArticlesActivity_ListAdapter mListAdapter;
    private RelativeLayout back;
    private String html;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 收到消息后执行handler
            mListAdapter.notifyAll(datas);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        url = Apis.BASEURL + getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("name");
        initView();
        initListView();
        Log.e("dwasdqw", url);
        parseData();
    }

    private void initListView() {
        mListAdapter = new ArticlesActivity_ListAdapter(this, datas);
        mListView.setAdapter(mListAdapter);
    }

    private void initView() {
        top = (RelativeLayout) findViewById(R.id.top);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((System.currentTimeMillis() - clickTime) > 2000) {
                    clickTime = System.currentTimeMillis();
                } else {
                    mListView.smoothScrollToPosition(0);
                }
            }
        });
        title = (TextView) findViewById(R.id.tv_title);
        back = (RelativeLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText(mTitle);
        mListView = (ListView) findViewById(R.id.listView);
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
            elements = doc.select("div.threadlist").select("li");
            for (int i = 0; i < elements.size(); i++) {
                ArticleListData data = new ArticleListData();
                data.url = elements.get(i).select("a").attr("href");
                data.id = elements.get(i).select("a").select("span").text().trim();
                elements.get(i).select("a").select("span").remove();
                data.title = elements.get(i).select("a").text().trim();
                data.commentNum = elements.get(i).select("span.num").text().trim();
                if (elements.get(i).select("span.icon_top").size() > 0) {
                    data.des = 1; //精华
                } else if (elements.get(i).select("span.icon_tu").size() > 0) {
                    data.des = 2;  //有图
                } else {
                    data.des = 0;  //啥也没有
                }
                datas.add(i, data);
            }
            handler.sendEmptyMessage(0);
        }
    };

    private void getData() {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        String myCookie = getSharedPreferences("cookie", Context.MODE_PRIVATE).getString("my_cookie", "");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new com.squareup.okhttp.Request.Builder()
                .addHeader("cookie", myCookie)
                .url(url)
                .build();

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
