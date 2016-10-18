package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
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
import com.wodm.android.tools.DisplayUtil;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.SeacherActivity;
import com.wodm.android.ui.newview.NewUserInfoActivity;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wodm.R.id.tv_other_value;
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
        url = Constants.APP_GET_TASKSTATUS + CURRENT_USER.getData().getAccount().getId();
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

    private void getData(String url, final ImageView imageView, final TextView tv_other_value, final TextView tv_value, final String value) {
        ((AppActivity) mContext).httpGet(url, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    JSONObject jsonObject = new JSONObject(obj.getString("data"));
                    if (jsonObject.optInt("status") == 1) {
                        imageView.setVisibility(View.VISIBLE);
                        tv_other_value.setVisibility(View.GONE);
                        tv_value.setVisibility(View.GONE);
                    } else {
                        if (value.equals("完善个人资料")) {
                            imageView.setVisibility(View.GONE);
                            tv_other_value.setVisibility(View.GONE);
                            tv_value.setVisibility(View.VISIBLE);
                        } else {
                            imageView.setVisibility(View.GONE);
                            tv_other_value.setVisibility(View.VISIBLE);
                            tv_value.setVisibility(View.INVISIBLE);
                        }

                    }
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
                gvHolder.tv_other_value = (TextView) convertView.findViewById(R.id.tv_other_value);
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
            gvHolder.tv_other_value.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            gvHolder.tv_other_value.setTextColor(mContext.getResources().getColor(R.color.color_ee984a));
            gvHolder.tv_other_value.setTextSize(14);
            String value = mArray[position];
            if (value.equals("完善个人资料")) {
                String dataurl = url + "&taskType=1&taskValue=4";
                getData(dataurl, gvHolder.img_right, gvHolder.tv_other_value, gvHolder.tv_value, value);
                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, NewUserInfoActivity.class));
                    }
                });
//                gvHolder.tv_other_value.setVisibility(View.GONE);
//                gvHolder.tv_value.setVisibility(View.VISIBLE);
//                gvHolder.img_right.setVisibility(View.GONE);
            } else if (value.equals("使用搜索功能")) {
                String dataurl = url + "&taskType=1&taskValue=5";
                getData(dataurl, gvHolder.img_right, gvHolder.tv_other_value, gvHolder.tv_value, value);
                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, SeacherActivity.class));
                    }
                });
//                gvHolder.img_right.setVisibility(View.VISIBLE);
            } else if (value.equals("查看一次资讯")) {
                String dataurl = url + "&taskType=1&taskValue=7";
                getData(dataurl, gvHolder.img_right, gvHolder.tv_other_value, gvHolder.tv_value, value);
//                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mContext.startActivity(new Intent(mContext, Main2Activity.class));
//                    }
//                });
            } else if (value.equals("观看动漫")) {
                String dataurl = url + "&taskType=1&taskValue=2";
                getData(dataurl, gvHolder.img_right, gvHolder.tv_other_value, gvHolder.tv_value, value);
//                gvHolder.ll_item_task.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mContext.startActivity(new Intent(mContext, Main2Activity.class));
//                    }
//                });
            } else if (value.equals("发评论")) {
                String dataurl = url + "&taskType=1&taskValue=3";
                getData(dataurl, gvHolder.img_right, gvHolder.tv_other_value, gvHolder.tv_value, value);
            } else if (value.equals("每日签到")) {
                if (isQiandao) {
                    gvHolder.img_right.setVisibility(View.VISIBLE);
                    gvHolder.tv_other_value.setVisibility(View.GONE);
                    gvHolder.tv_value.setVisibility(View.GONE);
                }
            } else {
                gvHolder.tv_other_value.setVisibility(View.VISIBLE);
                gvHolder.img_right.setVisibility(View.GONE);
                gvHolder.tv_value.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                int margins = DisplayUtil.px2dip(mContext, 240);
                params.setMargins(0, 0, margins, 0);
                gvHolder.tv_other_value.setLayoutParams(params);
            }

            gvHolder.img_icon.setBackgroundResource(miconArray[position]);
            return convertView;
        }

    }

    public void setQiandaoType() {
        isQiandao = true;
        notifyDataSetChanged();
    }

    /**
     * 经验值来源(0：非 1：签到经验 2：观看动漫 3：发评论 4：完善个人资料 5：使用搜索功能 6：点击一次广告 7：查看一次新闻8：添加一个好友 9：点一次赞 10：打赏一次 11：敬请期待)
     */

    class GvHolder {
        TextView tv_key, tv_value, tv_other_value;
        ImageView img_icon, img_right;
        RelativeLayout ll_adapter;
        LinearLayout ll_item_task;
    }
}