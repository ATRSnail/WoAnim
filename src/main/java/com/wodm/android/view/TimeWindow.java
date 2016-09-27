package com.wodm.android.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;

import com.wodm.R;
import com.wodm.android.utils.DeviceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by json on 2016/5/4.
 */
public class TimeWindow extends PopupWindow {
    public TimeWindow() {
    }


    public void showPopWindow(Context context, View parent, int gravity, final TimeCall resultCall) {
        LinearLayout layout = new LinearLayout(context);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1);
        layout.setLayoutParams(lp);
        layout.setGravity(80);
        layout.setBackgroundColor(Color.argb(150, 0, 0, 0));
        View child = View.inflate(context, R.layout.layout_view_time, null);
        layout.addView(child);
        final TimeWindow popupWindow = new TimeWindow();
        popupWindow.setWidth(-1);
        popupWindow.setHeight(-1);
        popupWindow.setContentView(layout);
        final NumberPicker mHourView = (NumberPicker) layout.findViewById(R.id.hour);
        final NumberPicker mMinView = (NumberPicker) layout.findViewById(R.id.min);
        mHourView.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value == mHourView.getValue() ? value + "时" : String.valueOf(value);
            }
        });
        mMinView.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value == mMinView.getValue() ? value + "分" : String.valueOf(value);
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        layout.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        layout.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (resultCall != null)
                    resultCall.resultCall(getResult(mHourView.getValue(), mMinView.getValue()), mHourView.getValue(), mMinView.getValue());
            }
        });

        mHourView.setMaxValue(23);
        mHourView.setMinValue(0);

        mMinView.setMaxValue(59);
        mMinView.setMinValue(0);

        popupWindow.showAtLocation(parent, gravity, 0, 0);
    }

    private String getResult(int h, int m) {

        if (h != 0) {
            return h + "小时" + m + "分钟";
        } else {
            return m + "分钟";
        }
    }

    public static class TimeCall {

        void resultCall(String result, int hour, int min) {
        }
    }
}
