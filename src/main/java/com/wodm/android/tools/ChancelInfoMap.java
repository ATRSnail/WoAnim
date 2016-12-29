package com.wodm.android.tools;

import java.util.HashMap;

/**
 * Created by songchenyu on 16/12/28.
 */

public class ChancelInfoMap {
    private static final HashMap<Integer,String> hashMap=new HashMap<>();
    private static ChancelInfoMap chancelInfoMap;
    public  static ChancelInfoMap getInstance(){
        if (chancelInfoMap==null){
            synchronized (ChancelInfoMap.class){
                if (chancelInfoMap==null){
                    chancelInfoMap=new ChancelInfoMap();
                    initChancelMap();
                }
            }
        }
        return chancelInfoMap;
    }
    private static void initChancelMap(){
        hashMap.put(0,"萌呷");
        hashMap.put(1,"沃商店");
        hashMap.put(2,"豌豆荚");
        hashMap.put(3,"360");
        hashMap.put(4,"91");
        hashMap.put(5,"百度");
        hashMap.put(6,"QQ");
        hashMap.put(7,"安卓市场");
        hashMap.put(8,"安智市场");
        hashMap.put(9,"3G门户");
        hashMap.put(10,"应用汇");
    }
    public static String getChancelInteger(int name){
        if (hashMap.size()==0){
            initChancelMap();
        }
        String chancelId=hashMap.get(name);
        return chancelId;
    }
}
