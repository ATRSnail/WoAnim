package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.wodm.R;
import com.wodm.android.bean.ObjectBean;

/**
 * Created by songchenyu on 16/12/12.
 * 动漫画详情页面 横向目录的适配
 */

public class JuJiAdapter extends BaseAdapter {
    private Context mContext;
    private int clickPosition=0;
    private int num=106;//总集数
    private int lianzai=1;//是否连载的标识  1 连载   2 全本
   public  int total;//当前空格最大的集数
    private int size;
    private ObjectBean bean = null;
   UpdateTotal updateTotal;

    public void setUpdateTotal(UpdateTotal updateTotal) {
        this.updateTotal = updateTotal;
    }

    public JuJiAdapter(Context context, ObjectBean bean){
        this.mContext=context;
        this.bean = bean;
        this.num=bean.getChapter();
        this.lianzai=bean.getType();
        size=((num%16==0)?(num/16):(num/16+1));
    }
    @Override
    public int getCount() {
        return size;
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

        if (lianzai==1){
            //连载-倒序展示
            total=num-position*16;
            holder.btn_juji.setText(total+"-"+(total-15));
            if (position==(size-1)){
                holder.btn_juji.setText(total+"-"+1);
            }
//            total=(size-position)*16;
//            holder.btn_juji.setText(total+"-"+(total-15));
//            if (position==0){
//                holder.btn_juji.setText(num+"-"+(total-15));
//            }

        }else {
          //完结-顺序展示
            total=(position+1)*16;
            holder.btn_juji.setText((total-15)+"-"+total);
            if (position==(size-1)){
                holder.btn_juji.setText((total-15)+"-"+num);
            }
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
                updateTotal.getTotal(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    class Holder{
        Button btn_juji;
    }
    public interface UpdateTotal{
       public void getTotal(int total);
    }
}
