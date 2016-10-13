package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.view.newview.NoScrollListView;

/**
 * Created by songchenyu on 16/10/12.
 */

public class TaskAdapter extends BaseAdapter {
    private Context mContext;
    private String[] personArray = {"完善个人资料", "使用搜索功能", "查看一次资讯"};
    private String[] messageArray = {"每日签到", "观看动漫", "发评论"};
    private int[] personIconArray = {R.mipmap.com_user_info, R.mipmap.img_task_search, R.mipmap.look_news};

    private int[] messageIconArray = {R.mipmap.day_qiandao, R.mipmap.watch_anim, R.mipmap.img_commnts};

    public TaskAdapter(Context context) {
        this.mContext = context;
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
        TaskAdapter.Holder holder = null;
        final int myPosition = position;
        if (convertView == null) {
            holder = new TaskAdapter.Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_task, null, false);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_title);
            holder.lv_task_adapter = (NoScrollListView) convertView.findViewById(R.id.lv_task_adapter);
            holder.adapter_view = convertView.findViewById(R.id.adapter_view);
            convertView.setTag(holder);
        } else {
            holder = (TaskAdapter.Holder) convertView.getTag();
        }
        TaskAdapter.Myadapter myadapter = null;
        if (position == 0) {
            myadapter = new TaskAdapter.Myadapter(personArray, personIconArray);
            holder.textView.setText(mContext.getString(R.string.new_user_task));
            holder.adapter_view.setBackgroundColor(mContext.getResources().getColor(R.color.color_facd89));
        } else {
            myadapter = new TaskAdapter.Myadapter(messageArray, messageIconArray);
            holder.textView.setText(mContext.getString(R.string.task_days));
            holder.adapter_view.setBackgroundColor(mContext.getResources().getColor(R.color.color_89c997));
        }
        if (myadapter != null)
            holder.lv_task_adapter.setAdapter(myadapter);

        return convertView;
    }

    private class Holder {
        TextView textView;
        NoScrollListView lv_task_adapter;
        View adapter_view;
    }


    class Myadapter extends BaseAdapter {
        private String mArray[];
        private int miconArray[];

        private Myadapter(String array[], int[] iconArray) {
            this.mArray = array;
            this.miconArray = iconArray;
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
            TaskAdapter.GvHolder gvHolder = null;
            if (convertView == null) {
                gvHolder = new TaskAdapter.GvHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task, null, false);
                gvHolder.tv_key = (TextView) convertView.findViewById(R.id.tv_task_item_key);
                gvHolder.tv_value = (TextView) convertView.findViewById(R.id.tv_task_item_value);
                gvHolder.img_icon = (ImageView) convertView.findViewById(R.id.img_task_item);
                gvHolder.img_right = (ImageView) convertView.findViewById(R.id.img_right);
                gvHolder.ll_adapter = (RelativeLayout) convertView.findViewById(R.id.ll_task_adapter);
                convertView.setTag(gvHolder);
            } else {
                gvHolder = (TaskAdapter.GvHolder) convertView.getTag();
            }
            gvHolder.tv_key.setText(mArray[position]);
            if (mArray[position].equals("完善个人资料")){
                gvHolder.tv_value.setVisibility(View.VISIBLE);
                gvHolder.img_right.setVisibility(View.GONE);
                gvHolder.tv_value.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                gvHolder.tv_value.setTextColor(mContext.getResources().getColor(R.color.color_ee984a));
                gvHolder.tv_value.setTextSize(14);
            }else if (mArray[position].equals("使用搜索功能")){
                gvHolder.img_right.setVisibility(View.VISIBLE);
            }else {
                gvHolder.img_right.setVisibility(View.GONE);
                gvHolder.tv_value.setVisibility(View.VISIBLE);
            }
//
            gvHolder.img_icon.setBackgroundResource(miconArray[position]);
            return convertView;
        }

    }

    class GvHolder {
        TextView tv_key,tv_value;
        ImageView img_icon,img_right;
        RelativeLayout ll_adapter;
    }
}