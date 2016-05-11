package org.xidian.ruisi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.ArticlesActivity_ListAdapter;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.bean.ArticleListData;
import org.xidian.ruisi.bean.GridViewBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticlesActivity extends Activity {

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


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 收到消息后执行handler
            mListAdapter.notifyAll(datas);

        }
    };
    private ArticlesActivity_ListAdapter mListAdapter;
    private RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_articles);
        url = Apis.BASEURL + getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("name");
        initView();
        initListView();
        Log.e("dwasdqw", url);
        getData();
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

    private void getData() {
        try {
            new Thread(runnable).start();  // 子线程
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Connection conn = Jsoup.connect(url);
            // 修改http包中的header,伪装成浏览器进行抓取
//            conn.header("User-Agent", "Mozilla/5.0 (Linux; U; Android 5.1; zh-CN; MX5 Build/LMY47I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 UCBrowser/10.9.9.739 U3/0.8.0 Mobile Safari/534.30");
            doc = null;
            try {
                doc = conn.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取下一页的链接
//            Elements link = doc.select("li.paginate_button").select("li.next");
//            next_page_url = link.select("a").attr("href");
            // 获取tbody元素下的所有tr元素
//            Elements elements = doc.select("div.postlist");
            // 执行完毕后给handler发送一个空消息
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
}
