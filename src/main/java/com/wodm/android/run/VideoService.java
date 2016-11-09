package com.wodm.android.run;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.android.bean.AnimLookCookieBean;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.db.WoDbUtils;

import java.util.List;

/**
 * Created by songchenyu on 16/11/8.
 */

public class VideoService extends Service {
    public static final int MYVIDEOLOOKHISTORY_INSERT=1;
    private String type;
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getExtras() != null) {
            type= intent.getExtras().getString("type");
        }
        if (type.equals("insert")){
            ChapterBean bean=null;
            if (intent.getExtras()!=null){
                bean= (ChapterBean) intent.getExtras().getSerializable("insert");
                if (bean!=null){
                    insertVideo(bean);
                }
            }
        }




        return Service.START_NOT_STICKY;
    }
    private void insertVideo(ChapterBean bean){
        try {
            String rescoureId=bean.getId();
            AnimLookCookieBean animLookCookieBean=new AnimLookCookieBean();
            animLookCookieBean.setRescoureid(rescoureId);
            animLookCookieBean.setAnimname(bean.getTitle());
            int index=bean.getContentUrl().indexOf("?");
            String playUrl=bean.getContentUrl().substring(0,index);
            animLookCookieBean.setAnimUrl(playUrl);
            final int[] time = {getAllLookHistory(rescoureId, playUrl)};
            if (time[0] ==-1){
                WoDbUtils.initialize(getApplicationContext()).delete(AnimLookCookieBean.class,WhereBuilder.b("animUrl"," = ",playUrl));
                WoDbUtils.initialize(getApplicationContext()).save(animLookCookieBean);
//                AnimLookCookieBean animLookCookieBean1=new AnimLookCookieBean();
//                animLookCookieBean1.setTotalTime(100000);
//                animLookCookieBean1.setLookTime(209993);
//                WoDbUtils.initialize(getApplicationContext()).update(animLookCookieBean1, WhereBuilder.b("animUrl","=",playUrl),"totalTime","lookTime");
//                WoDbUtils.initialize(getApplicationContext()).update(AnimLookCookieBean.class, WhereBuilder.b("animUrl","=",playUrl).expr("playState","=",100000));
            }
//            else {
//                int[] playTime = getMinuteAndSecond(time[0]);
//                String strTime=String.format("%02d:%02d", playTime[0], playTime[1]);
//                new AlertDialog.Builder(getBaseContext())
//                        .setMessage("您上次看到了"+strTime+"是否继续观看")
//                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                              }).setNegativeButton("否", new DialogInterface.OnClickListener() {
//                               @Override
//                             public void onClick(DialogInterface dialog, int which) {
//                                   time[0] =0;
//                               }
//                }).create().show();
//            }
            startVideoReciver(time[0],bean.getContentUrl());
//         List<AnimLookCookieBean> beanList= WoDbUtils.initialize(getApplicationContext()).findAll(Selector.from(AnimLookCookieBean.class).where("animUrl", "=", playUrl));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    private int[] getMinuteAndSecond(int mils) {
        mils /= 1000;
        int[] time = new int[2];
        time[0] = mils / 60;
        time[1] = mils % 60;
        return time;
    }
    private void startVideoReciver(int time,String url){
        Intent intent=new Intent("com.vodeo.play.notifition");
        intent.putExtra("playtime",time);
        intent.putExtra("url",url);
        sendBroadcast(intent);
    }

    private int getAllLookHistory(String rescoureId,String url) {
        try {
            List<AnimLookCookieBean> beanList= WoDbUtils.initialize(getApplicationContext()).
                    findAll(Selector.from(AnimLookCookieBean.class).where("animUrl", "=", url).and("rescoureid","=",rescoureId));
            if (beanList!=null&&beanList.size()!=0){
                return beanList.get(0).getLookTime();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return -1;

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
