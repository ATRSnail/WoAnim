package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.BuyingHistoryBean;
import com.wodm.android.bean.MedalInfoBean;

import org.apache.http.client.methods.HttpGet;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchenyu on 16/11/15.
 */

public class BuyingHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<BuyingHistoryBean.DataBean> dataBeanList;

    public BuyingHistoryAdapter(Context context, List<BuyingHistoryBean.DataBean> mdataBeanList) {
        this.mContext = context;
        this.dataBeanList=mdataBeanList;
    }


    @Override
    public int getCount() {
        return dataBeanList.size() > 0 ? dataBeanList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return dataBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_buying_history, null, false);
            holder = new ViewHolder();
            holder.score = (TextView) convertView.findViewById(R.id.numberflag);
            holder.score2 = (TextView) convertView.findViewById(R.id.numberflag2);
            holder.productName = (TextView) convertView.findViewById(R.id.productName);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.productTypeFlag = (TextView) convertView.findViewById(R.id.productTypeFlag);
            holder.productImageUrl = (CircularImage) convertView.findViewById(R.id.productImageUrl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BuyingHistoryBean.DataBean dataBean = dataBeanList.get(position);

        if (dataBean.getDescription() != null) {
            holder.description.setText(dataBean.getDescription());
        }
        if (dataBean.getNumberflag() != null) {
            holder.score.setText(dataBean.getNumberflag());
            holder.score2.setText(dataBean.getNumberflag());
        }

        if (dataBean.getProductTypeFlag() != null) {
            holder.productTypeFlag.setText(dataBean.getProductTypeFlag());
            if (dataBean.getProductName() != null) {
                holder.productName.setText("["+dataBean.getProductTypeFlag()+"] "+dataBean.getProductName()+"头像框");
            }
        }
        String pic = dataBean.getProductImageUrl();
        if (pic != null) {
            new AsyncImageLoader(mContext, R.mipmap.moren_header, R.mipmap.moren_header).display(holder.productImageUrl, pic);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView score;
        TextView score2;
        TextView productName;
        TextView description;
        TextView productTypeFlag;
        CircularImage productImageUrl;
    }


}
