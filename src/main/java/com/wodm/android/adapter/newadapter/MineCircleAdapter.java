package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;

/**
 * Created by songchenyu on 16/10/13.
 */

public class MineCircleAdapter extends BaseAdapter {
    private String[] messageArray = {"宅腐", "达人", "热血", "马猴","宅腐", "达人", "热血", "马猴"};
    private Context mContext;
    public MineCircleAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return messageArray.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        MineCircleAdapter.GvHolder gvHolder=null;
        if (convertView==null){
            gvHolder=new MineCircleAdapter.GvHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_mine_circle,null,false);
            gvHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            gvHolder.img_icon= (ImageView) convertView.findViewById(R.id.gv_img);
            convertView.setTag(gvHolder);
        }else {
            gvHolder= (MineCircleAdapter.GvHolder) convertView.getTag();
        }
        gvHolder.tv_name.setText(messageArray[position]);
        return convertView;
    }
    class GvHolder{
        TextView tv_name;
        ImageView img_icon;
    }
}
