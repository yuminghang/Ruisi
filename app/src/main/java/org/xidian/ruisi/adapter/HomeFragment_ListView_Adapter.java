package org.xidian.ruisi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xidian.ruisi.R;
import org.xidian.ruisi.bean.HomeFragment_ListViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymh on 2016/5/6.
 */
public class HomeFragment_ListView_Adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    List<HomeFragment_ListViewData> datas = new ArrayList<HomeFragment_ListViewData>();

    public HomeFragment_ListView_Adapter(Context context, List<HomeFragment_ListViewData> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
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
        ItemViewTag viewTag;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.home_listview_item, null);
            // construct an item tag
            viewTag = new ItemViewTag((TextView) convertView.findViewById(R.id.title),
                    (TextView) convertView.findViewById(R.id.author), (TextView) convertView.findViewById(R.id.comment));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }
        viewTag.title.setText(datas.get(position).getTitle());
        viewTag.author.setText(datas.get(position).getAuthor());
        viewTag.comment.setText(datas.get(position).getCommentNums());
        return convertView;
    }

    class ItemViewTag {
        protected TextView title, author, comment;

        public ItemViewTag(TextView title, TextView author, TextView comment) {
            this.title = title;
            this.author = author;
            this.comment = comment;
        }
    }
}
