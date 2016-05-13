package org.xidian.ruisi;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static List<Activity> activities = new ArrayList<Activity>();
    private static MyApplication myApplication = new MyApplication();
    private static boolean isLogin = false;
    private SharedPreferences sharedPreferences, sharedPreferences1;
    public static int count = 0;
    public static String myCookie;
    public static String userName = "";
    public static String uid = "";
    public static String userGroup = "";
    public static String groupId = "";
    public static String avatarUrl = "";

    public MyApplication() {

    }

    @Override
    public void onCreate() {
        sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("isLogin", false);
        sharedPreferences1 = getSharedPreferences("collect_upload_state", Context.MODE_PRIVATE);
        count = sharedPreferences1.getInt("isUpload", 0);
        SharedPreferences mCookie = getSharedPreferences("cookie", Context.MODE_PRIVATE);
        myCookie = mCookie.getString("my_cookie", "");
        uid = mCookie.getString("my_userid", "");
        userName = mCookie.getString("my_userName", "");
        userGroup = mCookie.getString("my_usergroup", "");
        avatarUrl = mCookie.getString("my_avatarurl", "");
        if (myCookie.length() > 0) {
            isLogin = true;
        }
        super.onCreate();
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public void exit() {
        try {
            for (Activity activity : activities) {
                if (!activity.isFinishing() && activity != null) {
                    activity.finish();
                }
            }
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {

        }

    }


    public static void setLoginIn() {
        isLogin = true;
    }

    public void setLoginOut() {
        isLogin = false;
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void closeAllActivity() {
        for (Activity activity1 : activities) {
            if (!activity1.isFinishing() && activity1 != null) {
                activity1.finish();
            }
        }
    }

}