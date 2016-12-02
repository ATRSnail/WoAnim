package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.adapter.BaseViewHolder;
import com.wodm.android.bean.DianZanBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/2
 */
public class DianZanAdapter extends BaseAdapter {

    private List<DianZanBean> list = new ArrayList<>();
    private List<DianZanBean> delList = new ArrayList<>();
    private Context context;
    private boolean isToDel = false;

    public DianZanAdapter(Context context, List<DianZanBean> list) {
        this.context = context;
        this.list = list;
    }

    public boolean isToDel() {
        return isToDel;
    }

    public void setToDel(boolean toDel) {
        isToDel = toDel;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_zan, parent, false);
        }
        TextView name = BaseViewHolder.get(convertView, R.id.name_atwo);
        TextView commentTv = BaseViewHolder.get(convertView,R.id.tv_comment);
        CheckBox cBox = BaseViewHolder.get(convertView, R.id.cb_select);
        ImageView replyImg = BaseViewHolder.get(convertView, R.id.reply_atwo);
        cBox.setChecked(false);
        cBox.setVisibility(isToDel ? View.VISIBLE : View.GONE);
        replyImg.setVisibility(isToDel ? View.GONE : View.VISIBLE);

        name.setText(list.get(position).getName());

        cBox.setOnCheckedChangeListener(new onChecked(list.get(position)));

        return convertView;
    }

    public void delSome(){
        if (delList.size() == 0)return;
        for (DianZanBean bean:delList){
            if (list.contains(bean))
                list.remove(bean);
        }
        notifyDataSetChanged();
    }

    class onChecked implements CompoundButton.OnCheckedChangeListener {

        DianZanBean dianZanBean;

        public onChecked(DianZanBean dianZanBean) {
            this.dianZanBean = dianZanBean;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                delList.add(dianZanBean);
            }else {
                delList.remove(dianZanBean);
            }
        }
    }
}
