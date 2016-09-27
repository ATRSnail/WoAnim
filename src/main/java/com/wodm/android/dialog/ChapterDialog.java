package com.wodm.android.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.adapter.SeriesAdapter;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.utils.DeviceUtils;

import org.eteclab.ui.widget.NoScrollGridView;

import java.util.List;

/**
 * Created by json on 2016/4/27.
 */
public class ChapterDialog extends Dialog {
    private Context mCtx;

    NoScrollGridView scrollGridView;

    public void show(Boolean isLand) {
        Window window = getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = isLand ? DeviceUtils.getScreenWH((Activity) mCtx)[0] * 65 / 100 : ViewGroup.LayoutParams.MATCH_PARENT; // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        getWindow().setGravity(isLand ? Gravity.CENTER : Gravity.BOTTOM);
        super.show();
    }

    public ChapterDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_chapter);
        mCtx = context;
        scrollGridView = (NoScrollGridView) findViewById(R.id.grid_new);
        findViewById(R.id.close_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setListViews(final List<ChapterBean> mChapterList, ChapterBean index) {
        final SeriesAdapter adapter = new SeriesAdapter(mCtx, mChapterList,8);
        index.setCheck(index.IsCheck());

        scrollGridView.setAdapter(adapter);
        scrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((TextView) view.findViewById(R.id.button)).getText().equals("更多")) {
                    adapter.setShowAll();
                } else {
                    ((AnimDetailActivity) (mCtx)).startPlay(mChapterList.get(position));
                    dismiss();
                }

            }
        });
    }
}