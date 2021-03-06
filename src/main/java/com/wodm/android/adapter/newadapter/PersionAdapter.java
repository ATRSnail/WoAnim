package com.wodm.android.adapter.newadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.newview.AnotherActivity;
import com.wodm.android.ui.newview.PersionActivity;
import com.wodm.android.ui.user.RecordActivity;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

import static com.wodm.R.id.rl_comments;
import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/10/8.
 */

public class PersionAdapter extends BaseAdapter {
    private Context mContext;
    boolean mAnotherInfo=false;
    long userId;
    int animationCount;
    int cartoonCount;
    public PersionAdapter(Context context){
        this.mContext=context;
    }
    public PersionAdapter(Context context, boolean b, long mUserId, int mAnimationCount, int mCartoonCount){
        this.mContext=context;
        this.mAnotherInfo=b;
        this.userId=mUserId;
        this.animationCount = mAnimationCount;
        this.cartoonCount = mCartoonCount;
    }
    @Override
    public int getCount() {
        return 2;
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_comments,null,false);
            holder.rl_comments= (RelativeLayout) convertView.findViewById(R.id.rl_comments);
            holder.textView= (Button) convertView.findViewById(R.id.btn_num);
            holder.gridView= (MyGridView) convertView.findViewById(R.id.gv_item_comments);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        if (position==0){
            holder.rl_comments.setBackgroundResource(R.mipmap.user_comment_anim);
        }else {
            holder.rl_comments.setBackgroundResource(R.mipmap.user_comment_manhua);
        }
        holder.rl_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("tid", R.id.my_collcet);
                i.putExtra("title", R.string.collect);
                if (position==0){
                    i.putExtra("position","动画");
                }else if (position==1){
                    i.putExtra("position","漫画");
                }
                i.setClass(mContext, RecordActivity.class);
                if(mAnotherInfo){
                    i.putExtra("userId",userId);
                    i.setClass(mContext, AnotherActivity.class);
                }
                mContext.startActivity(i);
            }
        });
        holder.textView.setText(position == 0? CURRENT_USER.getData().getAnimationCount()+""
                :""+CURRENT_USER.getData().getCartoonCount());
        if(mAnotherInfo){
            holder.textView.setText(position == 0? animationCount+""
                    :""+cartoonCount);
        }
        return convertView;
    }
    private class Holder{
        Button textView;
        MyGridView gridView;
        RelativeLayout rl_comments;
    }
    class Myadapter extends BaseAdapter{
        private String mArray[];
        private Myadapter(String array[]){
            this.mArray=array;
        }

        @Override
        public int getCount() {
            return mArray.length;
        }

        @Override
        public Object getItem(int position) {
            return mArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GvHolder gvHolder=null;
            if (convertView==null){
                gvHolder=new GvHolder();
                convertView=LayoutInflater.from(mContext).inflate(R.layout.adapter_gv,null,false);
                gvHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                gvHolder.img_icon= (ImageView) convertView.findViewById(R.id.gv_img);
                convertView.setTag(gvHolder);
            }else {
                gvHolder= (GvHolder) convertView.getTag();
            }
            gvHolder.tv_name.setText(mArray[position]);
            return convertView;
        }

    }
    class GvHolder{
        TextView tv_name;
        ImageView img_icon;
    }
}
