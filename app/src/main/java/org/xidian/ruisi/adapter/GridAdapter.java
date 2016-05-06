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

/**
 * Created by ymh on 2016/5/6.
 */
public class GridAdapter extends BaseAdapter {
    private int[] mDrawableList;
    private String[] mNameList;
    private LayoutInflater mInflater;
    private Context mContext;
    private boolean isShowLike;

    public GridAdapter(Context context, String[] nameList, int[] drawableList) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);
            // construct an item tag
            viewTag = new ItemViewTag((ImageView) convertView.findViewById(R.id.grid_icon),
                    (ImageView) convertView.findViewById(R.id.like), (TextView) convertView.findViewById(R.id.grid_name));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }

        // set name
        viewTag.mName.setText(mNameList[position]);
        viewTag.like.setVisibility(isShowLike ? View.VISIBLE : View.GONE);
        viewTag.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了！！！" + position, Toast.LENGTH_SHORT).show();
            }
        });
        // set icon
        viewTag.mIcon.setImageResource(mDrawableList[position]);
//        viewTag.mIcon.setLayoutParams(params);
        return convertView;
    }

    public void setIsShowDelete(boolean isShowLike) {
        this.isShowLike = isShowLike;
        notifyDataSetChanged();
    }

    class ItemViewTag {
        protected ImageView mIcon;
        protected ImageView like;
        protected TextView mName;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name the name view of the item
         * @param icon the icon view of the item
         */
        public ItemViewTag(ImageView icon, ImageView like, TextView name) {
            this.mName = name;
            this.mIcon = icon;
            this.like = like;
        }
    }
}

