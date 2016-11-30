package com.wodm.android.adapter.newadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wodm.R;
import com.wodm.android.tools.Tools;

/**
 * Created by songchenyu on 16/11/30.
 */

public class NewMainAdAdapter extends BaseAdapter {
    private Context mContext;

    public NewMainAdAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int width= Tools.getScreenWidth((Activity) mContext);
        ViewAdsHolders viewAdsHolders=null;
        if (convertView==null){
            viewAdsHolders=new ViewAdsHolders();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.new_main_ads, null, false);
            viewAdsHolders.img_angle_ads= (ImageView) convertView.findViewById(R.id.img_angle_ads);
            int hight= (int) ((width-60)*((float)140/690));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hight);
            params.setMargins(30,30,30,30);
            viewAdsHolders.img_angle_ads.setLayoutParams(params);
            convertView.setTag(viewAdsHolders);
        }else {
            viewAdsHolders= (ViewAdsHolders) convertView.getTag();
        }

        return convertView;
    }
    class ViewAdsHolders{
        private ImageView img_angle_ads;

    }
}