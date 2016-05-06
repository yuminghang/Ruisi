package org.xidian.ruisi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.GridAdapter;
import org.xidian.ruisi.adapter.HomeFragment_ViewPagerAdapter;
import org.xidian.ruisi.adapter.Home_GridAdapter;

import java.util.ArrayList;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        initView(view);
        initViewPager();
        initGridView();
        initListView();
        return view;
    }

    private void initViewPager() {
        int[] list = new int[3];
        list[0] = R.drawable.viewpager1;
        list[1] = R.drawable.viewpager2;
        list[2] = R.drawable.viewpager3;
        viewPager.setAdapter(new HomeFragment_ViewPagerAdapter(getActivity(), list));
        viewPager.setCurrentItem(0);//设置当前显示标签页为第一页
    }

    private void initGridView() {
        //配置适配器
        gridView.setAdapter(new Home_GridAdapter(getActivity(), mNameList, icon));
    }

    private void initListView() {

    }

    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        gridView = (GridView) view.findViewById(R.id.gridView);
        listView = (ListView) view.findViewById(R.id.listView);
    }

}
