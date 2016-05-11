package org.xidian.ruisi.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xidian.ruisi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/5/9.
 */
public class PartFragment_ListAdapter extends BaseAdapter {
    private Activity activity;
    public static int pos = 0;
    private TextView textView, tv_left;
    private RelativeLayout relativ_layout;
    List<String> mList = new ArrayList<String>();

    public PartFragment_ListAdapter(Activity activity, List<String> mList) {
        this.activity = activity;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(activity, R.layout.part_fragment_listview_item, null);
        textView = (TextView) view.findViewById(R.id.textView);
        tv_left = (TextView) view.findViewById(R.id.tv_left);
        relativ_layout = (RelativeLayout) view.findViewById(R.id.relativ_layout);
        textView.setText(mList.get(position));
        if (pos == position) {
            textView.setTextColor(activity.getResources().getColor(R.color.orangered));
            tv_left.setVisibility(View.VISIBLE);
            relativ_layout.setBackgroundColor(activity.getResources().getColor(R.color.background));
        } else {
            textView.setTextColor(activity.getResources().getColor(R.color.black));
            tv_left.setVisibility(View.INVISIBLE);
            relativ_layout.setBackgroundColor(activity.getResources().getColor(R.color.listBack));
        }
        return view;
    }
}
