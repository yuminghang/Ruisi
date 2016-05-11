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
    private static List<Activity> tvActivities = new ArrayList<Activity>();
    private static List<Activity> videoActivities = new ArrayList<Activity>();
    private static List<Activity> newsActivities = new ArrayList<Activity>();


    public MyApplication() {

    }

    @Override
    public void onCreate() {
        sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("isLogin", false);
        sharedPreferences1 = getSharedPreferences("collect_upload_state", Context.MODE_PRIVATE);
        count = sharedPreferences1.getInt("isUpload", 0);
        SharedPreferences mCookie = getSharedPreferences("cookie", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mCookie.edit();
        edit.clear();
        edit.putString("my_cookie", "Q8qA_2132_saltkey=uhe1qPEu; Q8qA_2132_lastvisit=1462970604; Q8qA_2132_ulastactivity=9e4aGf7F4IJPpt%2FNju5yuebQxD80vtH7hkl%2Fv7WkmozioeWo51LJ; Q8qA_2132_auth=54baUIcHmz%2BU30%2Bvhrnf5FlkgFprkqR%2BtxXKwxurQiAxDA1kmP%2BU5IAKKcZdqMcg4fx9U872yDWiM3FDQxainiwMoZA; Q8qA_2132_lastcheckfeed=285665%7C1462974218; Q8qA_2132_lip=202.117.119.4%2C1462974567; Q8qA_2132_visitedfid=157; Q8qA_2132_st_p=285665%7C1462975418%7C5f533edede64ea7d7113975953a0b7e7; Q8qA_2132_viewid=tid_856119; Q8qA_2132_lastact=1462975433%09forum.php%09guide; Q8qA_2132_sid=z8sccj");
        edit.commit();
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

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void setLoginIn() {
        isLogin = true;
    }

    public void setLoginOut() {
        isLogin = false;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public static void addTvActivity(Activity activity) {
        tvActivities.add(activity);
    }

    public static void closeAllTvActivity() {
        for (Activity activity1 : tvActivities) {
            if (!activity1.isFinishing() && activity1 != null) {
                activity1.finish();
            }
        }
    }

    public static void addVideoActivity(Activity activity) {
        videoActivities.add(activity);
    }

    public static void closeAllVideoActivity() {
        for (Activity activity1 : videoActivities) {
            if (!activity1.isFinishing() && activity1 != null) {
                activity1.finish();
            }
        }
    }

    public static void addNewsActivity(Activity activity) {
        newsActivities.add(activity);
    }

    public static void closeAllNewsActivity() {
        for (Activity activity1 : newsActivities) {
            if (!activity1.isFinishing() && activity1 != null) {
                activity1.finish();
            }
        }
    }

}