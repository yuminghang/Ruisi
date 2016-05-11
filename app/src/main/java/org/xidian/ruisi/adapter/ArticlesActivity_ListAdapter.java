package org.xidian.ruisi.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xidian.ruisi.R;
import org.xidian.ruisi.bean.ArticleListData;

import java.util.List;

/**
 * Created by ymh on 2016/5/11.
 */
public class ArticlesActivity_ListAdapter extends BaseAdapter {
    private Activity activity;
    private List<ArticleListData> list;

    public ArticlesActivity_ListAdapter(Activity activity, List<ArticleListData> list) {
        this.activity = activity;
        this.list = list;
    }

    public void notifyAll(List<ArticleListData> news) {
        if(list.size()>0){
            this.list.addAll(list.size() - 1, news);
        }else{
            this.list.addAll( news);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list.size() > 0) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.article_list_item, null);
//            convertView = mInflater.inflate(R.layout.item, null);
            // construct an item tag
            myViewHolder = new MyViewHolder((TextView) convertView.findViewById(R.id.title),
                    (TextView) convertView.findViewById(R.id.id),
                    (TextView) convertView.findViewById(R.id.commentNum), (ImageView) convertView.findViewById(R.id.jingpin));
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.title.setText(list.get(position).title);
        myViewHolder.id.setText(list.get(position).id);
        myViewHolder.commentNum.setText(list.get(position).commentNum);
        if (list.get(position).des == 1) {
            myViewHolder.jingpin.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.jingpin.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class MyViewHolder {
        protected TextView title;
        protected TextView id;
        protected TextView commentNum;
        protected ImageView jingpin;

        public MyViewHolder(TextView title, TextView id, TextView commentNum, ImageView jingpin) {
            this.title = title;
            this.id = id;
            this.commentNum = commentNum;
            this.jingpin = jingpin;
        }
    }
}
