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
import org.xidian.ruisi.R;
import org.xidian.ruisi.api.Apis;

import java.io.IOException;

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
    private String avatar;
    private String author;
    private String time;
    private String lastEditTime;
    int start, start1, start2, end2, end, end1;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 收到消息后执行handler
            show();
        }
    };

    private void show() {
//        if (datas.size() == 0) {
////            TextView message = (TextView) findViewById(R.id.message);
////            message.setText(R.string.message);
//        } else {
//            HomeFragment_ListView_Adapter adapter = new HomeFragment_ListView_Adapter(getActivity(), datas);
//            listView.setAdapter(adapter);
//        }
        mWebView.loadDataWithBaseURL(null, contentHtml, "text/html", "utf-8", null);
        Glide.with(this).load(avatar).into(avatarView);
        authorView.setText(author);
        timeView.setText(time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        url = Apis.BASEURL + getIntent().getStringExtra("url");
        url = url.replaceAll("&amp;", "&");
//        pb = (ProgressBar) findViewById(R.id.pb);
//        pb.setVisibility(View.VISIBLE);
//        webview.loadUrl(url);
        getData();
    }

    private void initView() {
        view_WebView = View.inflate(this, R.layout.webview_item, null);
        avatarView = (ImageView) view_WebView.findViewById(R.id.avatar);
        authorView = (TextView) view_WebView.findViewById(R.id.author);
        timeView = (TextView) view_WebView.findViewById(R.id.time);
        mWebView = (WebView) view_WebView.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        listView = (ListView) findViewById(R.id.listView);
        listView.addHeaderView(view_WebView);
        final String[] temp = new String[]{
                "12323", "12323", "12323", "12323", "12323", "12323", "12323", "12323"
        };
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return temp.length;
            }

            @Override
            public Object getItem(int position) {
                return temp[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(WebViewActivity.this);
                textView.setText(temp[position]);
                return textView;
            }
        });
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
//            for (int i = 0; i < elements.get(0).children().size(); i++) {
//                Element element = elements.get(0).children().get(i);
//                String title = element.getElementsByTag("a").get(0).childNodes().get(0).toString();
//                String author = element.getElementsByTag("a").get(0).childNodes().get(1).toString();
////                start = author.indexOf("\"by\">") + 5;
////                end = author.indexOf("</span>");
////                author = author.substring(start, end);
////                String commentNums = element.getElementsByTag("a").get(0).childNodes().get(3).toString();
////                start1 = commentNums.indexOf("\"num\">") + 6;
////                end1 = commentNums.indexOf("</span>");
////                commentNums = commentNums.substring(start1, end1);
////                url = element.getElementsByTag("a").get(0).attributes().toString();
////                url = url.substring(7, url.length() - 1);
////                Map<String, Object> map = new HashMap<>();
////                data = new HomeFragment_ListViewData();
////                data.setTitle(title);
////                data.setAuthor(author);
////                data.setCommentNums(commentNums);
////                data.setUrl(url);
////                datas.add(data);
//            }
            // 执行完毕后给handler发送一个空消息
            doc.getElementsByAttributeValue("class", "plc cl");

//            Elements elements = doc.select("ul.hotlist");
//            for (int i = 0; i < elements.get(0).children().size(); i++) {
//                Element element = elements.get(0).children().get(i);
//                String title = element.getElementsByTag("a").get(0).childNodes().get(0).toString();
//                String author = element.getElementsByTag("a").get(0).childNodes().get(1).toString();
//                start = author.indexOf("\"by\">") + 5;
//                end = author.indexOf("</span>");
//                author = author.substring(start, end);
//                String commentNums = element.getElementsByTag("a").get(0).childNodes().get(3).toString();
//                start1 = commentNums.indexOf("\"num\">") + 6;
//                end1 = commentNums.indexOf("</span>");
//                commentNums = commentNums.substring(start1, end1);
//                url = element.getElementsByTag("a").get(0).attributes().toString();
//                url = url.substring(7, url.length() - 1);
//                Map<String, Object> map = new HashMap<>();
//                data = new HomeFragment_ListViewData();
//                data.setTitle(title);
//                data.setAuthor(author);
//                data.setCommentNums(commentNums);
//                data.setUrl(url);
//                datas.add(data);
//
            setTopic();
            setAvatar();
            setComment();
            setNextPage();
            setPreviousPage();
            handler.sendEmptyMessage(0);
        }
    };


    private void setTopic() {
        topic = doc.getElementsByAttributeValue("class", "plc cl").get(0).toString();
//        contentHtml = Jsoup.parse((Jsoup.parse(topic.toString()).select("li.grey")).toString()).getElementsByTag("b").select("img").toString();
//        contentHtml = Jsoup.parse(topic).select("div.message").text().toString();

    }

    private void setAvatar() {
        avatar = Jsoup.parse(topic).select("span.avatar").select("img").toString();
        start = avatar.indexOf("src=\"");
        start += 5;
        end = avatar.indexOf("\" style=");
        avatar = avatar.substring(start, end);
        author = Jsoup.parse(Jsoup.parse((Jsoup.parse(topic).select("li.grey")).
                toString()).getElementsByTag("b").toString()).getElementsByTag("a").text().toString();
        time = Jsoup.parse(Jsoup.parse((Jsoup.parse(topic.toString()).select("li")).get(1).toString()).toString()).text().substring(2);
    }

    private void setComment() {

    }

    private void setNextPage() {

    }

    private void setPreviousPage() {

    }
}

