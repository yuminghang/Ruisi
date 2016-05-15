package org.xidian.ruisi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xidian.ruisi.R;
import org.xidian.ruisi.activity.ArticlesActivity;
import org.xidian.ruisi.activity.WebViewActivity;
import org.xidian.ruisi.adapter.HomeFragment_ListView_Adapter;
import org.xidian.ruisi.adapter.HomeFragment_ViewPagerAdapter;
import org.xidian.ruisi.adapter.HomeFragment_GridAdapter;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.bean.HomeFragment_ListViewData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ymh on 2016/5/6.
 */
public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private GridView gridView;
    private ListView listView;
    private int[] icon = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5,
            R.drawable.icon6, R.drawable.icon7, R.drawable.icon8};

    private String[] mNameList = {"心灵花园", "我是女生", "缘聚睿思", "西电问答", "我要毕业啦", "学习交流", "嵌入式技术",
            "竞赛交流"
    };
    private String[] mUrlList = {"http://bbs.rs.xidian.me/forum.php?mod=forumdisplay&fid=550&mobile=2", "http://bbs.rs.xidian.me/forum.php?mod=forumdisplay&fid=108&mobile=2",
            "http://bbs.rs.xidian.me/forum.php?mod=forumdisplay&fid=217&mobile=2", "http://bbs.rs.xidian.me/forum.php?mod=forumdisplay&fid=551&mobile=2",
            "http://bbs.rs.xidian.me/forum.php?mod=forumdisplay&fid=552&mobile=2", "http://bbs.rs.xidian.me/forum.php?mod=forumdisplay&fid=548&mobile=2",
            "http://bbs.rs.xidian.me/forum.php?mod=forumdisplay&fid=144&mobile=2", "http://bbs.rs.xidian.me/forum.php?mod=forumdisplay&fid=152&mobile=2"};
    private List<ImageView> viewList;
    private View view_ViewPager, view_GridView, view;
    List<HomeFragment_ListViewData> datas = new ArrayList<HomeFragment_ListViewData>();
    HomeFragment_ListViewData data;
    int start, start1, end, end1;
    private View view_Bar;
    private View topBar;
    private String url;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 收到消息后执行handler
            show();
        }
    };


    // 将数据填充到ListView中
    private void show() {
        if (datas.size() == 0) {
//            TextView message = (TextView) findViewById(R.id.message);
//            message.setText(R.string.message);
        } else {
            HomeFragment_ListView_Adapter adapter = new HomeFragment_ListView_Adapter(getActivity(), datas);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        initViewPager();
        initGridView();
        initBarView();
        initListView();
        getData();
        return view;
    }

    private void initBarView() {
        view_Bar = View.inflate(getActivity(), R.layout.home_bar, null);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Connection conn = Jsoup.connect(Apis.HOTNEWS);
            // 修改http包中的header,伪装成浏览器进行抓取
            conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
            Document doc = null;
            try {
                doc = conn.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取下一页的链接
//            Elements link = doc.select("li.paginate_button").select("li.next");
//            next_page_url = link.select("a").attr("href");
            // 获取tbody元素下的所有tr元素
            Elements elements = doc.select("ul.hotlist");
            for (int i = 0; i < elements.get(0).children().size(); i++) {
                Element element = elements.get(0).children().get(i);
                String title = element.getElementsByTag("a").get(0).childNodes().get(0).toString();
                String author = element.getElementsByTag("a").get(0).childNodes().get(1).toString();
                start = author.indexOf("\"by\">") + 5;
                end = author.indexOf("</span>");
                author = author.substring(start, end);
                String commentNums = element.getElementsByTag("a").get(0).childNodes().get(3).toString();
                start1 = commentNums.indexOf("\"num\">") + 6;
                end1 = commentNums.indexOf("</span>");
                commentNums = commentNums.substring(start1, end1);
                url = element.getElementsByTag("a").get(0).attributes().toString();
                url = url.substring(7, url.length() - 1);
                Map<String, Object> map = new HashMap<>();
                data = new HomeFragment_ListViewData();
                data.setTitle(title);
                data.setAuthor(author);
                data.setCommentNums(commentNums);
                data.setUrl(url);
                datas.add(data);
            }
            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };

    private void getData() {
        try {
            new Thread(runnable).start();  // 子线程
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewPager() {
        view_ViewPager = View.inflate(getActivity(), R.layout.home_viewpager, null);
        viewPager = (ViewPager) view_ViewPager.findViewById(R.id.viewPager);
        int[] list = new int[2];
        list[0] = R.drawable.viewpager1;
        list[1] = R.drawable.viewpager2;
        viewPager.setAdapter(new HomeFragment_ViewPagerAdapter(getActivity(), list));
        viewPager.setCurrentItem(0);//设置当前显示标签页为第一页
    }

    private void initGridView() {
        view_GridView = View.inflate(getActivity(), R.layout.home_gridview, null);
        gridView = (GridView) view_GridView.findViewById(R.id.gridView);
        //配置适配器
        gridView.setAdapter(new HomeFragment_GridAdapter(getActivity(), mNameList, icon));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ArticlesActivity.class);
                intent.putExtra("url", mUrlList[position]);
                intent.putExtra("name", mNameList[position]);
                startActivity(intent);
            }
        });
    }

    private void initListView() {

//        topBar = view.findViewById(R.id.top_bar);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.addHeaderView(view_ViewPager);
        listView.addHeaderView(view_GridView);
        listView.addHeaderView(view_Bar);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("123213", firstVisibleItem + "");
//                if (3 <= firstVisibleItem) {
//                    topBar.setVisibility(View.VISIBLE);
//                }else {
//                    topBar.setVisibility(View.GONE);
//                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 2) {
                    Log.e("sdasa", position + "");
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", datas.get(position - 3).getUrl());
                    getActivity().startActivity(intent);
                } else {
                    return;
                }

            }
        });
    }
}
