package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.tools.MallConversionUtil;
import com.wodm.android.view.newview.MyGridView;

import java.util.List;

/**
 * Created by songchenyu on 16/10/21.
 */

public class GuaJianImageHeaderAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context mContext;
    private MallGuaJianBean clickBean;
    private MyGridView mGirdview;
    private FragmentMyPager.addClickIconListener addClickIconListener;
    private  List<MallGuaJianBean> beanList;
    public GuaJianImageHeaderAdapter(MyGridView mGirdview,Context context,List<MallGuaJianBean> beanList){
        this.mContext=context;
        this.beanList=beanList;
        this.mGirdview=mGirdview;
        mGirdview.setOnItemClickListener(this);
    }
    public void setAddClickIconListener(FragmentMyPager.addClickIconListener listener) {
        addClickIconListener = listener;
    }

    @Override
    public int getCount() {
        return beanList.size()>0?beanList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_all_guajian,null,false);
            holder.img_icon= (ImageView) convertView.findViewById(R.id.img_icon);
            holder.img_guajian_kuang= (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_score= (TextView) convertView.findViewById(R.id.tv_score);
            holder.ll_guajian_free= (RelativeLayout) convertView.findViewById(R.id.ll_guajian_free);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        MallGuaJianBean mallGuaJianBean=beanList.get(position);
        String name=mallGuaJianBean.getProductName();
        if (clickBean!=null&&clickBean.getProductName().equals(name)){
            holder.img_guajian_kuang.setVisibility(View.VISIBLE);
        }else {
            holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
        }
        holder.tv_name.setText(name);
        holder.tv_score.setText(mallGuaJianBean.getNeedScore()+"");
        try {
            MallConversionUtil.getInstace().dealExpression(mContext,name,holder.img_icon,mallGuaJianBean.getProductImageUrl());
        } catch (Exception e) {
            Glide.with(mContext).load(name).placeholder(R.mipmap.loading).into(holder.img_icon);
            e.printStackTrace();
        }

//        holder.img_icon.setBackgroundResource(mallGuaJianBean.get);
        holder.tv_name.setText(name);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MallGuaJianBean mallGuaJianBean=beanList.get(position);
        clickBean=mallGuaJianBean;
        notifyDataSetChanged();
        addClickIconListener.addImage(mallGuaJianBean,true,0);
    }

    /**
     * 不选择时
     */
    public void onUnselect(){
        clickBean= null;
        notifyDataSetChanged();
    }


    class Holder{
        RelativeLayout ll_guajian_free;
        ImageView img_icon,img_guajian_kuang;
        TextView tv_name,tv_score;
    }
}
