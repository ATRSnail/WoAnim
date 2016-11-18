package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.run.VideoService;
import com.wodm.android.tools.BuyingTools;
import com.wodm.android.tools.MallConversionUtil;
import com.wodm.android.ui.newview.HeaderGuaJianActivity;

import java.util.List;

/**
 * Created by ATRSnail on 2016/10/20.
 */

public class MallAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<MallGuaJianBean> list;
    private MallGuaJianBean clickBean;
    private FragmentMyPager.addClickIconListener addClickIconListener;
    public MallAdapter(Context mcontext, List<MallGuaJianBean> mlist) {
        this.context = mcontext;
        this.list=mlist;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_mall, null, false);
            holder.name = (TextView) convertView.findViewById(R.id.name_item_mall);
            holder.score = (TextView) convertView.findViewById(R.id.score_item_mall);
            holder.xianshi_mall = (ImageView) convertView.findViewById(R.id.icon_item_mall);
            holder.img_guajian_kuang = (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout_mall);
            holder.rl_border = (RelativeLayout) convertView.findViewById(R.id.rl_border);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MallGuaJianBean mallGuaJianBean=list.get(position);
        final String name=mallGuaJianBean.getProductName();
        if (clickBean!=null&&clickBean.getProductName().equals(name)){
            holder.img_guajian_kuang.setVisibility(View.VISIBLE);
        }else {
            holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
        }
        holder.name.setText(name);
        int flag=mallGuaJianBean.getFlag();
        if (flag==0){
            holder.score.setText(mallGuaJianBean.getNeedScore()+"");
        }else {
            holder.score.setTextColor(context.getResources().getColor(R.color.color_999999));
            holder.score.setText("已购买");
        }

        try {
            MallConversionUtil.getInstace().dealExpression(context,name,holder.xianshi_mall,mallGuaJianBean.getProductImageUrl());
        } catch (Exception e) {
            Glide.with(context).load(name).placeholder(R.mipmap.loading).into(holder.xianshi_mall);
            e.printStackTrace();
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View     v) {
                if (addClickIconListener!=null){
//                    startMyService(mallGuaJianBean);
                    BuyingTools.getInstance(context,mallGuaJianBean).BuyingGoods();
                }else {
                    Intent intent = new Intent(context, HeaderGuaJianActivity.class);
                    intent.putExtra("iconClick",mallGuaJianBean);
                    context.startActivity(intent);
                }

            }
        });
//        holder.icon.setImageResource((Integer) map.get("icon"));


//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.rl_border.getLayoutParams();
//        int width= (Tools.getScreenWidth((Activity) context)-240)/3;
//        int h= (int) (width*((float)192/202));
//        params.width=width;
//        params.height=h;
//        holder.rl_border.setLayoutParams(params);
        return convertView;
    }
    private void startMyService(MallGuaJianBean mallGuaJianBean){
        Intent serviceIntent=new Intent(context, VideoService.class);
        serviceIntent.putExtra("type","buying");
        serviceIntent.putExtra("bean", mallGuaJianBean);
        context.startService(serviceIntent);
    }
    public void setAddClickIconListener(FragmentMyPager.addClickIconListener listener) {
        addClickIconListener = listener;
    }
    @Override
    public void onClick(View v) {

    }
    /**
     * 不选择时
     */
    public void onUnselect(){
        clickBean= null;
        notifyDataSetChanged();
    }
    static class ViewHolder {
        TextView name;
        TextView score;
        ImageView xianshi_mall,img_guajian_kuang;
        LinearLayout layout;
        RelativeLayout rl_border;
    }

}
