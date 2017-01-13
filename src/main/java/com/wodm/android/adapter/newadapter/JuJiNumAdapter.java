package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.wodm.R;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.tools.JujiDbTools;
import com.wodm.android.ui.home.CartoonReadActivity;
import com.wodm.android.ui.newview.DetailActivity;

import com.wodm.android.fragment.newfragment.MuluFragment;

import java.util.ArrayList;

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

    private     ArrayList<ChapterBean> mChapterList =new ArrayList<>();
    private MuluFragment.onJiShuNumClickListener listener;
    public JuJiNumAdapter(Context context, ObjectBean bean,int resourceType,int resourceId,MuluFragment.onJiShuNumClickListener listener){
        this.mContext=context;
        this.resourceId = resourceId;
        this.bean=bean;
        this.num=bean.getChapter();
        this.lianzai=bean.getType();
        this.listener=listener;
        size=((num%16==0)?(num/16):(num/16+1));
        this.resourceType=resourceType;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<ChapterBean> getmChapterList() {
        return mChapterList;
    }

    public void setmChapterList(ArrayList<ChapterBean> mChapterList) {
        this.mChapterList.clear();
        this.mChapterList.addAll(mChapterList);
        notifyDataSetChanged();
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
//           if (!(mChapterList!=null&&mChapterList.size()>0)){
//               return 0;
//           }
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
        final Holder finalHolder = holder;
        if (getmChapterList()!=null&getmChapterList().size()>position ){
            ArrayList<ChapterBean> chapter = getmChapterList();
            ChapterBean chapterBean  =   getmChapterList().get(position);

            if (chapterBean.getIsWatch()==0){
                finalHolder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_normal);
                finalHolder.btn_jujinum.setTextColor(Color.parseColor("#333333"));
            }else {
                finalHolder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_watch);
                finalHolder.btn_jujinum.setTextColor(Color.parseColor("#999999"));
            }
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

        holder.btn_jujinum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN :
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        finalHolder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_click);
                        finalHolder.btn_jujinum.setTextColor(Color.parseColor("#ffffff"));
                        break;
                    case MotionEvent.ACTION_BUTTON_RELEASE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        finalHolder.btn_jujinum.setBackgroundResource(R.drawable.btn_juji_num_watch);
                        finalHolder.btn_jujinum.setTextColor(Color.parseColor("#999999"));
                        break;

                }
                return false;
            }
        });


        holder.btn_jujinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String str=finalHolder.btn_jujinum.getText().toString();
                    int  jujinum=  Integer.valueOf(str);

                    listener.clickNum(position,jujinum,index);


//                clickPosition=position;
//                notifyDataSetChanged();
            }
        });
        return convertView;
    }


    class Holder{
        Button btn_jujinum;
        ImageView imageView;
    }
}
