package com.wodm.android.dbtools;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.android.bean.AdsClickBean;
import com.wodm.android.bean.UserBehavierInfo;
import com.wodm.android.db.WoDbUtils;

import java.util.List;

/**
 * Created by songchenyu on 16/11/24.
 */

public class DbContraller {
    private static DbContraller dbContraller;
    private static Context mContext;

    public static DbContraller getInstance(Context context){
        mContext=context;
        if (dbContraller==null){
            dbContraller=new DbContraller();
        }
        return dbContraller;
    }
    public  void insert(UserBehavierInfo userBehavierInfo){
        try {
           WoDbUtils.initialize(mContext).save(userBehavierInfo);
           changeClickNumById(userBehavierInfo.getResourceId());
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
    public  void insertAds(AdsClickBean adsClickBean){
        try {
            WoDbUtils.initialize(mContext).save(adsClickBean);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
    public void insertAll(){

    }
    public void update(long resourceId,UserBehavierInfo userBehavierInfo){
        try {
            WoDbUtils.initialize(mContext).update(userBehavierInfo, WhereBuilder.b("resourceId","=",resourceId),"end_time");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateAll(){

    }
    public void delete(){

    }
    public void deleteAll(){

    }
    public void selectAll(){

    }
    public void selectById(long resourceId){
        try {
            List<UserBehavierInfo> beanList= WoDbUtils.initialize(mContext).
                    findAll(Selector.from(UserBehavierInfo.class).where("resourceId","=",resourceId));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void changeClickNumById(long resourceId){
        try {
            List<UserBehavierInfo> beanList= WoDbUtils.initialize(mContext).
                    findAll(Selector.from(UserBehavierInfo.class).where("resourceId","=",resourceId));
            if (beanList!=null&&beanList.size()>0){
                for (UserBehavierInfo info:beanList) {
                    if (info.getResourceId()==resourceId){
                        int num=info.getClickNum();
                        UserBehavierInfo userInfo=new UserBehavierInfo();
                        userInfo.setClickNum(++num);
                        WoDbUtils.initialize(mContext).update(userInfo, WhereBuilder.b("resourceId","=",resourceId),"clickNum");
                    }

                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
