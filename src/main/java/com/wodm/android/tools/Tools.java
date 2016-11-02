package com.wodm.android.tools;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by songchenyu on 16/9/27.
 */

public class Tools {
    public static boolean isMobileNO(String phone) {
        boolean isPhoneNum = PhoneUtils.isPhoneNum(phone);

        return isPhoneNum;
    }

    public static String getText(TextView view) {
        String text = view.getText().toString();
        return text;
    }

    public static int getScreenWidth(Activity context) {
        WindowManager wm = context.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getScreenHeight(Activity context) {
        WindowManager wm = context.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }


}
