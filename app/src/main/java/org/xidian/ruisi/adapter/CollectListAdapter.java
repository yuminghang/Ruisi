package org.xidian.ruisi.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xidian.ruisi.R;
import org.xidian.ruisi.bean.MyCollectionData;
import org.xidian.ruisi.bean.MyMessageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/5/12.
 */
public class CollectListAdapter extends BaseAdapter {
    private List<MyCollectionData> datas = new ArrayList<MyCollectionData>();
    private Activity activity;

    public CollectListAdapter(Activity activity, ArrayList<MyCollectionData> datas) {
        this.activity = activity;
        this.datas = datas;
    }


    @Override
    public int getCount() {
        if (datas.size() > 0) {
            return datas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (datas.size() > 0) {
            return datas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.collection_item, null);
            myHolder = new MyHolder();
            myHolder.title = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }
        if (datas.size() > 0){
            myHolder.title.setText(datas.get(position).title);
        }
        return convertView;

    }

    class MyHolder {
        public TextView title;
    }
}
