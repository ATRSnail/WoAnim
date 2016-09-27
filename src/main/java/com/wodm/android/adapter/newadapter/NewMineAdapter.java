package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.view.newview.MyGridView;

/**
 * Created by songchenyu on 16/9/26.
 */

public class NewMineAdapter extends BaseAdapter {
    private Context mContext;
    private String [] personArray={"客服","足迹","任务","勋章","收藏","缓存","设置"};
    private String [] messageArray={"回复","点赞","系统通知","勋章","@我的","话题"};
    public NewMineAdapter(Context context){
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
        final int myPosition=position;
        if (convertView==null){
            holder=new Holder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.mine_adapter,null,false);
            holder.textView= (TextView) convertView.findViewById(R.id.tv_title);
            holder.gridView= (MyGridView) convertView.findViewById(R.id.gv_mygirdview);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (myPosition==0){
                   Toast.makeText(mContext, ""+personArray[position], Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(mContext, ""+messageArray[position], Toast.LENGTH_SHORT).show();
               }
            }
        });
        Myadapter myadapter=null;
        if (position==0){
            myadapter=new Myadapter(personArray);
            holder.textView.setText(mContext.getString(R.string.center_people));
        }else {
            myadapter=new Myadapter(messageArray);
            holder.textView.setText(mContext.getString(R.string.message));
        }
        if (myadapter!=null)
         holder.gridView.setAdapter(myadapter);

        return convertView;
    }
    private class Holder{
         TextView textView;
         GridView gridView;
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
