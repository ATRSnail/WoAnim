package com.wodm.android.tools;

import android.content.Context;
import android.content.Intent;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.db.WoDbUtils;
import com.wodm.android.run.DBService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchenyu on 17/1/9.
 */

public class JujiDbTools {
    private static JujiDbTools muluDbTools=null;
    private static Context mContext;
    public static JujiDbTools getInstance(Context context){
        mContext=context;
        if (muluDbTools==null){
            synchronized (JujiDbTools.class){
                if (muluDbTools==null){
                    muluDbTools=new JujiDbTools();
                }
            }
        }
        return muluDbTools;
    }

    /**
     * 保存某一状态下所有的剧集
     */
    public void insertAll(ArrayList<ChapterBean> mChapterList,int resourceId){
        Intent serviceIntent=new Intent(mContext, DBService.class);
        serviceIntent.putExtra("type","insertchapter");
        serviceIntent.putExtra("insertchapter",mChapterList);
        serviceIntent.putExtra("resourceId",resourceId);
        mContext.startService(serviceIntent);
    }
    /**
     * 保存某一状态下所有的剧集
     */
    public ArrayList<ChapterBean> findAll(int resourceId){
        ArrayList<ChapterBean> arraylist=new ArrayList<>();
        try {
            List<ChapterBean> list = WoDbUtils.initialize(mContext).findAll(Selector.from(ChapterBean.class).where("resourceId","=",resourceId));
            if (list!=null&&list.size()>0){
                arraylist.addAll(list);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return arraylist;
    }
    public void update(long resourceId){
        ChapterBean chapterBean=new ChapterBean();
        //1代表已经观看  0代表尚未观看
        chapterBean.setIsWatch(1);
        try {
            WoDbUtils.initialize(mContext).update(chapterBean, WhereBuilder.b("resourceId","=",resourceId),"isWatch");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
