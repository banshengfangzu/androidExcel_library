package cn.nuaa.dmrfcoder.androidexceldemo;

import android.app.Application;
import android.content.Context;


/**
 * Created by cxf on 2017/8/3.
 */

public class AppContext extends Application {

    public static AppContext sInstance;
    public static boolean sDeBug;
    private int mCount;
    private boolean mFront;//是否前台
    //public static RefWatcher sRefWatcher;
    private boolean mBeautyInited;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
//        if (sDeBug) {
//            sRefWatcher = LeakCanary.install(this);
//        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
