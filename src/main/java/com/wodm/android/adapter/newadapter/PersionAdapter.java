package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.view.newview.MyGridView;

/**
 * Created by songchenyu on 16/10/8.
 */

public class PersionAdapter extends BaseAdapter {
    private Context mContext;
    public PersionAdapter(Context context){
        this.mContext=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_comments,null,false);
            holder.textView= (Button) convertView.findViewById(R.id.btn_num);
            holder.gridView= (MyGridView) convertView.findViewById(R.id.gv_item_comments);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        holder.textView.setText("0");


        return convertView;
    }
    private class Holder{
        Button textView;
        MyGridView gridView;
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
