package com.wodm.android.tools;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.wodm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by songchenyu on 16/11/17.
 */

public class DegreeTools {
    private Map<Integer,HashMap<String,Drawable>> btnHashMaps=new HashMap<>();
    private List<Map<Integer,HashMap<String,String>>> tvHashMaps=new ArrayList<>();
    private static DegreeTools degreeTools;
    private  int mDegress;
    private  static Context mContext;
    private  View mView;
    public static DegreeTools getInstance(Context context){
        mContext=context;
        if (degreeTools==null){
            degreeTools=new DegreeTools();
        }
        return degreeTools;
    }
    public DegreeTools(){
        initData();
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void getDegree(Context context, int degress, TextView view){
        mContext=context;
        mDegress=degress;
        mView=view;
        HashMap<String,Drawable> map=btnHashMaps.get(degress);
        Iterator i = map.entrySet().iterator();
//        while (i.hasNext()) {
//            Object obj = i.next();
//            String key = obj.toString();
//        }
        while (i.hasNext()) {
            Map.Entry entry = (java.util.Map.Entry)i.next();
            String key=entry.getKey().toString();
            view.setText(key);
            Drawable drawable= (Drawable) entry.getValue();
            view.setBackground(drawable);
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void initData(){
        Map<Integer,HashMap<String,Drawable>> map=new HashMap<>();
        HashMap<String,Drawable> hashMap=null;
        for (int i = 1; i <= 30; i++) {
            hashMap=getHashMap(i);
            getHashMap(i);
            map.put(i,hashMap);
        }
        btnHashMaps.putAll(map);
    }
    private HashMap<String,Drawable> getHashMap(int num){
        HashMap<String,Drawable> hashMap=new HashMap<>();
        setIVI(hashMap,num);
        return hashMap;
    }
    private String setIVI(HashMap<String,Drawable> hashMap,int num){
        String key=null;
        Drawable drawable=null;
         if (num<=5){
             key="萌新";
             drawable=mContext.getResources().getDrawable(R.drawable.btn_degree_i);
         }else if (num<=10){
             key="死宅";
             drawable=mContext.getResources().getDrawable(R.drawable.btn_degree_iv);
         }else if (num<=15){
             key="绅士";
             drawable=mContext.getResources().getDrawable(R.drawable.btn_degree_iii);
         }else if (num<=20){
             key="老油条";
             drawable=mContext.getResources().getDrawable(R.drawable.btn_degree_ii);
         }else if (num<=25){
             key="肉山大魔王";
             drawable=mContext.getResources().getDrawable(R.drawable.btn_degree_vv);
         }else if (num<=30){
             key="宇宙最强";
             drawable=mContext.getResources().getDrawable(R.drawable.btn_degree_v);
         }else {
             key="LV."+num;
         }
        String nameStr=key+getVVV(num);
        hashMap.put(nameStr,drawable);
        return key;
    }
    private String getVVV(int num){
        String value=null;
        if (num%5==0){
            value="Ⅴ";
        }else if (num%5==1){
            value="I";
        }else if (num%5==2){
            value="Ⅱ";
        }else if (num%5==3){
            value="Ⅲ";
        }else if (num%5==4){
            value="Ⅳ";
        }else {
            value=""+num;
        }
        return value;
    }

}
