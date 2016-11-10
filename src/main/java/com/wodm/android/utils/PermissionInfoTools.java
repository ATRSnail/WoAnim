package com.wodm.android.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by songchenyu on 16/11/4.
 */

public class PermissionInfoTools {
    public static final int MY_PERMISSIONS_REQUEST_COMEAT_CONTACTS=1000000;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS=2000000;
    private Context mContxt;
    public PermissionInfoTools(Context context){
        this.mContxt=context;
    }
    int permissionCheck = ContextCompat.checkSelfPermission(mContxt,
            Manifest.permission.WRITE_CALENDAR);
    public interface SetPermissionCallBack{
        public void IPsermission(boolean isPermsion);
    }
    public void setPermissionCheckListener(Context context,SetPermissionCallBack callBack){

    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void getComeraPermission(Activity activity, SetPermissionCallBack callBack){
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
//			//申请WRITE_EXTERNAL_STORAGE权限
            callBack.IPsermission(false);
            activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_COMEAT_CONTACTS);
            activity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
        }else {
            callBack.IPsermission(true);
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    public static void getWritePermission(Activity activity, SetPermissionCallBack callBack){
// Here, thisActivity is the current activity
        if(Build.VERSION.SDK_INT<23){
            callBack.IPsermission(true);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//			//申请WRITE_EXTERNAL_STORAGE权限
            callBack.IPsermission(false);
            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
            activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }else {
            callBack.IPsermission(true);
        }
    }


}
