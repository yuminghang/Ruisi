package org.xidian.ruisi.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xidian.ruisi.R;
import org.xidian.ruisi.bean.MyMessageData;
import org.xidian.ruisi.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/5/12.
 */
public class MessageListAdapter extends BaseAdapter {
    private List<MyMessageData> datas = new ArrayList<MyMessageData>();
    private Activity activity;

    public MessageListAdapter(Activity activity, List<MyMessageData> datas) {
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
            convertView = View.inflate(activity, R.layout.message_item, null);
            myHolder = new MyHolder();
            myHolder.avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
            myHolder.id = (TextView) convertView.findViewById(R.id.id);
            myHolder.time = (TextView) convertView.findViewById(R.id.time);
            myHolder.send = (TextView) convertView.findViewById(R.id.send);
            myHolder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }
        if (datas.size() > 0) {
            myHolder.id.setText(datas.get(position).talker);
            myHolder.time.setText(datas.get(position).time);
            myHolder.content.setText(datas.get(position).message);
            Glide.with(activity).load(datas.get(position).avatar).into(myHolder.avatar);
        }
        return convertView;

    }

    class MyHolder {
        public CircleImageView avatar;
        public TextView id;
        public TextView time;
        public TextView send;
        public TextView content;
    }
}
