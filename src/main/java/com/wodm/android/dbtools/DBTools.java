package com.wodm.android.dbtools;

import android.content.Context;
import android.content.Intent;

import com.wodm.android.bean.UserBehavierInfo;
import com.wodm.android.run.DBService;
import com.wodm.android.utils.Preferences;

/**
 * Created by songchenyu on 16/11/24.
 */

public class DBTools {
    private static DBTools dbTools;
    private static Context mContext;
    private static DbContraller dbContraller;

    public static DBTools getInstance(Context context){
        mContext=context;
        if (dbTools==null){
            dbTools=new DBTools();
        }
        instanceContraller(context);
        return dbTools;
    }
    private static void instanceContraller(Context context){
        if (dbContraller==null){
            dbContraller=DbContraller.getInstance(context);
        }
    }
    public void inserDB(long rescourId){
        UserBehavierInfo userBehavierInfo=new UserBehavierInfo();
        userBehavierInfo.setResourceId(rescourId);
        int id=Preferences.getInstance(mContext).getPreference("userBehavier", 0);
        userBehavierInfo.setBehavier_id(id);
        userBehavierInfo.setStart_time(System.currentTimeMillis());
        dbContraller.insert(userBehavierInfo);
        startService();
    }
    private void startService(){
        Intent serviceIntent=new Intent(mContext, DBService.class);
        serviceIntent.putExtra("type","findall");
        mContext.startService(serviceIntent);
    }

    public void updateDB(long rescourId){
        UserBehavierInfo userBehavierInfo=new UserBehavierInfo();
        userBehavierInfo.setEnd_time(System.currentTimeMillis());
        dbContraller.update(rescourId,userBehavierInfo);
    }
}