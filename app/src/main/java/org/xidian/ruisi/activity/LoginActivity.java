package org.xidian.ruisi.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.xidian.ruisi.R;
import org.xidian.ruisi.api.Apis;

import java.io.IOException;


public class LoginActivity extends Activity {

    private static final int POSTED = 1;
    private static final int NETWORK_EORR = 2;
    private EditText mNameEditText;
    private EditText mPassEditText;
    private Button mLoginButton;
    private ProgressBar mProgressProgressBar;
    //    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String json;
    private String result;
    private ImageView exit;
    private String name;
    private String password;
    private String cookie;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case POSTED:
                    if (result.contains("欢迎您回来")) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Log.e("asdsadwqa", result);
                        Log.e("asdsadwqa", cookie);
                        SharedPreferences mCookie = getSharedPreferences("cookie", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = mCookie.edit();
                        edit.clear();
                        edit.putString("my_cookie", "Q8qA_2132_saltkey=uhe1qPEu; Q8qA_2132_lastvisit=1462970604; Q8qA_2132_ulastactivity=9e4aGf7F4IJPpt%2FNju5yuebQxD80vtH7hkl%2Fv7WkmozioeWo51LJ; Q8qA_2132_auth=54baUIcHmz%2BU30%2Bvhrnf5FlkgFprkqR%2BtxXKwxurQiAxDA1kmP%2BU5IAKKcZdqMcg4fx9U872yDWiM3FDQxainiwMoZA; Q8qA_2132_lastcheckfeed=285665%7C1462974218; Q8qA_2132_lip=202.117.119.4%2C1462974567; Q8qA_2132_visitedfid=157; Q8qA_2132_st_p=285665%7C1462975418%7C5f533edede64ea7d7113975953a0b7e7; Q8qA_2132_viewid=tid_856119; Q8qA_2132_lastact=1462975433%09forum.php%09guide; Q8qA_2132_sid=z8sccj");
                        edit.commit();
                    } else if (result.contains("密码错误次数过多")) {
                        Toast.makeText(LoginActivity.this, "密码错误次数过多，请15分钟后重试！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        Log.e("asdsadwqa", result);
                    }
                    break;
                case NETWORK_EORR:
                    Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    //    private String name;
//    private String password;
//
//    private void resetAccount() {
//        try {
//            SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
//            SharedPreferences.Editor edit = sharedPreferences.edit();
//            edit.putBoolean("isLogin", true);
//            edit.putString("name", name);
//            edit.putString("password", password);
//            edit.putInt("account", 0);
//            edit.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
//        MyApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_login);
        initView();
    }

    //
    private void initView() {
        mNameEditText = (EditText) findViewById(R.id.et_name);
        exit = (ImageView) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.onBackPressed();
            }
        });
        mPassEditText = (EditText) findViewById(R.id.et_pass);
        mLoginButton = (Button) findViewById(R.id.bt_login);
//        mProgressProgressBar = (ProgressBar) findViewById(R.id.login_progress);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        name = mNameEditText.getText().toString();
        password = mPassEditText.getText().toString();
        boolean cancel = false;
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            cancel = true;
        }
        if (!cancel) {
            new Thread() {
                @Override
                public void run() {
                    postJson();
                }
            }.start();
        }
    }
//

    private void postJson() {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
//        RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody formBody = new FormEncodingBuilder()
                .add("cookietime", "2592000")
                .add("username", name.trim())
                .add("password", password.trim())
                .build();
        //创建一个请求对象
//        Request request = new Request.Builder()
//                .url("http://bbs.rs.xidian.me/forum.php?forumlist=1&mobile=2")
////                .get(formBody)
//                .build();
//        request.newBuilder().addHeader("Cookie","Q8qA_2132_saltkey=uhe1qPEu; Q8qA_2132_lastvisit=1462970604; Q8qA_2132_ulastactivity=9e4aGf7F4IJPpt%2FNju5yuebQxD80vtH7hkl%2Fv7WkmozioeWo51LJ; Q8qA_2132_auth=54baUIcHmz%2BU30%2Bvhrnf5FlkgFprkqR%2BtxXKwxurQiAxDA1kmP%2BU5IAKKcZdqMcg4fx9U872yDWiM3FDQxainiwMoZA; Q8qA_2132_lastcheckfeed=285665%7C1462974218; Q8qA_2132_lip=202.117.119.4%2C1462974567; Q8qA_2132_visitedfid=157; Q8qA_2132_st_p=285665%7C1462975418%7C5f533edede64ea7d7113975953a0b7e7; Q8qA_2132_viewid=tid_856119; Q8qA_2132_lastact=1462975433%09forum.php%09guide; Q8qA_2132_sid=z8sccj");
        Request request = new com.squareup.okhttp.Request.Builder()
                .addHeader("cookie", "Q8qA_2132_saltkey=uhe1qPEu; Q8qA_2132_lastvisit=1462970604; Q8qA_2132_ulastactivity=9e4aGf7F4IJPpt%2FNju5yuebQxD80vtH7hkl%2Fv7WkmozioeWo51LJ; Q8qA_2132_auth=54baUIcHmz%2BU30%2Bvhrnf5FlkgFprkqR%2BtxXKwxurQiAxDA1kmP%2BU5IAKKcZdqMcg4fx9U872yDWiM3FDQxainiwMoZA; Q8qA_2132_lastcheckfeed=285665%7C1462974218; Q8qA_2132_lip=202.117.119.4%2C1462974567; Q8qA_2132_visitedfid=157; Q8qA_2132_st_p=285665%7C1462975418%7C5f533edede64ea7d7113975953a0b7e7; Q8qA_2132_viewid=tid_856119; Q8qA_2132_lastact=1462975433%09forum.php%09guide; Q8qA_2132_sid=z8sccj")
                .url("http://bbs.rs.xidian.me/forum.php?forumlist=1&mobile=2")
                .build();

        //发送请求获取响应
        try {
            Response response = okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                //打印服务端返回结果
                result = response.body().string();
                cookie = response.header("Set-Cookie");
//                String header = response.header("set-cookie");
//                SharedPreferences cookie = getSharedPreferences("cookie", Context.MODE_PRIVATE);
//                SharedPreferences.Editor edit = cookie.edit();
//                edit.clear();
//                edit.putString("my_cookie", header);
//                edit.commit();
                Log.i("success", result);
                handler.sendEmptyMessage(POSTED);
            } else {
                handler.sendEmptyMessage(NETWORK_EORR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

