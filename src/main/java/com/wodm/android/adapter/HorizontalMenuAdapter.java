package com.wodm.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.TabItemBean;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created moon1 henry on 2016/4/26.
 */
@Layout(R.layout.adapter_item_menu)
public class HorizontalMenuAdapter extends HolderAdapter<TabItemBean> {
    private int index = -1;
    private AllOnClickListener allOnClickListener;
    private boolean isCheck=false;

    public void setIndex(int index) {
        this.index = index;
    }

    public HorizontalMenuAdapter(Context context, List<TabItemBean> datas) {
        super(context, datas);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new Holder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        TabItemBean bean=mData.get(position);
        ((Holder) holder).text.setText(bean.getName());
        if (index == position||bean.isClick()) {
            if (allOnClickListener!=null){
                allOnClickListener.isChildClick(true);
            }
            isCheck=true;
            ((Holder) holder).text.setBackgroundResource(R.drawable.shape_text_color);
            ((Holder) holder).text.setTextColor(mContext.getResources().getColor(R.color.more_color));
        } else {
            if (allOnClickListener!=null){
                if (position==getItemCount()-1){
                    if (!isCheck){
                        allOnClickListener.isChildClick(false);
                    }
                }
            }
            ((Holder) holder).text.setBackgroundResource(R.drawable.shape_text_bg);
            ((Holder) holder).text.setTextColor(Color.BLACK);
        }
    }
    public void setOnChildClickListener(AllOnClickListener onClickListener) {
        this.allOnClickListener = onClickListener;
    }
    public interface AllOnClickListener{
        public boolean isChildClick(boolean isClick);
    }

    class Holder extends HolderAdapter.BaseViewHolder {
        @ViewIn(R.id.tab_name)
        TextView text;

        public Holder(View view) {
            super(view);
        }
    }
}
