package org.xidian.ruisi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xidian.ruisi.R;

/**
 * Created by ymh on 2016/5/6.
 */
public class FaxianFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.faxian_fragment_layout, container, false);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("ewdsadq", "3显示啦");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("ewdsadq", "3销毁啦");
    }
}
