package com.wodm.android;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.eteclab.ShareSdks;
import org.eteclab.track.TrackApplication;
import org.eteclab.track.utils.EneticConstance;

public class CartoonApplication extends TrackApplication {
    private static final Class<?> TAG = CartoonApplication.class;
    /**
     * 全局上下文
     */
    private static Context mContext;
    private static String uuid;
    private static String udid;
    private int DEVICEID_REQUEST_CODE=100000000;

    public static Context getContext() {
        return mContext;
    }

    public static String getUuid() {
        return uuid;
    }

    public static String getUdid() {
        return udid;
    }

    /**
     * 应用启动 初始化操作
     */
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler.getInstance().init(getApplicationContext());//默认系统处理bug,调试时注释处理才可看到bug
        mContext = getApplicationContext();
        Fresco.initialize(mContext);
        ShareSdks.initShare(getApplicationContext());
        // 先屏蔽主要是没有集成激光sdk
//        JPushInterface.setDebugMode(false);
//        JPushInterface.init(this);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String mac = EneticConstance.getMacAddress();
        if (hasVideoPermission()){
            String did = tm.getDeviceId();
            udid = EneticConstance.UDID(did, mac);
            uuid = EneticConstance.UUID(this.appKey(), udid);
        }else {
            udid = EneticConstance.UDID("0000000000000000", mac);
            uuid = EneticConstance.UUID(this.appKey(), udid);
        }

        setHttpSuccessCode("1000");
    }
    public boolean hasVideoPermission(){
        if(Build.VERSION.SDK_INT<23)
            return true;
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else {
            return true;
        }

    }

    /**
     * 应用结束 回收收尾操作
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    public String channelId() {
        return "a00000";
    }
}