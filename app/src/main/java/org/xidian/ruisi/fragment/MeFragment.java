package org.xidian.ruisi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.xidian.ruisi.R;

/**
 * Created by ymh on 2016/5/6.
 */
public class MeFragment extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment_layout, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
//        webView.loadUrl();
        return view;
    }
}
