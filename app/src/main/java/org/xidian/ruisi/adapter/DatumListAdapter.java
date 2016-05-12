package org.xidian.ruisi.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xidian.ruisi.R;

/**
 * Created by ymh on 2016/5/12.
 */
public class DatumListAdapter extends BaseAdapter {
    private View view;

    private Activity activity;
    private String[] datas = new String[]{};
    private String[] leftdatas = new String[]{"积分", "金币", "上传量", "下载量", "发种数", "筹码", "保种度", "人品"};
    private int upload;
    private int download;

    public DatumListAdapter(Activity activity, String[] datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        if (datas.length > 0) {
            return datas.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (datas.length > 0) {
            return datas[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = View.inflate(activity, R.layout.datumlist_item, null);
        TextView tv_left = (TextView) view.findViewById(R.id.tv_left);
        TextView tv_right = (TextView) view.findViewById(R.id.tv_right);
//        if (position == 2 && datas[position] != null) {
//            upload = Integer.parseInt(datas[position]);
//            upload /= 1024;
//            upload /= 1024;
//            upload /= 1024;
//            tv_right.setText(String.valueOf(upload));
//        } else if (position == 3 && datas[position] != null) {
//            download = Integer.parseInt(datas[position] + " GB");
//            download /= 1024;
//            download /= 1024;
//            download /= 1024;
//            tv_right.setText(String.valueOf(download) + " GB");
//        }
        tv_left.setText(leftdatas[position]);
        tv_right.setText(datas[position]);
        return view;
    }

}
