package com.wodm.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.DowmBean;
import com.wodm.android.utils.CommonAdapter;

import java.util.List;

/**
 * Created by json on 2016/5/5.
 */
public class SeriesDowmAdapter extends CommonAdapter<DowmBean> {

    private Boolean isShowAll = false;
    private int num = 16;
    private int indexCheck = -1;

    public SeriesDowmAdapter(Context context, List<DowmBean> list, int num) {
        super(context, list);
        this.num = num << 1;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.adapter_series;
    }

    @Override
    protected void setViews(Viewholder holder, DowmBean bean) {

        TextView btn = holder.getView(R.id.button);
        holder.setText(R.id.button, bean.getChapter());
        if (holder.getPosition() == indexCheck) {
            btn.setTextColor(Color.WHITE);
            holder.getView(R.id.layout).setBackgroundResource(R.drawable.series_anim_select);
        }
        if (holder.getPosition() + 1 == getCount() && isShowAll) {
            btn.setText("更多");
            holder.getView(R.id.img).setVisibility(View.INVISIBLE);
        }
    }




    //
    public void setIndexCheck(int indexCheck) {
        this.indexCheck = indexCheck;
    }

    //
    public void setShowAll() {
        isShowAll = false;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (isShowAll) {
            if (list.size() > this.num) {
                return this.num;
            } else {
                return list.size();
            }
        } else {
            return list.size();
        }
    }

}
