package com.wodm.android.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.adapter.SeriesAdapter;
import com.wodm.android.adapter.SeriesDowmAdapter;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.DowmBean;
import com.wodm.android.bean.ObjectBean;

import org.eteclab.ui.widget.NoScrollGridView;

import java.util.ArrayList;

/**
 * Created by json on 2016/5/4.
 */
public class ChapterWindow extends PopupWindow {
    public ChapterWindow() {
    }

    public PopupWindow showPopWindow(Context context, View parent, final ArrayList<ChapterBean> lists, int indexCheck, ObjectBean bean, int position) {
        LinearLayout layout = new LinearLayout(context);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1);
        layout.setLayoutParams(lp);
        layout.setGravity(80);
//        lp.width= ViewGroup.LayoutParams.WRAP_CONTENT;
        layout.setBackgroundColor(Color.argb(150, 0, 0, 0));
        View child = View.inflate(context, R.layout.layout_view_chapter, null);
        layout.addView(child);

        final ChapterWindow popupWindow = new ChapterWindow();
        popupWindow.setWidth(-1);
        popupWindow.setHeight(-1);
        popupWindow.setContentView(layout);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        NoScrollGridView gridView = (NoScrollGridView) layout.findViewById(R.id.grid_new);
        ArrayList<ChapterBean> list = new ArrayList<>();
        for (ChapterBean bn : lists) {
            if (indexCheck == lists.indexOf(bn)) {
                bn.setCheck(3);
            } else
                bn.setCheck(0);
            list.add(bn);
        }
        final SeriesAdapter adapter = new SeriesAdapter(context, lists, 8);
        adapter.setObjectBean(bean);
        adapter.setIndex(position);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((TextView) view.findViewById(R.id.button)).getText().equals("更多")) {
                    adapter.setShowAll();

                } else {
                    popupWindow. dismiss();
                    requestHttps(position);
                }
            }


        });
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        int h1 = parent.getMeasuredHeight();
        int h = parent.getBottom() - parent.getTop();
        popupWindow.showAtLocation(parent, Gravity.TOP, location[0], location[1] - h);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    public PopupWindow showPopWindows(Context context, View parent, final ArrayList<DowmBean> lists, String path, ObjectBean bean, int position) {
        LinearLayout layout = new LinearLayout(context);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(-1, -1);
        layout.setLayoutParams(lp);
        layout.setGravity(80);
//        lp.width= ViewGroup.LayoutParams.WRAP_CONTENT;
        layout.setBackgroundColor(Color.argb(150, 0, 0, 0));
        View child = View.inflate(context, R.layout.layout_view_chapter, null);
        layout.addView(child);

        final ChapterWindow popupWindow = new ChapterWindow();
        popupWindow.setWidth(-1);
        popupWindow.setHeight(-1);
        popupWindow.setContentView(layout);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        NoScrollGridView gridView = (NoScrollGridView) layout.findViewById(R.id.grid_new);

        final SeriesDowmAdapter adapter
                = new SeriesDowmAdapter(context, lists, 8);
        for (DowmBean bn : lists) {
            if (bn.getPath().equals(path)) adapter.setIndexCheck(lists.indexOf(bn));
        }

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((TextView) view.findViewById(R.id.button)).getText().equals("更多")) {
                    adapter.setShowAll();

                } else {
                    popupWindow. dismiss();
                    requestPaths(lists.get(position));
                }
            }


        });
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        int h1 = parent.getMeasuredHeight();
        int h = parent.getBottom() - parent.getTop();
        popupWindow.showAtLocation(parent, Gravity.TOP, location[0], location[1] - h);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    public void requestHttps(int position) {
    }

    public void requestPaths(DowmBean bean) {
    }
}