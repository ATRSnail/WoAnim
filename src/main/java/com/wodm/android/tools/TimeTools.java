package com.wodm.android.tools;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by songchenyu on 16/9/19.
 */

public class TimeTools {
   public static String getTime(long time){
       //mill为你龙类型的时间戳
       Date date=new Date(time);
       String strs="";
       try {
//yyyy表示年MM表示月dd表示日
//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
           SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
//进行格式化
           strs=sdf.format(date);
           System.out.println(strs);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return strs;
   }
    public static String getNianTime(){
        //mill为你龙类型的时间戳
        Date date=new Date(System.currentTimeMillis());
        String strs="";
        try {
//yyyy表示年MM表示月dd表示日
//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strs=sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }
}
