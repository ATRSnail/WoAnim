package com.wodm.android.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;


/**
 * Created by ATRSnail on 2016/11/29.
 */

public class TextColorUtils {
    private TextColorUtils() {
    }

    private static TextColorUtils textColorUtils = null;


    public static TextColorUtils getInstance() {
        if (textColorUtils == null) {
            synchronized (TextColorUtils.class) {
                if (textColorUtils == null) {
                    textColorUtils = new TextColorUtils();
                }
            }

        }
        return textColorUtils;
    }


    public void setGradeColor(Context con, TextView textView, int num) {

        String str = "#85d97b";
        if (num <= 5) {
            str = "#85d97b";
        } else if (num <= 10) {
            str = "#52c0ff";
        } else if (num <= 15) {
            str = "#b960e7";
        } else if (num <= 20) {
            str = "#f775d7";
        } else if (num <= 25) {
            str = "#fe6e6e";
        } else if (num <= 30) {
            str = "#ffc452";
        }
        int color = Color.parseColor(str);
        textView.setTextColor(color);
        textView.setText("LV."+num);

    }
}
