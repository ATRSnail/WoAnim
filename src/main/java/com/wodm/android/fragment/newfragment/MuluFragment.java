package com.wodm.android.fragment.newfragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.JuJiAdapter;
import com.wodm.android.adapter.newadapter.JuJiNumAdapter;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.newview.DetailActivity;
import com.wodm.android.view.newview.HorizontalListView;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by songchenyu on 16/12/12.
 * 新的动漫画详情页面目录的碎片
 */

public class MuluFragment extends Fragment implements View.OnClickListener{
    private ImageView img_details_up;
    private TextView tv_jianjie_value,tv_jianjie_value2,state_zp,time,jishu;
    private boolean isShow=false;
    private RelativeLayout rl_details_up;
    private boolean isOverFlowed;
    private HorizontalListView hor_lv;
    private MyGridView gv_adapter_juji;
    private JuJiAdapter juJiAdapter;
    private JuJiNumAdapter juJiNumAdapter;
    private LinearLayout linearLayout;
    private ObjectBean bean = null;
    private int resourceId = -1;
    private int resourceType = 1;
    RecommendFragment recommend;
    private static MuluFragment muluFragment=null;
    private static onJiShuNumClickListener listener;
    public   static  int page=1;//作品的第几页
    public static MuluFragment getInstance(onJiShuNumClickListener jiShuNumClickListener){
        listener=jiShuNumClickListener;
        if (muluFragment==null){
            muluFragment=new MuluFragment();
        }
        return muluFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        resourceId=  bundle.getInt("resourceId");
        resourceType=  bundle.getInt("resourceType");
        recommend=new RecommendFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("resourceId",resourceId);
        bundle1.putInt("resourceType",resourceType);
        recommend.setArguments(bundle1);
        View view=inflater.from(getActivity()).inflate(R.layout.mulu_fragment,null,false);
        getCarToon(view);


        return view;
    }
    public void setJishuClickListener(onJiShuNumClickListener listener){
        this.listener=listener;
    }
    public interface onJiShuNumClickListener{
        public void clickNum(int position, int num);
        public void updatePager(boolean flag, ArrayList<ChapterBean> page);//更新作品每一页的内容
    }

    /**
     * 获取作品详情
     * @param view
     */
    private void getCarToon(final View view) {
        String url = Constants.URL_CARTTON_DETAIL + resourceId;
        if (Constants.CURRENT_USER != null) {
            url += ("?userId=" + Constants.CURRENT_USER.getData().getAccount().getId());
        }
        new AppActivity().httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, final JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    bean = new Gson().fromJson(obj.getString("data"), ObjectBean.class);
                    initView(view,bean);
                    setViews(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 获取作品章节
     * @param bean
     * @param total
     * @param juJiNumAdapter
     */
    public  void getNewJuji(final ObjectBean bean, int total, final JuJiNumAdapter juJiNumAdapter){
        int sortBy=0;
        if (bean.getType()==1){
            sortBy=1;
        }else {
            sortBy=0;//已完结的
        }
        String url= Constants.NEW_CHAPTER_LIST+resourceId+"&page="+(total+1)+"&sortBy="+sortBy;
        new AppActivity().httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    ArrayList<ChapterBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ChapterBean>>() {
                    }.getType());
                    listener.updatePager(true,list);
//                    juJiNumAdapter.setmChapterList(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initView(View view, final ObjectBean bean){
        img_details_up= (ImageView) view.findViewById(R.id.img_details_up);
        linearLayout= (LinearLayout) view.findViewById(R.id.tuijian);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tuijian,recommend);
        transaction.commit();
        rl_details_up= (RelativeLayout) view.findViewById(R.id.rl_details_up);
        hor_lv= (HorizontalListView) view.findViewById(R.id.hor_lv);
        gv_adapter_juji= (MyGridView) view.findViewById(R.id.gv_adapter_juji);
        juJiNumAdapter = new JuJiNumAdapter(getActivity(),bean,resourceType,resourceId,listener);
        gv_adapter_juji.setAdapter(juJiNumAdapter);
//        getNewJuji(bean,0,juJiNumAdapter);
        juJiAdapter =new JuJiAdapter(getActivity(),bean);
        juJiAdapter.setUpdateTotal(new JuJiAdapter.UpdateTotal() {
            @Override
            public void getTotal(int total) {
//                listener.updatePager(true,total);
//                page=total;
                getNewJuji(bean,total,juJiNumAdapter);
                juJiNumAdapter.setIndex(total);
                juJiNumAdapter.notifyDataSetChanged();
            }
        });
        hor_lv.setAdapter(juJiAdapter);
        img_details_up.setOnClickListener(this);
        rl_details_up.setOnClickListener(this);
        tv_jianjie_value= (TextView) view.findViewById(R.id.tv_jianjie_value);
        tv_jianjie_value2= (TextView) view.findViewById(R.id.tv_jianjie_value2);
        state_zp= (TextView) view.findViewById(R.id.state_zp);
        time= (TextView) view.findViewById(R.id.time);
        jishu= (TextView) view.findViewById(R.id.jishu);
        isOverFlowed=isOverFlowed(tv_jianjie_value,bean);
        if (isOverFlowed){
            img_details_up.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 判断TextView的内容宽度是否超出其可用宽度
     * @param tv
     * @param bean
     * @return
     */
    public static boolean isOverFlowed(TextView tv, ObjectBean bean) {
        int availableWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight();
        Paint textViewPaint = tv.getPaint();
//        float textWidth = textViewPaint.measureText(tv.getText().toString());
        float textWidth = textViewPaint.measureText(bean.getBriefDesp());
        if (textWidth > availableWidth) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_details_up:
            case R.id.img_details_up:
                if (isOverFlowed){
                    if (!isShow){
                        tv_jianjie_value2.setVisibility(View.VISIBLE);
                        tv_jianjie_value.setVisibility(View.GONE);
                        img_details_up.setBackgroundResource(R.mipmap.img_details_up);
                        isShow=true;
                    }else {
                        tv_jianjie_value2.setVisibility(View.GONE);
                        tv_jianjie_value.setVisibility(View.VISIBLE);
                        img_details_up.setBackgroundResource(R.mipmap.img_details_down);
                        isShow=false;
                    }

                }
                break;
        }
    }



    public void setViews(ObjectBean bean) {
        String str= DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()).toString();
        time.setText(str);
        if (bean!=null){
            tv_jianjie_value.setText(bean.getBriefDesp());
            tv_jianjie_value2.setText(bean.getBriefDesp());
            if (bean.getType()==1){
                state_zp.setText("连载中");
                jishu.setText("更新至"+bean.getChapter()+"集");
                if (resourceType==2){
                    jishu.setText("更新至"+bean.getChapter()+"话");
                }
            }else   if (bean.getType()==2){
                state_zp.setText("完结");
                jishu.setText(bean.getChapter()+"集");
                if (resourceType==2){
                    jishu.setText(bean.getChapter()+"话");
                }
            }
        }
    }
}
