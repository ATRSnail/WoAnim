package com.wodm.android.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by songchenyu on 16/12/19.
 */

public class Untils {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
