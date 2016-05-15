package org.xidian.ruisi.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.CommentListAdapter;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.base.BaseActivity;
import org.xidian.ruisi.bean.NewsDetailData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends BaseActivity {
    private String url, title;
    private static ProgressBar pb;
    private ListView listView;
    private View view_WebView;
    private WebView mWebView;
    //正文内容相关变量
    private TextView timeView;
    private TextView positionView;
    List<NewsDetailData> datas = new ArrayList<NewsDetailData>();
    private BaseAdapter mListAdapter;
    private NewsDetailData author;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        url = Apis.BASEURL + getIntent().getStringExtra("url");
        url = url.replaceAll("&amp;", "&");
        initView();
        mWebView.loadUrl(url);

    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

}

