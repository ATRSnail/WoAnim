package com.wodm.android.db;

import android.content.Context;

import com.lidroid.xutils.exception.DbException;
import com.wodm.android.bean.AdsClickBean;
import com.wodm.android.bean.AnimLookCookieBean;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.SeacherBean;
import com.wodm.android.bean.UserBehavierInfo;


/**
 * Created by json on 2016/5/5.
 */
public class WoDbUtils {
    private static com.lidroid.xutils.DbUtils instance;
    private static final int VERSION = 1;


    public static synchronized com.lidroid.xutils.DbUtils initialize(Context context) {
        if (instance == null) {
            instance = com.lidroid.xutils.DbUtils.create(context, "wodm.db", VERSION, new com.lidroid.xutils.DbUtils.DbUpgradeListener() {
                public void onUpgrade(com.lidroid.xutils.DbUtils utils, int oldVersion, int newVersion) {
                    try {
                        utils.configDebug(true);
                        utils.dropTable(SeacherBean.class);
                        utils.createTableIfNotExist(SeacherBean.class);
                        utils.createTableIfNotExist(AnimLookCookieBean.class);
                        utils.createTableIfNotExist(UserBehavierInfo.class);
                        utils.createTableIfNotExist(AdsClickBean.class);
                        utils.createTableIfNotExist(ChapterBean.class);
                    } catch (DbException var5) {
                        var5.printStackTrace();
                    }
                }
            });
            instance.configAllowTransaction(true);
        }
        return instance;
    }
}