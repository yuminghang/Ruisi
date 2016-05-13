package org.xidian.ruisi.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.xidian.ruisi.R;
import org.xidian.ruisi.bean.MyTopicData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/5/13.
 */
public class TopicListAdapter extends BaseAdapter {
    private Activity activity;
    private List<MyTopicData> datas = new ArrayList<MyTopicData>();

    public TopicListAdapter(Activity activity, List<MyTopicData> datas) {
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
            convertView = View.inflate(activity, R.layout.topic_item, null);
            myHolder = new MyHolder();
            myHolder.titleView = (TextView) convertView.findViewById(R.id.title);
            myHolder.commentView = (TextView) convertView.findViewById(R.id.comment);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }
        if(datas.size()>0){
            myHolder.titleView.setText(datas.get(position).title);
            myHolder.commentView.setText(datas.get(position).commentNum);
        }

        return convertView;
    }

    class MyHolder {
        public TextView titleView;
        public TextView commentView;
    }
}

