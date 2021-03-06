package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.SeacherActivity;
import com.wodm.android.ui.newview.NewUserInfoActivity;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/10/12.
 */

public class TaskAdapter extends BaseAdapter {
    private boolean isQiandao = false;
    private String url;
    private Context mContext;
    private String[] personArray = {"完善个人资料", "使用搜索功能", "查看一次资讯"};
    private String[] messageArray = {"每日签到", "观看动漫", "发评论"};
    private int[] personIconArray = {R.mipmap.com_user_info, R.mipmap.img_task_search, R.mipmap.look_news};
    /**
     * taskType;
     * /**任务类型(1：日常 2：新手 3：临时任务 4：临时活动)*/


    /**taskValue   0：非 1：签到经验 2：观看动漫 3：发评论 4：完善个人资料
     * 5：使用搜索功能 6：点击一次广告 7：查看一次新闻
     * 8：添加一个好友 9：点一次赞 10：打赏一次 11：敬请期待*/
    private int[] messageIconArray = {R.mipmap.day_qiandao, R.mipmap.watch_anim, R.mipmap.img_commnts};
    private static int i=0;
    public TaskAdapter(Context context) {
        this.mContext = context;
    }
    private QianDaoListener qianDaoListener;

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
        url = Constants.APP_GET_TASKSTATUS + CURRENT_USER.getData().getAccount().getId();
        TaskAdapter.Myadapter myadapter = null;
        if (position == 0) {
            myadapter = new TaskAdapter.Myadapter(personArray, personIconArray);
            holder.lv_task_adapter.setAdapter(myadapter);
            holder.textView.setText(mContext.getString(R.string.new_user_task));
            holder.adapter_view.setBackgroundColor(mContext.getResources().getColor(R.color.color_facd89));
        } else {
            myadapter = new TaskAdapter.Myadapter(messageArray, messageIconArray);
            holder.lv_task_adapter.setAdapter(myadapter);
            holder.textView.setText(mContext.getString(R.string.task_days));
            holder.adapter_view.setBackgroundColor(mContext.getResources().getColor(R.color.color_89c997));
        }


        return convertView;
    }

    private class Holder {
        TextView textView;
        NoScrollListView lv_task_adapter;
        View adapter_view;
    }

    private void getData(String url, final ImageView imageView, final TextView tv_value,final String value) {
        ((AppActivity) mContext).httpGet(url, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    JSONObject jsonObject = new JSONObject(obj.getString("data"));
                    int status=jsonObject.optInt("status");
                    handler.sendEmptyMessage(status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
        switch (msg.what){
            case 0:
                textview.setVisibility(View.VISIBLE);
                imageview.setVisibility(View.GONE);
                break;
            case 1:
                textview.setVisibility(View.GONE);
                imageview.setVisibility(View.VISIBLE);
                break;
        }
        }
    };
    private  boolean isPersion=false;
    private  boolean isSearch=false;
    private  boolean isNews=false;
    private  boolean isWatchVideo=false;
    private  boolean isComments=false;
    private TextView textview;
    private ImageView imageview;
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
                gvHolder.ll_item_task = (LinearLayout) convertView.findViewById(R.id.ll_item_task);
                convertView.setTag(gvHolder);
            } else {
                gvHolder = (TaskAdapter.GvHolder) convertView.getTag();
            }
            gvHolder.tv_key.setText(mArray[position]);
            gvHolder.tv_value.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            gvHolder.tv_value.setTextColor(mContext.getResources().getColor(R.color.color_ee984a));
            gvHolder.tv_value.setTextSize(14);
            textview=gvHolder.tv_value;
            imageview=gvHolder.img_right;
            String value = mArray[position];
            if (value.equals("完善个人资料")) {
                    String dataurl = url + "&taskType=2&taskValue=4";
                    getData(dataurl, gvHolder.img_right, gvHolder.tv_value,value);
                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, NewUserInfoActivity.class));
                    }
                });
            } else if (value.equals("使用搜索功能")) {
                if (!isSearch){
                    String dataurl = url + "&taskType=2&taskValue=5";
                    getData(dataurl, gvHolder.img_right, gvHolder.tv_value,value);
                    isSearch=true;
                 }

                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, SeacherActivity.class));
                    }
                });
//                gvHolder.img_right.setVisibility(View.VISIBLE);
            }
            else if (value.equals("查看一次资讯")) {
                if (!isNews){
                    String dataurl = url + "&taskType=2&taskValue=7";
                    getData(dataurl, gvHolder.img_right,  gvHolder.tv_value,value);
                    isNews=true;
                }
//                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mContext.startActivity(new Intent(mContext, Main2Activity.class));
//                    }
//                });
            } else if (value.equals("观看动漫")) {
                if (!isWatchVideo){
                    String dataurl = url + "&taskType=1&taskValue=2";
                    getData(dataurl, gvHolder.img_right, gvHolder.tv_value,value);
                    isWatchVideo=true;
                }
//                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mContext.startActivity(new Intent(mContext, Main2Activity.class));
//                    }
//                });
            } else if (value.equals("发评论")) {
                if (!isComments){
                    String dataurl = url + "&taskType=1&taskValue=3";
                    getData(dataurl, gvHolder.img_right, gvHolder.tv_value,value);
                    isComments=true;
                 }

            } else if (value.equals("每日签到")) {
//                String dataurl = url + "&taskType=1&taskValue=1";
//                getData(dataurl, gvHolder.img_right, gvHolder.tv_other_value, gvHolder.tv_value, value);
//                if (!isQiandao) {
//                    gvHolder.img_right.setVisibility(View.VISIBLE);
//                    gvHolder.tv_other_value.setVisibility(View.GONE);
//                    gvHolder.tv_value.setVisibility(View.GONE);
//                }
                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isQiandao) {
                            qianDaoListener.qiandao();
                        }
                    }
                });
            }

            gvHolder.img_icon.setBackgroundResource(miconArray[position]);
            return convertView;
        }

    }
    public void setQiandapListener(QianDaoListener qianDaoListener){
        this.qianDaoListener=qianDaoListener;
    }
    public interface QianDaoListener{
        public void qiandao();
    }

    public void setQiandaoType() {
        isQiandao = true;
        notifyDataSetChanged();
    }

    /**
     * 经验值来源(0：非 1：签到经验 2：观看动漫 3：发评论 4：完善个人资料 5：使用搜索功能 6：点击一次广告 7：查看一次新闻8：添加一个好友 9：点一次赞 10：打赏一次 11：敬请期待)
     */

    class GvHolder {
        TextView tv_key, tv_value;
        ImageView img_icon, img_right;
        RelativeLayout ll_adapter;
        LinearLayout ll_item_task;
    }
}