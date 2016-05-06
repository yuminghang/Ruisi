package org.xidian.ruisi.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<ImageView> viewList;
    private View view_ViewPager, view_GridView, view;
    List<HomeFragment_ListViewData> datas = new ArrayList<HomeFragment_ListViewData>();
    HomeFragment_ListViewData data;
    int start, start1, end, end1;
    private View view_Bar;

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
                Map<String, Object> map = new HashMap<>();
                data = new HomeFragment_ListViewData();
                data.setTitle(title);
                data.setAuthor(author);
                data.setCommentNums(commentNums);
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
        int[] list = new int[3];
        list[0] = R.drawable.viewpager1;
        list[1] = R.drawable.viewpager2;
        list[2] = R.drawable.viewpager3;
        viewPager.setAdapter(new HomeFragment_ViewPagerAdapter(getActivity(), list));
        viewPager.setCurrentItem(0);//设置当前显示标签页为第一页
    }

    private void initGridView() {
        view_GridView = View.inflate(getActivity(), R.layout.home_gridview, null);
        gridView = (GridView) view_GridView.findViewById(R.id.gridView);
        //配置适配器
        gridView.setAdapter(new HomeFragment_GridAdapter(getActivity(), mNameList, icon));
    }

    private void initListView() {
        listView = (ListView) view.findViewById(R.id.listView);

        final String[] temp = {"789", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "789", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "789", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377", "102", "377", "456", "789", "102", "377", "456", "789", "102", "377"};
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
                TextView textView = new TextView(getActivity());
                textView.setText(temp[position]);
                return textView;
            }
        });
        listView.addHeaderView(view_ViewPager);
        listView.addHeaderView(view_GridView);
        listView.addHeaderView(view_Bar);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
}
