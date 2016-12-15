package com.wodm.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.TabItemBean;
import com.wodm.android.bean.TypeBean;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by moon1 on 2016/4/27.
 */
@Layout(R.layout.adapter_type_tab)
public class TabTypeAdapter extends HolderAdapter<TypeBean> {
    public TabTypeAdapter(Context context, List<TypeBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new Holder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holders, int position) {
        final TypeBean bean = mData.get(position);
        final Holder holder = (Holder) holders;
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.mTabList.setLayoutManager(lm);
        holder.mName.setText(bean.getName());
        final HorizontalMenuAdapter adapter = new HorizontalMenuAdapter(mContext, bean.getList());
//        holder.mName.setBackgroundResource(R.drawable.shape_text_color);
//        holder.mName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        if (!bean.isClick()){
            holder.mName.setBackgroundResource(R.drawable.shape_text_color);
            holder.mName.setTextColor(mContext.getResources().getColor(R.color.color_fb487f));
        }else {
            Log.e("AA","--------------"+bean.toString());
            holder.mName.setTextColor(Color.rgb(0x92, 0x92, 0x92));
                                            holder.mName.setBackgroundResource(R.drawable.shape_text_bg);
                                        }
                adapter.setOnChildClickListener(new HorizontalMenuAdapter.AllOnClickListener() {
                    @Override
            public boolean isChildClick(boolean isClick) {
               if (isClick){
                   holder.mName.setTextColor(Color.rgb(0x92, 0x92, 0x92));
                   holder.mName.setBackgroundResource(R.drawable.shape_text_bg);
               }else {
                   holder.mName.setBackgroundResource(R.drawable.shape_text_color);
                   holder.mName.setTextColor(mContext.getResources().getColor(R.color.color_fb487f));
               }
               return false;
            }
        });


        holder.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null){
                    onClickListener.onTypaAll(bean);
                }
                holder.mName.setBackgroundResource(R.drawable.shape_text_color);
                holder.mName.setTextColor(mContext.getResources().getColor(R.color.color_fb487f));
                adapter.setIndex(-1);
                adapter.notifyDataSetChanged();


            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener<TabItemBean>() {
            @Override
            public void onItemClick(View view, TabItemBean tabItemBean, int i) {
                if (onClickListener != null){
                    onClickListener.onTypaOne(tabItemBean, bean);
                }
                holder.mName.setTextColor(Color.rgb(0x92, 0x92, 0x92));
                holder.mName.setBackgroundResource(R.drawable.shape_text_bg);
                adapter.setIndex(i);
                adapter.notifyDataSetChanged();

            }
        });
        holder.mTabList.setAdapter(adapter);
    }

    class Holder extends BaseViewHolder {
        @ViewIn(R.id.tab_name)
        private TextView mName;
        @ViewIn(R.id.tab_list)
        private RecyclerView mTabList;

        public Holder(View view) {
            super(view);
        }
    }

    OnClickListener onClickListener;


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onTypaAll(TypeBean bean);

        void onTypaOne(TabItemBean tabItemBean, TypeBean bean);


    }


}
