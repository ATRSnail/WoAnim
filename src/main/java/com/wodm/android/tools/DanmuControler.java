package com.wodm.android.tools;

import android.content.Context;

import com.wodm.android.bean.BarrageBean;

import java.util.ArrayList;

import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by songchenyu on 16/9/26.
 */

public class DanmuControler {
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
    public void addData(ArrayList<BarrageBean> commentBeanList){
        danMuTools.addData(commentBeanList);
    }
    public void addBuilt(String text,int color){
        danMuTools.addDanmakuContraller(text,color);
    }
    public void setDanmakuView(DanmakuView danmakuView){
        danMuTools.setDanMuView(danmakuView);
    }
}
