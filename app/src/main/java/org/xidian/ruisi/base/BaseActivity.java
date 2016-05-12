package org.xidian.ruisi.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import org.xidian.ruisi.MyApplication;
import org.xidian.ruisi.R;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
