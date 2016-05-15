package org.xidian.ruisi.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;
import org.xidian.ruisi.MyApplication;
import org.xidian.ruisi.R;
import org.xidian.ruisi.adapter.MyFragmentPagerAdapter;
import org.xidian.ruisi.fragment.FaxianFragment;
import org.xidian.ruisi.fragment.HomeFragment;
import org.xidian.ruisi.fragment.MeFragment;
import org.xidian.ruisi.fragment.PartFragment;
import org.xidian.ruisi.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private long exitTime;
    private RelativeLayout home_RL, part_RL, mine_RL;
    private ImageView btn_home, btn_part,  btn_mine, avatar;
    private TextView tv_home, tv_part,  tv_mine;
    private NoScrollViewPager mViewPager;
    private ArrayList<Fragment> fragmentList;
    List<ImageView> ivlist = new ArrayList<>();
    List<TextView> tvlist = new ArrayList<>();
    int[] pic_press = new int[]{R.drawable.home_tabbar_press, R.drawable.newvideo_tabbar_pressed,
            R.drawable.topic_tabbar_press, R.drawable.mine_tabbar_press};
    int[] pic = new int[]{R.drawable.home_tabbar, R.drawable.newvideo_tabbar,
            R.drawable.topic_tabbar, R.drawable.mine_tabbar};
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    public static MeFragment meFragment;
    private FaxianFragment faxianFragment;
    private PartFragment partFragment;
    private HomeFragment homeFragment;
    private MyAvatarBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.addActivity(this);
        registerMyBroadcast();
        initViews();
        initViewpager();
        setListener();
    }

    private void registerMyBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("BBS.RS.XIDIAN.ME");
        receiver = new MyAvatarBroadcastReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private void setListener() {
        home_RL.setOnClickListener(this);
        part_RL.setOnClickListener(this);
        mine_RL.setOnClickListener(this);
        avatar.setOnClickListener(this);
    }


    private void initViewpager() {
        fragmentList = new ArrayList<Fragment>();
        homeFragment = new HomeFragment();
        partFragment = new PartFragment();
        meFragment = new MeFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(partFragment);
        fragmentList.add(meFragment);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mViewPager.setCurrentItem(0);//设置当前显示标签页为第一页
        mViewPager.setOffscreenPageLimit(3);

    }

    private void initViews() {
        avatar = (ImageView) findViewById(R.id.avatar);
        mViewPager = (NoScrollViewPager) findViewById(R.id.noScrollViewPager);
        home_RL = (RelativeLayout) findViewById(R.id.home_RL);
        part_RL = (RelativeLayout) findViewById(R.id.part_RL);
        mine_RL = (RelativeLayout) findViewById(R.id.mine_RL);
        btn_home = (ImageView) findViewById(R.id.btn_home);
        btn_part = (ImageView) findViewById(R.id.btn_part);
        btn_mine = (ImageView) findViewById(R.id.btn_mine);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_part = (TextView) findViewById(R.id.tv_part);
        tv_mine = (TextView) findViewById(R.id.tv_mine);
        ivlist.add(btn_home);
        ivlist.add(btn_part);
        ivlist.add(btn_mine);
        tvlist.add(tv_home);
        tvlist.add(tv_part);
        tvlist.add(tv_mine);
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


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_RL:
                mViewPager.setCurrentItem(0);
                setButton(0);
                break;
            case R.id.part_RL:
                mViewPager.setCurrentItem(1);
                setButton(1);
                break;
            case R.id.mine_RL:
                mViewPager.setCurrentItem(2);
                setButton(2);
                break;
            case R.id.avatar:
                if (MyApplication.isLogin()) {
                    startActivity(new Intent(this, MyDatumActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                setButton(2);
                break;
        }
    }

    private void setButton(int pos) {
        for (int i = 0; i < tvlist.size(); i++) {
            if (pos == i) {
                tvlist.get(i).setTextColor(getResources().getColor(R.color.mychoice));
                ivlist.get(i).setImageResource(pic_press[i]);
            } else {
                tvlist.get(i).setTextColor(getResources().getColor(R.color.mychoice_dark));
                ivlist.get(i).setImageResource(pic[i]);
            }
        }
    }

    class MyAvatarBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MyApplication.avatarUrl.length() > 0) {
                Glide.with(MainActivity.this).load(MyApplication.avatarUrl).into(avatar);
                meFragment.loadAvatar(MyApplication.avatarUrl);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

