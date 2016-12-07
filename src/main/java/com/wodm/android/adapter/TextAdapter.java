package com.wodm.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wodm.R;
import com.wodm.android.utils.Preferences;

import java.util.ArrayList;

import static com.wodm.R.id.img_select_color;

/**
 * Created by songchenyu on 16/12/1.
 */

public class TextAdapter extends BaseAdapter{
    private Context mContext;
    private int clickPosition=0;
    private ArrayList<String> listColor;
    private setColorListener listener;
    public TextAdapter(Context context, ArrayList<String> listdata){
         this.mContext=context;
         this.listColor=listdata;
    }
    @Override
    public int getCount() {
        return listColor.size();
    }

    @Override
    public Object getItem(int position) {
        return listColor.get(position);
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
            holder.img_select_color= (ImageView) convertView.findViewById(img_select_color);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        final Holder finalHolder = holder;
        initColor(finalHolder.img_select_color,position);
        clickPosition=Preferences.getInstance(mContext).getPreference("bulletcolor",clickPosition);
        if (position==clickPosition){
            initPressedColor(finalHolder.img_select_color);
        }
        holder.img_select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition=position;
                Preferences.getInstance(mContext).setPreference("bulletcolor", clickPosition);
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
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet1_pressed);
        }else if (clickPosition==1){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet2_pressed);
        }else if (clickPosition==2){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet3_pressed);
        }else if (clickPosition==3){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet4_pressed);
        }else if (clickPosition==4){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet5_pressed);
        }else if (clickPosition==5){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet6_pressed);
        }else if (clickPosition==6){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet7_pressed);
        }
        if (listener!=null){
//            int color= Color.parseColor(listColor.get(clickPosition));
            listener.initColor(clickPosition);
        }
    }
    public interface setColorListener{
        public void initColor(int position);
    }
    public void setTextColorListener(setColorListener listener){
            this.listener=listener;
    }
    private void initColor(ImageView img_select_color,int position){
        if (position==0){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet1_normal);
        }else if (position==1){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet2_normal);
        }else if (position==2){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet3_normal);
        }else if (position==3){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet4_normal);
        }else if (position==4){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet5_normal);
        }else if (position==5){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet6_normal);
        }else if (position==6){
            img_select_color.setBackgroundResource(R.drawable.btn_border_bullet7_normal);
        }
    }
}
