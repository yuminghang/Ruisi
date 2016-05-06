package org.xidian.ruisi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ymh on 2016/5/6.
 */
public class PartFragment extends Fragment {
    private GridView gridView;
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
    private boolean isShowLike = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part_fragment_layout, container, false);
        initGridView(view);
        return view;
    }

    private void initGridView(View view) {
        gridView = (GridView) view.findViewById(R.id.gridView);
        //新建List
        //配置适配器
        final GridAdapter mGridAdapter = new GridAdapter(getActivity(), mNameList, icon);
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
