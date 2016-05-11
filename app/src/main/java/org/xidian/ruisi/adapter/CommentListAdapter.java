package org.xidian.ruisi.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xidian.ruisi.R;
import org.xidian.ruisi.activity.WebViewActivity;
import org.xidian.ruisi.bean.NewsDetailData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/5/10.
 */
public class CommentListAdapter extends BaseAdapter {
    private List<NewsDetailData> datas = new ArrayList<NewsDetailData>();
    private Activity activity;

    public CommentListAdapter(Activity activity, List<NewsDetailData> datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if (position == 0) {
            convertView = View.inflate(activity, R.layout.webview_item, null);
            ImageView avatarView = (ImageView) convertView.findViewById(R.id.avatar);
            TextView authorView = (TextView) convertView.findViewById(R.id.id);
            TextView timeView = (TextView) convertView.findViewById(R.id.time);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            authorView.setText(datas.get(position).id);
            timeView.setText(datas.get(position).time);
            title.setText(datas.get(position).title);
            Glide.with(activity).load(datas.get(position).avatar).into(avatarView);

        } else {
            if (convertView == null) {
                convertView = View.inflate(activity, R.layout.comment_item, null);
                myHolder = new MyHolder();
                myHolder.avatarView = (ImageView) convertView.findViewById(R.id.avatar);
                myHolder.authorView = (TextView) convertView.findViewById(R.id.id);
                myHolder.timeView = (TextView) convertView.findViewById(R.id.time);
                myHolder.positionView = (TextView) convertView.findViewById(R.id.position);
                convertView.setTag(myHolder);
            } else {
                myHolder = (MyHolder) convertView.getTag();
            }
            myHolder.authorView.setText(datas.get(position).id);
            myHolder.timeView.setText(datas.get(position).time);
            myHolder.positionView.setText(datas.get(position).position);
//        myHolder.webView.getSettings().setJavaScriptEnabled(false);
//        myHolder.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        myHolder.webView.loadDataWithBaseURL(null, datas.get(position).content, "text/html", "utf-8", null);
            Glide.with(activity).load(datas.get(position).avatar).into(myHolder.avatarView);
        }
        return convertView;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return 0;
//        } else {
//            return 1;
//        }
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }

    class MyHolder {
        public ImageView avatarView;
        public TextView authorView;
        public TextView timeView;
        public TextView positionView;
        public WebView webView;
    }
}
