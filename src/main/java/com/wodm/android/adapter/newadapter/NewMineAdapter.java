package com.wodm.android.adapter.newadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.newview.CustomerServiceActivity;
import com.wodm.android.ui.newview.MallActivity;
import com.wodm.android.ui.newview.MyMedalActivity;
import com.wodm.android.ui.newview.TaskActivity;
import com.wodm.android.ui.user.RecordActivity;
import com.wodm.android.ui.user.UsSetActivity;
import com.wodm.android.ui.user.UserCacheActivity;
import com.wodm.android.ui.user.UserIntegralActivity;
import com.wodm.android.ui.user.UserMessageActivity;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.track.Tracker;

import static com.wodm.R.id.adapter_view;

/**
 * Created by songchenyu on 16/9/26.
 */

public class NewMineAdapter extends BaseAdapter {
    private Context mContext;
    private String[] personArray = {"成就", "任务", "足迹", "收藏", "客服", "设置", "商城", ""};
    private String[] messageArray = {"回复", "点赞", "系统通知", "@我的", "话题", "", "", ""};
    private int[] personIconArray = {R.mipmap.medal_mine, R.mipmap.task_mine, R.mipmap.footprint_mine
            , R.mipmap.collect_mine, R.mipmap.service_mine, R.mipmap.setting_mine, R.mipmap.mall_mine, 0};

    private int[] messageIconArray = {R.mipmap.reply_mine, R.mipmap.like_mine, R.mipmap.inform_mine
            , R.mipmap.my_mine, R.mipmap.topic_mine, 0, 0, 0};

    public NewMineAdapter(Context context) {
        this.mContext = context;
    }

//    @Override
//    public int getCount() {
//        return 2;
//    }

    @Override
    public int getCount() {
        return 1;
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
        Holder holder = null;
        final int myPosition = position;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mine_adapter, null, false);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_title);
            holder.gridView = (MyGridView) convertView.findViewById(R.id.gv_mygirdview);
            holder.adapter_view = convertView.findViewById(adapter_view);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (myPosition == 0) {
                    getIntent(personArray[position]);
//                   Toast.makeText(mContext, ""+personArray[position], Toast.LENGTH_SHORT).show();
                } else {
                    getIntent(messageArray[position]);
//                   Toast.makeText(mContext, ""+messageArray[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
        Myadapter myadapter = null;
        if (position == 0) {
            myadapter = new Myadapter(personArray, personIconArray);
            holder.textView.setText(mContext.getString(R.string.center_people));
            holder.adapter_view.setBackgroundColor(mContext.getResources().getColor(R.color.color_cce198));
        } else {
            myadapter = new Myadapter(messageArray, messageIconArray);
            holder.textView.setText(mContext.getString(R.string.message));
            holder.adapter_view.setBackgroundColor(mContext.getResources().getColor(R.color.color_facd89));
        }
        if (myadapter != null)
            holder.gridView.setAdapter(myadapter);

        return convertView;
    }

    private class Holder {
        TextView textView;
        GridView gridView;
        View adapter_view;
    }

    private void getIntent(String text) {
        Tracker.getInstance(mContext).trackMethodInvoke("我的", "跳转" + text + "界面");
        if (!UpdataUserInfo.isLogIn(mContext, true) && !text.equals("设置")) {
            Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent();
        if (text.equals("@我的")) {
            mContext.startActivity(new Intent(mContext, UserMessageActivity.class));
        } else if (text.equals("收藏")) {
            i.putExtra("tid", R.id.my_collcet);
            i.putExtra("title", R.string.collect);
            startIntent(i, RecordActivity.class);
        } else if (text.equals("积分")) {
//           mContext.startActivity(new Intent(mContext, UserIntegralActivity.class));
            startIntent(null, UserIntegralActivity.class);
        } else if (text.equals("设置")) {
//           mContext.startActivity(new Intent(mContext, UsSetActivity.class));
            startIntent(null, UsSetActivity.class);
        } else if (text.equals("成就")) {
//           mContext.startActivity(new Intent(mContext, UsSetActivity.class));
            startIntent(null, MyMedalActivity.class);
        } else if (text.equals("足迹")) {
            i.putExtra("tid", R.id.watch_records);
            i.putExtra("title", R.string.wathc_recoder);
//           mContext.startActivity(new Intent(mContext, UsSetActivity.class));
            startIntent(i, RecordActivity.class);
        } else if (text.equals("缓存")) {
//           Intent i = new Intent(getActivity(), RecordActivity.class);
            i.putExtra("tid", R.id.watch_records);
            i.putExtra("title", R.string.wathc_recoder);
            startIntent(i, UserCacheActivity.class);
//           startActivity(i);
        } else if (text.equals("任务")) {
//           Intent i = new Intent(getActivity(), RecordActivity.class);
            startIntent(null, TaskActivity.class);
//           startActivity(i);
        } else if (text.equals("客服")) {
            startIntent(null, CustomerServiceActivity.class);
        } else if (text.equals("商城")) {
            startIntent(null, MallActivity.class);
        } else {
            Toast.makeText(mContext, "此功能暂未开通,敬请期待!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startIntent(Intent intent, Class classs) {
        if (intent == null)
            intent = new Intent();
        intent.setClass(mContext, classs);
        mContext.startActivity(intent);
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
            GvHolder gvHolder = null;
            if (convertView == null) {
                gvHolder = new GvHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_gv, null, false);
                gvHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                gvHolder.img_icon = (ImageView) convertView.findViewById(R.id.gv_img);
                gvHolder.adapter_view = convertView.findViewById(adapter_view);
                gvHolder.ll_adapter = (LinearLayout) convertView.findViewById(R.id.ll_adapter);
                convertView.setTag(gvHolder);
            } else {
                gvHolder = (GvHolder) convertView.getTag();
            }
            gvHolder.tv_name.setText(mArray[position]);
            if (miconArray[position] != 0) {
                gvHolder.img_icon.setBackgroundResource(miconArray[position]);
            }
            int screenWidth = Tools.getScreenWidth((Activity) mContext);
            int width = screenWidth / 4;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            gvHolder.ll_adapter.setLayoutParams(params);
            return convertView;
        }

    }

    class GvHolder {
        TextView tv_name;
        ImageView img_icon;
        LinearLayout ll_adapter;
        View adapter_view;
    }
}
