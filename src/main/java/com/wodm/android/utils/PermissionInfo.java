package com.wodm.android.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by songchenyu on 16/11/4.
 */

public class PermissionInfo {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1000000;
    private Context mContxt;
    public PermissionInfo(Context context){
        this.mContxt=context;
    }
    int permissionCheck = ContextCompat.checkSelfPermission(mContxt,
            Manifest.permission.WRITE_CALENDAR);
    public interface SetPermissionCallBack{
        public void IPsermission();
    }
    public void setPermissionCheckListener(Context context,SetPermissionCallBack callBack){

    }

    public static boolean getComeraPermission(Activity context, SetPermissionCallBack callBack){
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_CONTACTS)) {

            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            }
        }
        return false;
    }


}
