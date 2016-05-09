package org.xidian.ruisi.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xidian.ruisi.R;

/**
 * Created by ymh on 2016/5/9.
 */
public class PartFragment_ListAdapter extends BaseAdapter {
    private String[] data = new String[]{"西电生活", "学术交流", "休闲娱乐", "社团风采", "睿思BT", "站务管理"};
    private Activity activity;
    public static int pos = 0;
    private TextView textView, tv_left;
    private RelativeLayout relativ_layout;

    public PartFragment_ListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
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
        textView.setText(data[position]);
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
