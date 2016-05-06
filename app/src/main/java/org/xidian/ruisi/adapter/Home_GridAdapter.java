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

/**
 * Created by ymh on 2016/5/6.
 */
public class Home_GridAdapter extends BaseAdapter {
    private int[] mDrawableList;
    private String[] mNameList;
    private LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams params;

    public Home_GridAdapter(Context context, String[] nameList, int[] drawableList) {
        mNameList = nameList;
        mDrawableList = drawableList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
//        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER;
    }

    public int getCount() {
        return mNameList.length;
    }

    public Object getItem(int position) {
        return mNameList[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.home_item, null);
            // construct an item tag
            viewTag = new ItemViewTag((ImageView) convertView.findViewById(R.id.grid_icon), (TextView) convertView.findViewById(R.id.grid_name));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }

        // set name
        viewTag.mName.setText(mNameList[position]);

        // set icon
        viewTag.mIcon.setImageResource(mDrawableList[position]);
//        viewTag.mIcon.setLayoutParams(params);
        return convertView;
    }

    class ItemViewTag {
        protected ImageView mIcon;
        protected TextView mName;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name the name view of the item
         * @param icon the icon view of the item
         */
        public ItemViewTag(ImageView icon, TextView name) {
            this.mName = name;
            this.mIcon = icon;
        }
    }
}


