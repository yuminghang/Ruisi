package org.xidian.ruisi.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.MyFragmentPagerAdapter;
import org.xidian.ruisi.fragment.FaxianFragment;
import org.xidian.ruisi.fragment.HomeFragment;
import org.xidian.ruisi.fragment.MeFragment;
import org.xidian.ruisi.fragment.PartFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long exitTime;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    static ArrayList<String> titleContainer = new ArrayList<String>();
    private ArrayList<Fragment> fragmentList;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewpager();
    }


    private void initViews() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initViewpager() {
        initTitle();
        fragmentList = new ArrayList<Fragment>();
        Fragment homeFragment = new HomeFragment();
        Fragment partFragment = new PartFragment();
        Fragment faxianFragment = new FaxianFragment();
        Fragment meFragment = new MeFragment();

        fragmentList.add(homeFragment);
        fragmentList.add(partFragment);
        fragmentList.add(faxianFragment);
        fragmentList.add(meFragment);

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleContainer);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);//设置当前显示标签页为第一页
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.requestDisallowInterceptTouchEvent(true);
        tabLayout.setTabsFromPagerAdapter(myFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(6);
    }

    private static void initTitle() {
        //页签项
        titleContainer.add("首页");
        titleContainer.add("板块");
        titleContainer.add("发现");
        titleContainer.add("我的");
//        titleContainer.add("搞笑");
//        titleContainer.add("焦点");
//        titleContainer.add("影视");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
