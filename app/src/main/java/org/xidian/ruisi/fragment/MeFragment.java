package org.xidian.ruisi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import org.xidian.ruisi.MyApplication;
import org.xidian.ruisi.R;
import org.xidian.ruisi.activity.MyDatumActivity;
import org.xidian.ruisi.activity.LoginActivity;
import org.xidian.ruisi.activity.MyCollectActivity;
import org.xidian.ruisi.activity.MyMessageActivity;
import org.xidian.ruisi.activity.MyTopicActivity;
import org.xidian.ruisi.activity.SettingActivity;
import org.xidian.ruisi.view.CircleImageView;

/**
 * Created by ymh on 2016/5/6.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout shoucang, xiaoxi, zhuti, ziliao, shezhi, touxiang;
    private View view;
    private Boolean islogin;
    private CircleImageView MeFragment_mAvatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me_fragment_layout, container, false);
        initView();
        return view;
    }

    private void initView() {
        MeFragment_mAvatar = (CircleImageView) view.findViewById(R.id.MeFragment_mAvatar);
        if (MyApplication.avatarUrl.length() > 0) {
            Glide.with(getActivity()).load(MyApplication.avatarUrl).into(MeFragment_mAvatar);
        }
        touxiang = (LinearLayout) view.findViewById(R.id.touxiang);
        shoucang = (LinearLayout) view.findViewById(R.id.shoucang);
        xiaoxi = (LinearLayout) view.findViewById(R.id.xiaoxi);
        zhuti = (LinearLayout) view.findViewById(R.id.zhuti);
        ziliao = (LinearLayout) view.findViewById(R.id.ziliao);
        shezhi = (LinearLayout) view.findViewById(R.id.shezhi);
        touxiang.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        xiaoxi.setOnClickListener(this);
        zhuti.setOnClickListener(this);
        ziliao.setOnClickListener(this);
        shezhi.setOnClickListener(this);
    }

    public void loadAvatar(String url) {
        Glide.with(getActivity()).load(url).into(MeFragment_mAvatar);
    }

    @Override
    public void onClick(View v) {
        islogin = MyApplication.isLogin();
        switch (v.getId()) {
            case R.id.shoucang:
                if (!islogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyCollectActivity.class));
                }
                break;
            case R.id.xiaoxi:
                if (!islogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyMessageActivity.class));
                }
                break;
            case R.id.zhuti:
                if (!islogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyTopicActivity.class));
                }
                break;
            case R.id.ziliao:
                if (!islogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MyDatumActivity.class));
                }
                break;
            case R.id.shezhi:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.touxiang:
                if (!islogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
//                    startActivity(new Intent(getActivity(), TvActivity.class));
                }
                break;
        }
    }
}
