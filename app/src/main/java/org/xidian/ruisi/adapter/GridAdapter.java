package org.xidian.ruisi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xidian.ruisi.R;
import org.xidian.ruisi.bean.GridViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ymh on 2016/5/6.
 */
public class GridAdapter extends BaseAdapter {
    private int[] mDrawableList;
    private Context mContext;
    List<ArrayList<GridViewBean>> news = new ArrayList<ArrayList<GridViewBean>>();
    private int pos = 0;

    public GridAdapter(Context context, int[] drawableList) {
        mDrawableList = drawableList;
        mContext = context;
    }

    public void setData(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }

    public void notifyAll(List<ArrayList<GridViewBean>> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (news.size() > 0) {
            return news.get(pos).size();
        }
        return 0;
    }

    public Object getItem(int position) {
        if (news.size() > 0) {
            return news.get(pos).get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item, null);
//            convertView = mInflater.inflate(R.layout.item, null);
            // construct an item tag
            viewTag = new ItemViewTag((ImageView) convertView.findViewById(R.id.grid_icon),
                    (TextView) convertView.findViewById(R.id.like), (TextView) convertView.findViewById(R.id.grid_name));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }
        // set name
        viewTag.mName.setText(news.get(pos).get(position).name);
        if (news.get(pos).get(position).num.length() > 0) {
            viewTag.like.setText(news.get(pos).get(position).num);
            viewTag.like.setVisibility(View.VISIBLE);
        } else {
            viewTag.like.setVisibility(View.INVISIBLE);
        }
//        viewTag.like.setVisibility(isShowLike ? View.VISIBLE : View.GONE);
//        viewTag.like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "点击了！！！" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
        // set icon
        viewTag.mIcon.setImageResource(mDrawableList[position]);
//        viewTag.mIcon.setLayoutParams(params);
        return convertView;
    }

    class ItemViewTag {
        protected ImageView mIcon;
        protected TextView like;
        protected TextView mName;

        public ItemViewTag(ImageView icon, TextView like, TextView name) {
            this.mName = name;
            this.mIcon = icon;
            this.like = like;
        }
    }
}

