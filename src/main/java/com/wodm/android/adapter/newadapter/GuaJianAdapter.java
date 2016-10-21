package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.view.newview.MyGridView;

/**
 * Created by songchenyu on 16/10/21.
 */

public class GuaJianAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context mContext;
    private int imageRescoures[]={R.mipmap.guajian_xiong,R.mipmap.guajian_wukong,R.mipmap.guajian_lvmaozi};
    private String strRescoures[]={"熊仔头像框","悟空头像框","绿帽子头像框"};
    private String clickStr;
    private MyGridView mGirdview;

    public GuaJianAdapter(MyGridView girdview,Context context){
        this.mContext=context;
        this.mGirdview=girdview;
        mGirdview.setOnItemClickListener(this);

    }
    @Override
    public int getCount() {
        return imageRescoures.length;
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
        Holder holder=null;
        if (convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_guajian_free,null,false);
            holder.img_icon= (ImageView) convertView.findViewById(R.id.img_icon);
            holder.img_guajian_kuang= (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        if (clickStr!=null&&clickStr.equals(strRescoures[position])){
            holder.img_guajian_kuang.setVisibility(View.VISIBLE);
        }else {
            holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
        }

        holder.img_icon.setBackgroundResource(imageRescoures[position]);
        holder.tv_name.setText(strRescoures[position]);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clickStr=strRescoures[position];
        notifyDataSetChanged();
    }

    class Holder{
        ImageView img_icon,img_guajian_kuang;
        TextView tv_name;
    }
}
