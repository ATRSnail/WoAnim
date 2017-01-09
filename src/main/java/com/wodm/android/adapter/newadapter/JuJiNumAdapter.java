package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.SeriesAdapter;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.ChapterPageBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.home.CartoonReadActivity;
import com.wodm.android.ui.newview.DetailActivity;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchenyu on 16/12/12.
 * 动漫画详情页面 所有目录的适配
 */

public class JuJiNumAdapter extends BaseAdapter {
    private Context mContext;
    private int clickPosition=0;
    private int lianzai=1;//是否连载的标识  1 连载   2 全本
    private int total=1;//横向目录此时位置的最大集数
    private int index=0;//横向目录此时位置
    private int num=106;//总集数
    private int size;
    private int resourceType=2;
    private int resourceId = -1;
    private ObjectBean bean = null;
    DetailActivity detailActivity = new DetailActivity();
    Intent intent;
    private  ArrayList<ChapterBean> mChapterList;
    public JuJiNumAdapter(Context context, ObjectBean bean,int resourceType,int resourceId){
        this.mContext=context;
        this.resourceId = resourceId;
        this.bean=bean;
        this.num=bean.getChapter();
        this.lianzai=bean.getType();
        size=((num%16==0)?(num/16):(num/16+1));
        this.resourceType=resourceType;
    }
    public JuJiNumAdapter(){
    }


    public  void setmChapterList(ArrayList<ChapterBean> mChapterList) {
      this.mChapterList = mChapterList;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getCount() {
//        if (lianzai==1){
            //连载-倒序展示 ---第一页展示个数
//            total=(size-index)*16;
//            if(num<total&index==0){
//                return (16+num-total);
//            }
//        }else {
//            //完结-顺序展示----最后一页展示个数
//            total=(index+1)*16;
//            if(num<total&index==(size-1)){
//                return (16+num-total);
//            }
//        }
          total=(index+1)*16;
          if(num<total&index==(size-1)){
              return (16+num-total);
          }
        return 16;
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
            holder.imageView= (ImageView) convertView.findViewById(R.id.new_juji);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }


        holder.imageView.setVisibility(View.GONE);
        if (lianzai==1){
            //连载-倒序展示
//            total=(size-index)*16;
            total=num-index*16;
            if (index==0){
               total=num;
                if (position==0){
                    //显示最新图标
                    holder.imageView.setVisibility(View.VISIBLE);
                }
            }
            holder.btn_jujinum.setText(String.valueOf(total-position));
        }else {
            //完结-顺序展示
            total=(index+1)*16;
            holder.btn_jujinum.setText(String.valueOf(total-15+position));
//            if (index==(size-1)&(total-15+position)==num){
//                //显示最新图标
//                holder.imageView.setVisibility(View.VISIBLE);
//            }
        }


//        if (clickPosition==position){
//            holder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_click);
//            holder.btn_jujinum.setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
//        }else {
//            holder.btn_jujinum.setTextColor(mContext.getResources().getColor(R.color.color_333333));
//            holder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_normal);
//        }
        final Holder finalHolder = holder;
        holder.btn_jujinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str=finalHolder.btn_jujinum.getText().toString();
                int  jujinum;
                if (lianzai==1){
                    jujinum=  num-Integer.valueOf(str)-1;
                }else {
                    jujinum= Integer.valueOf(str)-1;
                }
                  detailActivity.startRead(jujinum);
//                clickPosition=position;
//                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
        notifyDataSetChanged();
    }


    class Holder{
        Button btn_jujinum;
        ImageView imageView;
    }
}
