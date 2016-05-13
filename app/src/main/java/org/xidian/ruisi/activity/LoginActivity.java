package org.xidian.ruisi.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xidian.ruisi.MyApplication;
import org.xidian.ruisi.R;
import org.xidian.ruisi.api.Apis;
import org.xidian.ruisi.base.BaseActivity;
import org.xidian.ruisi.bean.loginjson;

import java.io.IOException;


public class LoginActivity extends BaseActivity {

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
    private String cookie = "";
    private String userName = "";
    private String uid = "";
    private loginjson datas;
    private RequestBody formBody;
    private OkHttpClient okHttpClient;
    private String avatarUrl;
    private String res;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case POSTED:
                    if (result.contains("欢迎您回来")) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //存到MyApplication ，接下来的网络操作用
                        MyApplication.myCookie = cookie;
                        MyApplication.userName = datas.getUsername();
                        MyApplication.uid = datas.getUid();
                        MyApplication.userGroup = datas.getUsergroup();
                        MyApplication.groupId = datas.getGroupid();
                        MyApplication.avatarUrl = avatarUrl;
                        MyApplication.setLoginIn();

                        //存到本地，下次登录用
                        SharedPreferences mCookie = getSharedPreferences("cookie", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = mCookie.edit();
                        edit.clear();
                        edit.putString("my_cookie", cookie);
                        edit.putString("my_userid", MyApplication.uid);
                        edit.putString("my_usergroup", MyApplication.userGroup);
                        edit.putString("my_userName", MyApplication.userName);
                        edit.putString("my_avatarurl", MyApplication.avatarUrl);
                        edit.commit();
                        Apis.USERID = MyApplication.uid;
                        Intent intent = new Intent();
                        intent.putExtra("AvatarUrl", avatarUrl);
//                        setResult(RESULT_OK, intent);
                        Intent intent1 = new Intent("BBS.RS.XIDIAN.ME");
                        sendBroadcast(intent1);
                        finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().setEnterTransition(new Fade());
//        getWindow().setExitTransition(new Fade());
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
        okHttpClient = new OkHttpClient();
        Gson gson = new Gson();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
//        RequestBody requestBody = RequestBody.create(JSON, json);
        RequestBody formBody = new FormEncodingBuilder()
                .add("cookietime", "2592000")
                .add("username", name.trim())
                .add("password", password.trim())
                .build();
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(Apis.LoginUrl)
                .post(formBody)
                .build();

        //发送请求获取响应
        try {
            Response response = okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                //打印服务端返回结果
                result = response.body().string();
                for (int i = 0; i < response.headers("Set-Cookie").size(); i++) {
                    cookie += response.headers("Set-Cookie").get(i).split(";")[0] + ";";
                }

                if (result.contains("欢迎您回来")) {
                    int start = result.indexOf("现在将转入登录前页面', ");
                    start += "现在将转入登录前页面', ".length();
                    int end = result.indexOf(");}</script></p>");
                    datas = (gson.fromJson(result.substring(start, end), new TypeToken<loginjson>() {
                    }.getType()));
                    getAvatar();
                }
                handler.sendEmptyMessage(POSTED);
            } else {
                handler.sendEmptyMessage(NETWORK_EORR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户头像，该方法在子线程运行
     */
    private void getAvatar() {
        Request request = new com.squareup.okhttp.Request.Builder()
                .addHeader("Cookie", cookie)
                .url(Apis.AvatarUrl)
                .build();
        //发送请求获取响应
        try {
            Response response = okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                //打印服务端返回结果
                res = response.body().string();
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = Jsoup.parse(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        avatarUrl = doc.select("div.avatar_m").select("span").select("img").attr("src");

    }
}

