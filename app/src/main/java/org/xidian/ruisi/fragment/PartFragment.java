package org.xidian.ruisi.fragment;

import android.os.Bundle;
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

import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.GridAdapter;
import org.xidian.ruisi.adapter.PartFragment_ListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
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
    private String[] mYuLeList = {"西电睿思灌水专区", "摄影天地", "西电后街", "音乐纵横线", "游间客栈", "西电睿思灌水专区", "原创精品区", "我爱运动", "绝对漫域", "大话体坛"};
    private String[] mSheTuanList = {"腾讯创新俱乐部", "华为创新俱乐部"};
    private String[] mBTList = {"电影", "剧集", "音乐", "动漫", "游戏", "综艺", "体育", "软件", "学习", "纪录片", "其他", "西电", "求种专区", "新手试种专区", "资源回收站"};
    private String[] mGuanLiList = {"西电睿思举报专区", "西电睿思帮助专区", "睿思发展建议专区", "睿思邀请码申请", "睿思公告活动专区"};
    private String[][] pos = new String[][]{mLifeList, mXueshuList, mYuLeList, mSheTuanList, mBTList, mGuanLiList};
    private boolean isShowLike = false;
    private View view;
    private PartFragment_ListAdapter mListAdapter;
    private GridAdapter mGridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.part_fragment_layout, container, false);
        initGridView(view);
        initListView();
        return view;
    }

    private void initListView() {
        listView = (ListView) view.findViewById(R.id.listView);
        mListAdapter = new PartFragment_ListAdapter(getActivity());
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PartFragment_ListAdapter.pos = position;
                mListAdapter.notifyDataSetChanged();
                mGridAdapter.setData(pos[position]);
                Log.e("safdasd", pos[position] + "");
            }
        });
    }

    private void initGridView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridView);
        //新建List
        //配置适配器
        mGridAdapter = new GridAdapter(getActivity(), mNameList, icon);
        gridView.setAdapter(mGridAdapter);
        gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowLike) {
                    isShowLike = false;
                } else {
                    isShowLike = true;
                }
                mGridAdapter.setIsShowDelete(isShowLike);
                return true;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "外面！！点击了！！！" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
