package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
import com.wodm.android.bean.HeaderBean;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.tools.MallConversionUtil;
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


public class FragmentMyPager extends Fragment implements View.OnTouchListener {
    private static String TYPE_NUM = "type";
    private static String LIST_TYPE = "list";
    private int mNum = 0;
    private Context mContext;
    private RelativeLayout ll_header;
    private addClickIconListener addClickIconListener;
    private NoScrollListView lv_noscroll;
    private ArrayList<ColumnBean> columnBeanList = new ArrayList<>();
    private static MallGuaJianBean clickBean;

    public FragmentMyPager() {
    }

    public static FragmentMyPager newInstance(int type,ArrayList<ColumnBean> columnBeanList){
        FragmentMyPager fragmentMyPager = new FragmentMyPager();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_NUM,type);
        bundle.putParcelableArrayList(LIST_TYPE, columnBeanList);
        fragmentMyPager.setArguments(bundle);
        return fragmentMyPager;
    }

    public void setClickImage(MallGuaJianBean clickBean) {
        this.clickBean = clickBean;
//        if (touXiangAdapter != null) {
//            setTouXiangAdapter(touXiangAdapter);
//            touXiangAdapter.setMclickBean(clickBean);
//            touXiangAdapter.notifyDataSetChanged();
//        }
    }


    /**
     * 不选择时
     */
    public void onUnselect() {

//        if (getTouXiangAdapter() != null) {
//            getTouXiangAdapter().onUnselec(getTouXiangAdapter());
//        }
        updateGv(null);
    }


    public void setAddClickIconListener(addClickIconListener listener) {
        addClickIconListener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == event.ACTION_UP) {
            v.getParent().requestDisallowInterceptTouchEvent(false);
        } else {
            v.getParent().requestDisallowInterceptTouchEvent(true);
        }


        return false;
    }

    public interface addClickIconListener {
        void addImage(MallGuaJianBean mallGuaJianBean, boolean isVip, int index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("num----->"+mNum);
        mContext = getActivity();
        View v = inflater.inflate(R.layout.fragment_listview, container, false);
        lv_noscroll = (NoScrollListView) v.findViewById(R.id.lv_noscroll);
        lv_noscroll.setOnTouchListener(this);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null){
            mNum = getArguments().getInt(TYPE_NUM);
            columnBeanList = getArguments().getParcelableArrayList(LIST_TYPE);
            requestList();
        }
    }

    private ArrayList<HeaderBean> headerBeen = new ArrayList<>();
    private int cateLength;

    private void requestList() {
        System.out.println("num----->"+mNum);
        for (ColumnBean columnBean : columnBeanList) {
            httpGet(Constants.APP_GET_PRODUCT_PAGEBYCLIUMN + Constants.CURRENT_USER.getData().getAccount().getId() + "&productType=" + (this.mNum == 0 ? 3 : 4) + "&name=" + columnBean.getName(), new CallBack(columnBean));
        }
    }

    class CallBack extends HttpCallback {
        private ColumnBean columnBean;

        public CallBack(ColumnBean columnBean) {
            this.columnBean = columnBean;
        }

        @Override
        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
            super.doAuthSuccess(result, obj);
            ArrayList<MallGuaJianBean> beanList = null;
            try {
                beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MallGuaJianBean>>() {
                }.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            headerBeen.add(new HeaderBean(beanList, columnBean));
            requestCount();
        }

        @Override
        public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
            super.doAuthFailure(result, obj);
            headerBeen.add(new HeaderBean(null, columnBean));
            requestCount();
        }
    }

    private void requestCount() {
        cateLength++;
        if (columnBeanList.size() == cateLength) {
            HeaderImageGuajianAdapter adapter = new HeaderImageGuajianAdapter();
            lv_noscroll.setAdapter(adapter);
        }
    }

    private class HeaderImageGuajianAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return columnBeanList.size() > 0 ? columnBeanList.size() : 0;
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
            MyHolder myHolder;
            HeaderBean headerBean = headerBeen.get(position);
            String name = headerBean.getColumnBean().getName();
            if (convertView == null) {
                myHolder = new MyHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mypage, null, false);
                myHolder.ofenuserview = (OfenUseView) convertView.findViewById(R.id.ofenuserview);
                myHolder.gv_guajian = (GridView) convertView.findViewById(R.id.gv_guajian);
                myHolder.gv_guajian.setAdapter(new TouXiangAdapter(myHolder.gv_guajian));
                convertView.setTag(myHolder);
            } else {
                myHolder = (MyHolder) convertView.getTag();
            }
            TouXiangAdapter adapter = (TouXiangAdapter) myHolder.gv_guajian.getAdapter();
            clickListeners.add(adapter);
            adapter.setBeanList(headerBean.getGuaJianList());
            myHolder.ofenuserview.setTitle(name);

            return convertView;
        }
    }


    private class MyHolder {
        private OfenUseView ofenuserview;
        private GridView gv_guajian;
    }

    public void httpGet(String url, final HttpCallback callback) {

        try {
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpGet(getActivity(), url, TrackApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<AdapterView.OnItemClickListener> clickListeners = new ArrayList<>();
    private void updateGv(MallGuaJianBean mclickBean){
        if (clickListeners == null || clickListeners.size() <= 0) return;
        for (AdapterView.OnItemClickListener clickListener: clickListeners) {
            ((TouXiangAdapter)clickListener).notifyState(mclickBean);
        }
    }

    public class TouXiangAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, View.OnTouchListener {
        private GridView mGirdview;
        private List<MallGuaJianBean> beanList = new ArrayList<>();
        MallGuaJianBean mclickBean;

        public void setBeanList(List<MallGuaJianBean> beanList) {
            if (beanList == null) return;
            this.beanList.clear();
            this.beanList.addAll(beanList);
            notifyDataSetChanged();
        }

        public MallGuaJianBean getMclickBean() {
            return mclickBean;
        }


        public void notifyState( MallGuaJianBean mclickBean){
            this.mclickBean = mclickBean;
            notifyDataSetChanged();
        }

        public TouXiangAdapter(GridView mGirdview) {
            this.mGirdview = mGirdview;
            mGirdview.setOnItemClickListener(this);
        }

        @Override
        public int getCount() {
            return beanList.size() > 0 ? beanList.size() : 0;
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

            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_guajian_guajian, null, false);
                holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
                holder.img_guajian_kuang = (ImageView) convertView.findViewById(R.id.img_guajian_kuang);
                holder.score = (TextView) convertView.findViewById(R.id.tv_score);
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            MallGuaJianBean mallGuaJianBean = beanList.get(position);
            String name = mallGuaJianBean.getProductName();
            holder.tv_name.setText(name);
            if (getMclickBean() != null) {
                if (getMclickBean().getProductName().equals(name)) {
                    holder.img_guajian_kuang.setVisibility(View.VISIBLE);
                } else {
                    holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
                }
//              addClickIconListener.addImage(clickStr, imageRescoures[position], false,mNum);
            } else {
                holder.img_guajian_kuang.setVisibility(View.INVISIBLE);
            }
            int flag = mallGuaJianBean.getFlag();
            if (flag == 0) {
                holder.score.setText(mallGuaJianBean.getNeedScore() + "积分");
            } else {
                holder.score.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                holder.score.setText("已购买");
            }
            try {
                MallConversionUtil.getInstace().dealExpression(getActivity(), name, holder.img_icon, mallGuaJianBean.getProductImageUrl());
            } catch (Exception e) {
                Glide.with(getActivity()).load(name).placeholder(R.mipmap.loading).into(holder.img_icon);
                e.printStackTrace();
            }

            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (addClickIconListener == null)
                return;
            MallGuaJianBean mallGuaJianBean = beanList.get(position);
            updateGv(mallGuaJianBean);
            addClickIconListener.addImage(mallGuaJianBean, false, mNum);
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                v.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        }


        class Holder {
            ImageView img_icon, img_guajian_kuang;
            TextView tv_name, score;
        }
    }

}

