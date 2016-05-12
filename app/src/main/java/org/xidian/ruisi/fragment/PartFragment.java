package org.xidian.ruisi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xidian.ruisi.MyApplication;
import org.xidian.ruisi.R;
import org.xidian.ruisi.activity.ArticlesActivity;
import org.xidian.ruisi.adapter.GridAdapter;
import org.xidian.ruisi.adapter.PartFragment_ListAdapter;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.bean.GridViewBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by ymh on 2016/5/6.
 */
public class PartFragment extends Fragment {
    private GridView gridView;
    private ListView listView;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = {R.drawable.icon1, R.drawable.icon2, R.drawable.icon3, R.drawable.icon4, R.drawable.icon5,
            R.drawable.icon6, R.drawable.icon7, R.drawable.icon8, R.drawable.icon9, R.drawable.icon10,
            R.drawable.icon11, R.drawable.icon12, R.drawable.icon13, R.drawable.icon4, R.drawable.icon5, R.drawable.icon6,
            R.drawable.icon17, R.drawable.icon6, R.drawable.icon7, R.drawable.icon8, R.drawable.icon9};

    private String[] mNameList = {"心灵花园", "我是女生", "缘聚睿思", "西电问答", "我要毕业啦", "学习交流", "嵌入式技术",
            "竞赛交流", "摄影天地", "我爱运动", "腾讯创新俱乐部", "西电睿思发展建议区", "西电睿思公告活动专区", "文章天地"
            , "校园交易", "版聊专区", "失物招领", "校园活动", "就业招聘", "技术博客", "软件技术交流",

    };
    private String[] mLifeList = {"心灵花园", "我是女生", "缘聚睿思", "西电问答", "我要毕业啦", "就业招聘", "校园交易", "西电版聊专区", "失物招领专区", "校园活动", "文章天地"};
    private String[] mXueshuList = {"学习交流", "嵌入式技术", "竞赛交流", "技术博客", "软件技术交流", "考研交流", "Bbroad & English"};
    private String[] mYuLeList = {"西电睿思灌水专区", "摄影天地", "西电后街", "音乐纵横线", "游间客栈", "原创精品区", "我爱运动", "绝对漫域", "大话体坛"};
    private String[] mSheTuanList = {"腾讯创新俱乐部", "华为创新俱乐部"};
    private String[] mBTList = {"电影", "剧集", "音乐", "动漫", "游戏", "综艺", "体育", "软件", "学习", "纪录片", "其他", "西电", "求种专区", "新手试种专区", "资源回收站"};
    private String[] mGuanLiList = {"西电睿思举报专区", "西电睿思帮助专区", "睿思发展建议专区", "睿思邀请码申请", "睿思公告活动专区"};
    private String[] mMineList = {"学习交流", "嵌入式技术", "竞赛交流", "技术博客", "软件技术交流", "考研交流", "Bbroad & English"};

    private String[][] pos = new String[][]{mLifeList, mXueshuList, mYuLeList, mSheTuanList, mBTList, mGuanLiList, mMineList};
    private boolean isShowLike = false;
    private View view;
    private PartFragment_ListAdapter mListAdapter;
    private GridAdapter mGridAdapter;
    private Document doc;
    private Elements elements;
    private String html;
    List<ArrayList<GridViewBean>> news = new ArrayList<ArrayList<GridViewBean>>();
    List<String> mList = new ArrayList<String>();
    private int position = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 收到消息后执行handler
            mGridAdapter.notifyAll(news);
            mListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.part_fragment_layout, container, false);
        initGridView(view);
        initListView();
        Log.e("ewdsadq", "创建啦");
        parseData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("ewdsadq", "显示啦");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("ewdsadq", "销毁啦");
    }

    private void initListView() {
        listView = (ListView) view.findViewById(R.id.listView);
        mListAdapter = new PartFragment_ListAdapter(getActivity(), mList);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PartFragment.this.position = position;
                PartFragment_ListAdapter.pos = position;
                mListAdapter.notifyDataSetChanged();
                mGridAdapter.setData(position);
                Log.e("safdasd", pos[position] + "");
            }
        });
        listView.setSelection(0);
    }

    private void initGridView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridView);
        //新建List
        //配置适配器
        mGridAdapter = new GridAdapter(getActivity(), icon);
        gridView.setAdapter(mGridAdapter);
        gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if (isShowLike) {
//                    isShowLike = false;
//                } else {
//                    isShowLike = true;
//                }
//                return true;
//            }
//        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "外面！！点击了！！！" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArticlesActivity.class);
                intent.putExtra("url", news.get(PartFragment.this.position).get(position).url);
                intent.putExtra("name", news.get(PartFragment.this.position).get(position).name);
                startActivity(intent);
            }
        });
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
//            Connection conn = Jsoup.connect(Apis.PARTPAGER);
            // 修改http包中的header,伪装成浏览器进行抓取
            doc = null;
            try {
                doc = Jsoup.parse(html);
            } catch (Exception e) {
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
            elements = doc.select("div#wp").select("div.bm.bmw.fl");
            for (int i = 0; i < elements.size(); i++) {
                String partName = elements.get(i).select("h2").text();
                if (partName.length() > 4) {
                    partName = partName.substring(0, 4);
                }
                mList.add(i, partName);
                Elements kidsData = elements.get(i).select("div[id^=sub_forum_]").select("li");
                ArrayList<GridViewBean> list = new ArrayList<GridViewBean>();
                for (int j = 0; j < kidsData.size(); j++) {
                    GridViewBean bean = new GridViewBean();
                    String url = kidsData.get(j).select("a").attr("href");
                    String num = kidsData.get(j).select("a").select("span").text();
                    kidsData.get(j).select("a").select("span").remove();
                    String name = kidsData.get(j).select("a").text();
                    bean.name = name;
                    bean.num = num;
                    bean.url = url;
                    list.add(bean);
                }
                news.add(list);
            }
            handler.sendEmptyMessage(0);
        }
    };

    private void getData() {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request;
        if (MyApplication.myCookie!=null) {
            request = new com.squareup.okhttp.Request.Builder()
                    .addHeader("Cookie", MyApplication.myCookie)
                    .url(Apis.PARTPAGER)
                    .build();
        } else {
            request = new com.squareup.okhttp.Request.Builder()
//                    .addHeader("Cookie", MyApplication.myCookie)
                    .url(Apis.PARTPAGER)
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
