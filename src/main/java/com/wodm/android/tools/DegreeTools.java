package com.wodm.android.tools;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songchenyu on 16/11/17.
 */

public class DegreeTools {
    private List<Map<Integer,HashMap<String,String>>> btnHashMaps=new ArrayList<>();
    private List<Map<Integer,HashMap<String,String>>> tvHashMaps=new ArrayList<>();
    private DegreeTools degreeTools;
    private int degress;
    private View view;
    private DegreeTools getInstance(int degress, View view){
        if (degreeTools==null){
            degreeTools=new DegreeTools(degress,view);
        }
        return degreeTools;
    }
    public DegreeTools(int degress, View view){

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void initData(){
        Map<Integer,HashMap<String,Integer>> map=null;
        HashMap<String,Integer> hashMap=null;
        for (int i = 0; i < 30; i++) {
            map=new ArrayMap<>();
            hashMap=getHashMap(i);
            getHashMap(i);
        }
    }
    private HashMap<String,Integer> getHashMap(int num){
        HashMap<String,Integer> hashMap=new HashMap<>();

        return hashMap;
    }
    private String setIVI(int num){
        String key=null;
         if (num<5){
             key="萌新";
         }else if (num<10){
             key="死宅";
         }else if (num<15){
             key="绅士";
         }else if (num<20){
             key="老油条";
         }else if (num<20){
             key="肉山大魔王";
         }else if (num<20){
             key="宇宙最强";
         }else {
             key="LV."+num;
         }
        return key;
    }
    private String getVVV(int num){
        String value=null;
        return value;
    }

}
