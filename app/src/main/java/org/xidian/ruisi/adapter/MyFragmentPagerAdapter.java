package org.xidian.ruisi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ymh on 2016/5/6.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;
    ArrayList<String> titlelist;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titlelist) {
        super(fm);
        this.list = list;
        this.titlelist = titlelist;
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position);
    }
}
