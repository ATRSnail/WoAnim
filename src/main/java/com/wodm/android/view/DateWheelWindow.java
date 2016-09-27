package com.wodm.android.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.utils.DeviceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by json on 2016/5/4.
 */
public class DateWheelWindow extends PopupWindow {
    public DateWheelWindow() {
    }


    public void showPopWindow(final Context context, View parent, int gravity, final String format, final DateResultCall resultCall) {
        LinearLayout layout = new LinearLayout(context);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1);
        layout.setLayoutParams(lp);
        layout.setGravity(80);
        layout.setBackgroundColor(Color.argb(150, 0, 0, 0));
        View child = View.inflate(context, R.layout.layout_view_wheel, null);
        layout.addView(child);
        final DateWheelWindow popupWindow = new DateWheelWindow();
        popupWindow.setWidth(-1);
        popupWindow.setHeight(-1);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setContentView(layout);
        final NumberPicker mYearView = (NumberPicker) layout.findViewById(R.id.year);
        final NumberPicker mMonthView = (NumberPicker) layout.findViewById(R.id.month);
        final NumberPicker mDayView = (NumberPicker) layout.findViewById(R.id.day);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
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
                    resultCall.resultCall(getResult(mYearView.getValue(), mMonthView.getValue() - 1, mDayView.getValue(), format));
            }
        });


        mYearView.setMinValue(Calendar.getInstance().get(Calendar.YEAR) - 100);
        mMonthView.setMinValue(1);
        mDayView.setMinValue(1);

        mYearView.setMaxValue(Calendar.getInstance().get(Calendar.YEAR));
        mMonthView.setMaxValue(Calendar.getInstance().get(Calendar.MONTH) + 1);
        mDayView.setMaxValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        mYearView.setValue(Calendar.getInstance().get(Calendar.YEAR));
        mMonthView.setValue(Calendar.getInstance().get(Calendar.MONTH) + 1);
        mDayView.setValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


        mMonthView.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if (mYearView.getValue() == Calendar.getInstance().get(Calendar.YEAR) && Calendar.getInstance().get(Calendar.MONTH) + 1 == newVal) {
                    mDayView.setMaxValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                } else {
                    mDayView.setMaxValue(DeviceUtils.getMaxDayByYearMonth(mYearView.getValue(), newVal - 1));
                }
//                Toast.makeText(context, oldVal + " , mm " + (mYearView.getValue() == Calendar.getInstance().get(Calendar.YEAR) && Calendar.getInstance().get(Calendar.MONTH) + 1 == newVal), 0).show();
            }
        });
        mYearView.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Toast.makeText(context, oldVal + " ,yy " + (newVal == Calendar.getInstance().get(Calendar.YEAR)), 0).show();

                if (newVal == Calendar.getInstance().get(Calendar.YEAR)) {
                    mMonthView.setMaxValue(Calendar.getInstance().get(Calendar.MONTH) + 1);

                    if (mMonthView.getValue() == Calendar.getInstance().get(Calendar.MONTH) + 1) {
                        mDayView.setMaxValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    }
                    return;
                } else {
                    mMonthView.setMaxValue(12);
                }

                if (DeviceUtils.isRunYear(newVal)) {
                    if (mMonthView.getValue() == 2) {
                        mDayView.setMaxValue(29);
                    }
                } else {
                    mDayView.setMaxValue(28);
                }
                mDayView.setMaxValue(DeviceUtils.getMaxDayByYearMonth(newVal, mMonthView.getValue()));
            }
        });
        popupWindow.showAtLocation(parent, gravity, 0, 0);
    }

    private String getResult(int y, int m, int d, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m, d);
        Date date = calendar.getTime();
        SimpleDateFormat formats = new SimpleDateFormat(format);
        return formats.format(date);
    }

    public static class DateResultCall {

        void resultCall(String result) {
        }
    }
}
