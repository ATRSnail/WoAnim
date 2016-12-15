package com.wodm.android.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
        return context != null ? ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth() : 0;
    }

    public static int getScreenHeight(Activity context) {
        WindowManager wm = context.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    public static int dp2px(Context paramContext, float paramFloat) {
        return dp2px(paramContext.getResources(), paramFloat);
    }

    public static int dp2px(Resources paramResources, float paramFloat) {
        return (int) (0.5F + paramFloat * paramResources.getDisplayMetrics().density);
    }


}
