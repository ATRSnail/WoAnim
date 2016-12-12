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

public class JuJiAdapter extends BaseAdapter {
    private Context mContext;
    private int clickPosition=0;
    public JuJiAdapter(Context context){
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_juji,null,false);
            holder.btn_juji= (Button) convertView.findViewById(R.id.btn_juji);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        if (clickPosition==position){
            holder.btn_juji.setBackgroundResource(R.drawable.btn_juji_onclick);
            holder.btn_juji.setTextColor(mContext.getResources().getColor(R.color.color_fb487f));
        }else {
            holder.btn_juji.setTextColor(mContext.getResources().getColor(R.color.color_cccccc));
            holder.btn_juji.setBackgroundResource(R.drawable.btn_juji_normal);
        }
        holder.btn_juji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition=position;
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    class Holder{
        Button btn_juji;
    }
}
