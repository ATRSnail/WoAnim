package com.wodm.android.run;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.android.bean.AnimLookCookieBean;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.UserBehavierInfo;
import com.wodm.android.db.WoDbUtils;
import com.wodm.android.receiver.UpDataReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchenyu on 16/11/8.
 */

public class DBService extends Service {
    public static final int MYVIDEOLOOKHISTORY_INSERT=1;
    private String type;
    private UpDataReceiver upDataReceiver;
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null&&intent.getExtras() != null) {
            initReceiver();
            type= intent.getExtras().getString("type");
            if (type.equals("insert")){
                ChapterBean bean=null;
                if (intent.getExtras()!=null){
                    bean= (ChapterBean) intent.getExtras().getSerializable("insert");
                    if (bean!=null){
                        insertVideo(bean);
                    }
                }
            }else if (type.equals("findall")){
                findAllUserInfo();
            }else if (type.equals("updataall")){
                upAllData();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }
    private void initReceiver(){
        if (upDataReceiver==null){
            upDataReceiver=new UpDataReceiver();
            IntentFilter intentfilter=new IntentFilter("com.data.up");
            registerReceiver(upDataReceiver,intentfilter);
        }

    }
    private void upAllData(){
        List<UserBehavierInfo> userBehavierInfoList=query();
        if (userBehavierInfoList.size()>0){
            startUpdataReceivce((ArrayList<UserBehavierInfo>) userBehavierInfoList);
        }
    }
    private void findAllUserInfo(){
        List<UserBehavierInfo> userBehavierInfoList=query();
        if (userBehavierInfoList.size()>5){
            startUpdataReceivce((ArrayList<UserBehavierInfo>) userBehavierInfoList);
        }
    }
    /**
     * query all persons, return list
     * @return List<Person>
     */
    public List<UserBehavierInfo> query() {
        ArrayList<UserBehavierInfo> list = new ArrayList<UserBehavierInfo>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            UserBehavierInfo userBehavierInfo = new UserBehavierInfo();
            userBehavierInfo.setBehavier_id(c.getInt(c.getColumnIndex("behavier_id")));
            userBehavierInfo.setStart_time(c.getLong(c.getColumnIndex("start_time")));
            userBehavierInfo.setResourceId(c.getLong(c.getColumnIndex("resourceId")));
            userBehavierInfo.setClickNum(c.getInt(c.getColumnIndex("clickNum")));
            long end_time=c.getLong(c.getColumnIndex("end_time"));
            if (end_time!=0){
                userBehavierInfo.setEnd_time(c.getLong(c.getColumnIndex("end_time")));
                list.add(userBehavierInfo);
            }
        }
        c.close();
        return list;
    }

    /**
     * query all persons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        DbUtils dbUtils= WoDbUtils.initialize(getApplicationContext());
        SQLiteDatabase database=dbUtils.getDatabase();
        Cursor c = database.rawQuery("SELECT * FROM userbehavier", null);
        return c;
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
            }
            startVideoReciver(time[0],bean.getContentUrl());
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
    private void startUpdataReceivce(ArrayList<UserBehavierInfo> list){
        Intent intent=new Intent("com.data.up");
        intent.putExtra("list",list);
        sendBroadcast(intent);
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
        if (upDataReceiver!=null){
            this.unregisterReceiver(upDataReceiver);
        }
    }
}
