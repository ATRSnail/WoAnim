package com.wodm.android.tools;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songchenyu on 16/9/27.
 */

public class Tools  {
    public static boolean isMobileNO(String phone) {
        Pattern p = Pattern.compile("^1(3[0-2]|5[56]|8[56])\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
    public static String  getText(TextView view) {
        String text=view.getText().toString();
        return text;
    }
    public static int getScreenWidth(Activity context) {
        WindowManager wm = context.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }


}
