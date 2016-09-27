package com.wodm.android.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by json on 2016/4/27.
 */
public class DeviceUtils {


    public static int[] getScreenWH(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return new int[]{width, height};
    }

    public static String getTimes(long times) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String time = format.format(times);
        return time;

    }

    public static Boolean isToday(long times) {
        Calendar now = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(times);
        return cal.get(Calendar.DAY_OF_YEAR) ==
                now.get(Calendar.DAY_OF_YEAR);

    }

    public static String getTimeperiod(long times) {

        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        String time = format.format(times);


        Date now = new Date();
        Date date = new Date(times);
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (day > 1) {
//            time = time;
        } else if (day == 1) {
            time = "昨天";
        } else if (day == 0 && hour == 0 && min == 0) {
            time = "刚刚";
        } else if (day == 0 && hour == 0) {
            time = min + "分钟前";
        } else if (day == 0) {
            time = hour + "小时前";
        }

        return time;
    }

    public static Boolean isRunYear(int year) {
        return year % 400 == 0 || year % 100 != 0 && year % 4 == 0;
    }


    public static int getMaxDayByYearMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DATE);
    }


    public static class SDcardInfo {

        /**
         * 判断SD卡是否插好
         *
         * @return
         */
        public static Boolean isHasCard() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }

        /**
         * 获取SD存储卡总容量
         *
         * @return SD存储卡总容量(单位：字节)
         */
        public static long getAllSize() {
            if (!isHasCard()) {
                return -1l;
            }

            File path = Environment.getExternalStorageDirectory();

            StatFs stat = new StatFs(path.getPath());

            long blockSize = stat.getBlockSize();

            long availableBlocks = stat.getBlockCount();

            return availableBlocks * blockSize;
        }


        /**
         * 获取SD存储卡剩余容量
         *
         * @return SD存储卡剩余容量(单位：字节)
         */
        public synchronized static long getAvailaleSize() {
            if (!isHasCard()) {
                return -1l;
            }

            File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径

            StatFs stat = new StatFs(path.getPath());


            long blockSize = stat.getBlockSize();


            long availableBlocks = stat.getAvailableBlocks();


            return availableBlocks * blockSize;
        }


    }

}
