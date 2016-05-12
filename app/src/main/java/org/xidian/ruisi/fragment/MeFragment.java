package org.xidian.ruisi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import org.xidian.ruisi.MyApplication;
import org.xidian.ruisi.R;
import org.xidian.ruisi.activity.DatumActivity;
import org.xidian.ruisi.activity.LoginActivity;
import org.xidian.ruisi.activity.MyCollectActivity;
import org.xidian.ruisi.activity.MyMessageActivity;
import org.xidian.ruisi.activity.SettingActivity;

/**
 * Created by ymh on 2016/5/6.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout shoucang, xiaoxi, zhuti, ziliao, shezhi, touxiang;
    private View view;
    private Boolean islogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me_fragment_layout, container, false);
        initView();
        return view;
    }

    private void initView() {
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
//                    startActivity(new Intent(getActivity(), TvActivity.class));
                }
                break;
            case R.id.ziliao:
                if (!islogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), DatumActivity.class));
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
