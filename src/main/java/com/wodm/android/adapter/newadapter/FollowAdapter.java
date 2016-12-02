package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.FansBean;
import com.wodm.android.bean.FollowBean;
import com.wodm.android.tools.DegreeTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.newview.AttentionActivity;
import com.wodm.android.ui.newview.PersionActivity;
import com.wodm.android.utils.TextColorUtils;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/11/29.
 */

public class FollowAdapter extends BaseAdapter {
    List<FollowBean.DataBean> list=new ArrayList<>();
    UpdateAttention updateAttention;

    public void setUpdateAttention(UpdateAttention updateAttention) {
        this.updateAttention = updateAttention;
    }

    public List<FollowBean.DataBean> getList() {
        return list;
    }

    public void setList(List<FollowBean.DataBean> list) {
        this.list.clear();
        this.list=list;
        notifyDataSetChanged();
    }

    Context mContext;

    public FollowAdapter(AttentionActivity attentionActivity) {
        this.mContext = attentionActivity;
    }

    public FollowAdapter() {
    }

    MyHolder holder = null;

    @Override
    public int getCount() {
        Log.e("AA","getList().size()--------------------"+getList().size());
        return getList().size();
    }

    @Override
    public Object getItem(int position) {
        return getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new MyHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.attenitem, null);
            holder.pho = (CircularImage) convertView.findViewById(R.id.pho_attention);
            holder.name = (TextView) convertView.findViewById(R.id.name_attention);
            holder.grade = (TextView) convertView.findViewById(R.id.grade_attention);
            holder.gradeName = (TextView) convertView.findViewById(R.id.gradeName_attention);
            holder.attention = (ImageView) convertView.findViewById(R.id.attention);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        FollowBean.DataBean bean = getList().get(position);
        final long followUserId = bean.getFollowUserId();
        holder.attention.setBackgroundResource(R.mipmap.noattention);
        holder.attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveOrDeleteUserFollow(0, followUserId);
            }
        });

        holder.name.setText(bean.getNickName());
        TextColorUtils.getInstance().setGradeColor(mContext, holder.grade, bean.getGradeValue());
        DegreeTools.getInstance(mContext).getDegree(mContext, bean.getGradeValue(), holder.gradeName);
        new AsyncImageLoader(mContext, R.mipmap.moren_header, R.mipmap.moren_header).display(holder.pho, bean.getPortrait());
        holder.pho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersionActivity.class);
                intent.putExtra("anotherId", followUserId);
                intent.putExtra("anotherInfo", true);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    public void saveOrDeleteUserFollow(int behavior, long followUserId) {
        try {
            if (Constants.CURRENT_USER == null) return;
            AppActivity appActivity = new AppActivity();
            String url;

            if (behavior == 0) {
                url = Constants.REMOVE_USER_ATTENTION;
            } else {
                url = Constants.SAVE_USER_ATTENTION;
            }

            JSONObject post = new JSONObject();
            long userId = Constants.CURRENT_USER.getData().getAccount().getId();
            post.put("userId", userId);
            post.put("followUserId", followUserId);
            appActivity.httpPost(url, post, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    updateAttention.update(true);
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);

                }

                @Override
                public void doRequestFailure(Exception exception, String msg) {
                    super.doRequestFailure(exception, msg);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    static class MyHolder {
        CircularImage pho;
        TextView name;
        TextView grade;
        TextView gradeName;
        ImageView attention;

    }

    public  interface  UpdateAttention{
        public void update(boolean flag);
    }
}
