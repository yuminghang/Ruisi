package org.xidian.ruisi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/5/6.
 */
public class HomeFragment_ViewPagerAdapter extends PagerAdapter {
    private List<ImageView> viewList = new ArrayList<>();
    private Context context;

    public HomeFragment_ViewPagerAdapter(Context context, int[] list) {
        this.context = context;
        for (int i = 0; i < list.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(list[i]);
            viewList.add(imageView);
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        // TODO Auto-generated method stub
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(viewList.get(position));
        return viewList.get(position);
    }
}
