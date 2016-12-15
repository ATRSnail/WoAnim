package com.wodm.android.adapter.newadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wodm.R;
import com.wodm.android.adapter.NewMain1Adapter;
import com.wodm.android.bean.NewMainBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.view.newview.MyGridView;

import java.util.List;

/**
 * Created by songchenyu on 16/11/30.
 */

public class NewMainGvAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewMainBean.ResourcesBean> resourcesBean;
    public NewMainGvAdapter(Context context,List<NewMainBean.ResourcesBean> resourcesBean) {
        this.mContext = context;
        this.resourcesBean=resourcesBean;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return resourcesBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View1Holders view1Holders=null;
        int width= Tools.getScreenWidth((Activity) mContext);
        if (convertView==null){
            view1Holders=new View1Holders();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.new_main_gv_adapter,null,false);
            view1Holders.new_main_image_no_gv= (MyGridView) convertView.findViewById(R.id.new_main_image_no_gv);
            convertView.setTag(view1Holders);
        }else {
            view1Holders= (View1Holders) convertView.getTag();
        }
            view1Holders.new_main_image_no_gv.setNumColumns(3);
            view1Holders.new_main_image_no_gv.setAdapter(new NewMain1Adapter(mContext,resourcesBean,3));
        return convertView;
    }
    class View1Holders {
        private ImageView img_angle;
        private MyGridView new_main_image_no_gv;
    }
}