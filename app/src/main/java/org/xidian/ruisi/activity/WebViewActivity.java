package org.xidian.ruisi.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.CommentListAdapter;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.bean.NewsDetailData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {
    private String url, title;
    private static ProgressBar pb;
    private ListView listView;
    private View view_WebView;
    private WebView mWebView;
    //正文内容相关变量
    Document doc;
    private String contentHtml;//正文html代码
    String topic;
    private ImageView avatarView;
    private TextView authorView;
    private TextView timeView;
    private TextView positionView;
    List<NewsDetailData> datas = new ArrayList<NewsDetailData>();
    private Elements elements;
    private Elements kidsData;
    private BaseAdapter mListAdapter;
    private NewsDetailData author;

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
        setContentView(R.layout.activity_web_view);
        initView();
        url = Apis.BASEURL + getIntent().getStringExtra("url");
        url = url.replaceAll("&amp;", "&");
        getData();
    }

    private void initView() {
        view_WebView = View.inflate(this, R.layout.webview_item, null);
        authorView = (TextView) view_WebView.findViewById(R.id.author);
        timeView = (TextView) view_WebView.findViewById(R.id.time);
        mWebView = (WebView) view_WebView.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        listView = (ListView) findViewById(R.id.listView);
        mListAdapter = new CommentListAdapter(this, datas);
        listView.setAdapter(mListAdapter);
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
            conn.header("User-Agent", "Mozilla/5.0 (Linux; U; Android 5.1; zh-CN; MX5 Build/LMY47I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 UCBrowser/10.9.9.739 U3/0.8.0 Mobile Safari/534.30");
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
            /**
             contentHtml = String.valueOf(doc.getElementsByAttributeValue("class", "plc cl").get(0).childNodes().get(3));
             start2 = contentHtml.indexOf("</ul>");
             start2 += 5;
             contentHtml = contentHtml.substring(start2);
             start2 = contentHtml.indexOf("<i");
             end2 = contentHtml.indexOf("</i>");
             end2 += 13;
             contentHtml = contentHtml.substring(0, start2) + contentHtml.substring(end2);
             */
            // 执行完毕后给handler发送一个空消息
            elements = doc.select("div.postlist");
            setTopic();
            setNextPage();
            setPreviousPage();
            handler.sendEmptyMessage(0);
        }
    };


    private void setTopic() {

        kidsData = doc.select("div[id^=pid]");
        for (int i = 0; i < kidsData.size(); i++) {
            NewsDetailData follower = new NewsDetailData();
            if (0 == i) {
                follower.title = doc.select("h2").text().trim();
            } else {
                follower.position = kidsData.get(i).select("li.grey").select("em").text();
            }
            follower.avatar = kidsData.get(i).select("span.avatar").select("img").attr("src");
            follower.position = kidsData.get(i).select("li.grey").select("em").text();
            follower.id = kidsData.get(i).select("li.grey").select("b").select("a").text();
            follower.time = kidsData.get(i).select("li.grey.rela").text();
            follower.content = kidsData.get(i).select("div.message").text();
            datas.add(i, follower);
        }
    }

    private void setNextPage() {

    }

    private void setPreviousPage() {

    }
}

