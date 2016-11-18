package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.ColumnBean;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.tools.MallConversionUtil;
import com.wodm.android.view.newview.MyGridView;
import com.wodm.android.view.newview.NoScrollListView;
import com.wodm.android.view.newview.OfenUseView;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by songchenyu on 16/10/21.
 */


public class FragmentMyPager extends Fragment {
    int mNum = 0;
    private MyGridView gv_guajian;
    private Context mContext;
    private RelativeLayout ll_header;
    private addClickIconListener addClickIconListener;
    private TouXiangAdapter touXiangAdapter;
    private NoScrollListView lv_noscroll;
    private List<ColumnBean> columnBeanList=new ArrayList<>();
    private static MallGuaJianBean clickBean;
    public void setClickImage(MallGuaJianBean clickBean){
        if (clickBean!=null){
            this.clickBean=clickBean;
        }
        if (touXiangAdapter != null) {
            touXiangAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 不选择时
     */
    public void onUnselect() {

        if (touXiangAdapter != null) {
            touXiangAdapter.onUnselect();
        }
    }


    public void setmNum(int position){
        this.mNum = position;
    }

    public TouXiangAdapter getTouAdapter(){
        return touXiangAdapter;
    }

//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
//    }

    public void setAddClickIconListener(addClickIconListener listener) {
        addClickIconListener = listener;
    }

    public interface addClickIconListener {
        void addImage(MallGuaJianBean mallGuaJianBean ,boolean isVip, int index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        View v = inflater.inflate(R.layout.fragment_listview, container, false);
        lv_noscroll= (NoScrollListView) v.findViewById(R.id.lv_noscroll);
        lv_noscroll.setAdapter(new HeaderImageGuajianAdapter());
        return v;
    }
    public void setNumClown(List<ColumnBean> columnBeanList){
        if (columnBeanList.size()>0){
            this.columnBeanList.addAll(columnBeanList);
        }
    }
    private class HeaderImageGuajianAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return columnBeanList.size()>0?columnBeanList.size():0;
        }

        @Override
        public Object getItem(int position) {
            return columnBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int productType=3;
            if (mNum==0){
                productType=3;
            }else {
                productType=4;
            }
            MyHolder myHolder=null;
            if (convertView==null){
                myHolder=new MyHolder();
                convertView=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mypage,null,false);
                OfenUseView ofenuserview= (OfenUseView) convertView.findViewById(R.id.ofenuserview);
                final MyGridView gv_guajian= (MyGridView) convertView.findViewById(R.id.gv_guajian);
                String name=columnBeanList.get(position).getName();
                ofenuserview.setTitle(name);
                httpGet(Constants.APP_GET_PRODUCT_PAGEBYCLIUMN +Constants.CURRENT_USER.getData().getAccount().getId()+"&productType="+productType+"&name="+name, new HttpCallback() {

                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            List<MallGuaJianBean> beanList= new Gson().fromJson(obj.getString("data"), new TypeToken<List<MallGuaJianBean>>() {
                            }.getType());
                            if (beanList.size()>0){
                                gv_guajian.setAdapter(new TouXiangAdapter(gv_guajian,beanList));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        Toast.makeText(getActivity(), ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(myHolder);
            }else {
                myHolder= (MyHolder) convertView.getTag();
            }
            return convertView;
        }
    }
    private class MyHolder{

    }
    public void httpGet(String url, final HttpCallback callback) {

        try {
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpGet(getActivity(), url, TrackApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public class TouXiangAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        private MyGridView mGirdview;
        private List<MallGuaJianBean> beanList;

        public TouXiangAdapter(MyGridView girdview, List<MallGuaJianBean> beanList) {
            this.mGirdview = girdview;
            mGirdview.setOnItemClickListener(this);
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
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_guajian_guajian, null, false);
                holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
                holder.img_guajian_kuang = (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
                holder.score= (TextView) convertView.findViewById(R.id.tv_score);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            MallGuaJianBean mallGuaJianBean=beanList.get(position);
            String name=mallGuaJianBean.getProductName();
            holder.tv_name.setText(name);
            if (clickBean != null && clickBean.getProductName().equals(name)) {
                holder.img_guajian_kuang.setVisibility(View.VISIBLE);
//              addClickIconListener.addImage(clickStr, imageRescoures[position], false,mNum);
            } else {
                holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
            }
            int flag=mallGuaJianBean.getFlag();
            if (flag==0){
                holder.score.setText(mallGuaJianBean.getNeedScore()+"积分");
            }else {
                holder.score.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                holder.score.setText("已购买");
            }
            try {
                MallConversionUtil.getInstace().dealExpression(getActivity(),name,holder.img_icon,mallGuaJianBean.getProductImageUrl());
            } catch (Exception e) {
                Glide.with(getActivity()).load(name).placeholder(R.mipmap.loading).into(holder.img_icon);
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (addClickIconListener == null) return;
            MallGuaJianBean mallGuaJianBean = beanList.get(position);
            addClickIconListener.addImage(mallGuaJianBean,false,mNum);
            notifyDataSetChanged();
        }

        /**
         * 不选择时
         */
        public void onUnselect() {
            clickBean = null;
            notifyDataSetChanged();
        }

        class Holder {
            ImageView img_icon, img_guajian_kuang;
            TextView tv_name,score;
        }
    }

}

