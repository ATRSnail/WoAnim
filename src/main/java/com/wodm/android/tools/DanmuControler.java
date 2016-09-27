package com.wodm.android.tools;

import android.content.Context;

import com.wodm.android.bean.CommentBean;

import java.util.ArrayList;

import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by songchenyu on 16/9/26.
 */

public class DanmuControler {
    private static DanmuControler danmuControler;
    private DanMuTools danMuTools;
    public DanmuControler(Context context,DanmakuView mDanmakuView){
        danMuTools=new DanMuTools(context,mDanmakuView);
    }
    public void release(){
        danMuTools.onDestroy();
    }
    public void show(){
        danMuTools.show();
    }
    public void hide(){
        danMuTools.hide();
    }
    public void addData(ArrayList<CommentBean> commentBeanList){
        danMuTools.addData(commentBeanList);
    }




}
