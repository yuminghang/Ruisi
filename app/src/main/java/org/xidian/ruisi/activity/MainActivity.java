package org.xidian.ruisi.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.xidian.ruisi.R;
import org.xidian.ruisi.fragment.FaxianFragment;
import org.xidian.ruisi.fragment.HomeFragment;
import org.xidian.ruisi.fragment.MeFragment;
import org.xidian.ruisi.fragment.PartFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private long exitTime;
    private RelativeLayout home_RL, part_RL, faxian_RL, mine_RL;
    private HomeFragment homeFragment;
    private PartFragment partFragment;
    private FaxianFragment faxianFragment;
    private MeFragment meFragment;
    private FrameLayout fragment_container;
    List<RelativeLayout> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListener();
    }

    private void setListener() {
        home_RL.setOnClickListener(this);
        part_RL.setOnClickListener(this);
        faxian_RL.setOnClickListener(this);
        mine_RL.setOnClickListener(this);
        list.add(home_RL);
        list.add(part_RL);
        list.add(faxian_RL);
        list.add(mine_RL);
    }


    private void initViews() {
        fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
        home_RL = (RelativeLayout) findViewById(R.id.home_RL);
        part_RL = (RelativeLayout) findViewById(R.id.part_RL);
        faxian_RL = (RelativeLayout) findViewById(R.id.faxian_RL);
        mine_RL = (RelativeLayout) findViewById(R.id.mine_RL);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_RL:
                select(0);
                break;
            case R.id.part_RL:
                select(1);
                break;
            case R.id.faxian_RL:
                select(2);
                break;
            case R.id.mine_RL:
                select(3);
                break;
        }
    }

    private void select(int i) {
        FragmentManager fm = getSupportFragmentManager();  //获得Fragment管理器
        FragmentTransaction ft = fm.beginTransaction(); //开启一个事务
        hidtFragment(ft);   //先隐藏 Fragment
        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ft.replace(R.id.fragment_container, homeFragment);
                } else {
                    ft.show(homeFragment);
                }
                break;
            case 1:
                if (partFragment == null) {
                    partFragment = new PartFragment();
                    ft.add(R.id.fragment_container, partFragment);
                } else {
                    ft.show(partFragment);
                }
                break;
            case 2:
                if (faxianFragment == null) {
                    faxianFragment = new FaxianFragment();
                    ft.add(R.id.fragment_container, faxianFragment);
                } else {
                    ft.show(faxianFragment);
                }
                break;
            case 3:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    ft.add(R.id.fragment_container, meFragment);
                } else {
                    ft.show(meFragment);
                }
                break;
        }
        ft.commit();   //提交事务
    }

    //隐藏所有Fragment
    private void hidtFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (partFragment != null) {
            fragmentTransaction.hide(partFragment);
        }
        if (faxianFragment != null) {
            fragmentTransaction.hide(faxianFragment);
        }
        if (meFragment != null) {
            fragmentTransaction.hide(meFragment);
        }
    }

    private void setButton() {
        for (RelativeLayout relativeLayout : list) {
//            relativeLayout.setBackground();
        }
    }
}

