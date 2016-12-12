package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.wodm.R;

/**
 * Created by songchenyu on 16/12/12.
 */

public class JuJiNumAdapter extends BaseAdapter {
    private Context mContext;
    private int clickPosition=0;
    public JuJiNumAdapter(Context context){
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_juji_num,null,false);
            holder.btn_jujinum= (Button) convertView.findViewById(R.id.btn_jujinum);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        if (clickPosition==position){
            holder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_click);
            holder.btn_jujinum.setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
        }else {
            holder.btn_jujinum.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            holder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_normal);
        }
        holder.btn_jujinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition=position;
                notifyDataSetChanged();
            }
        });
//        final Holder finalHolder = holder;
//        holder.btn_jujinum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finalHolder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_click);
//            }
//        });
        return convertView;
    }
    class Holder{
        Button btn_jujinum;
    }
}
