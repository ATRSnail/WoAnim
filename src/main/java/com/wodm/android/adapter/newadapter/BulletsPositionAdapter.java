package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wodm.R;
import com.wodm.android.utils.Preferences;

/**
 * Created by songchenyu on 16/12/1.
 */

public class BulletsPositionAdapter extends BaseAdapter {
    private Context mContext;
    private int clickPosition=0;
    private setPositionListener listener;
    public BulletsPositionAdapter(Context context){
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return 3;
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.color_select,null,false);
            holder.img_select_color= (ImageView) convertView.findViewById(R.id.img_select_color);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        final Holder finalHolder = holder;
        initColor(finalHolder.img_select_color,position);
        clickPosition=Preferences.getInstance(mContext).getPreference("bulletsposition",clickPosition);
        if (position==clickPosition){
            initPressedColor(finalHolder.img_select_color);
        }

        holder.img_select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition=position;
                Preferences.getInstance(mContext).setPreference("bulletsposition", clickPosition);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
    class Holder{
        ImageView img_select_color;
    }
    private void initPressedColor(ImageView img_select_color){
        if (clickPosition==0){
            img_select_color.setBackgroundResource(R.mipmap.img_danmu_top_press);
        }else if (clickPosition==1){
            img_select_color.setBackgroundResource(R.mipmap.img_danmu_mi_press);
        }else if (clickPosition==2){
            img_select_color.setBackgroundResource(R.mipmap.img_danmu_bottom_press);
        }
        if (listener!=null){
            listener.initPosition(clickPosition);
        }
    }
    public interface setPositionListener{
        public void initPosition(int position);
    }
    public void setPositionListener(setPositionListener listener){
        this.listener=listener;
    }
    private void initColor(ImageView img_select_color,int position){
        if (position==0){
            img_select_color.setBackgroundResource(R.mipmap.img_danmu_top_normal);
        }else if (position==1){
            img_select_color.setBackgroundResource(R.mipmap.img_danmu_mi_normal);
        }else if (position==2){
            img_select_color.setBackgroundResource(R.mipmap.img_danmu_bottom_normal);
        }
}
}

