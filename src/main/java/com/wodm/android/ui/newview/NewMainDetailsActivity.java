package com.wodm.android.ui.newview;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.NewMain1Adapter;
import com.wodm.android.adapter.newadapter.NewMain3Adapter;
import com.wodm.android.adapter.newadapter.NewMainDetailsLVAdapter;
import com.wodm.android.bean.NewMainBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by songchenyu on 16/11/30.
 */
@Layout(R.layout.aty_newmaindetails)
public class NewMainDetailsActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;
    @ViewIn(R.id.gv_maindetails)
    private GridView gv_maindetails;
    private String columnId;
    private int style;
    private List<NewMainBean> beanList;
    private List<NewMainBean.ResourcesBean> resourcesBeen;
    private Context mContext;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=NewMainDetailsActivity.this;
        if (getIntent()!=null){
            columnId=getIntent().getExtras().getString("name");
            style=getIntent().getExtras().getInt("style");
            id=getIntent().getExtras().getInt("id");
        }else {
            finish();
        }

        initView();
        initData();
    }
    private void initData(){
        HttpUtil.httpGet(this, Constants.APP_GET_MAIN_MORE_RESCOURE + "?columnId="+id, new HttpCallback() {
            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    resourcesBeen = new Gson().fromJson(obj.getString("data"), new TypeToken<List<NewMainBean.ResourcesBean>>() {
                    }.getType());
//                    List<NewMainBean.ResourcesBean> beanList=srotList(resourcesBeen);
                    if (style==1){
                        gv_maindetails.setAdapter(new NewMain1Adapter(mContext,resourcesBeen,2));
                    }else if (style==2){
                        gv_maindetails.setAdapter(new NewMain3Adapter(mContext,resourcesBeen));
                    }else if (style==3){
                        gv_maindetails.setAdapter(new NewMainDetailsLVAdapter(mContext,resourcesBeen));
                    } else if (style==4){
                        gv_maindetails.setNumColumns(2);
                        gv_maindetails.setAdapter(new NewMain1Adapter(mContext,resourcesBeen,2));
                    } else if (style==5){
                        gv_maindetails.setNumColumns(3);
                        gv_maindetails.setAdapter(new NewMain1Adapter(mContext,resourcesBeen,3));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



    }
    private List<NewMainBean.ResourcesBean> srotList(List<NewMainBean.ResourcesBean> resourcesBean){
        List<NewMainBean.ResourcesBean> myList=new ArrayList<>();
        if (resourcesBean==null||resourcesBean.size()==0){
            return myList;
        }
        int[] inteds=new int[resourcesBean.size()];
        for (int i = 0; i <resourcesBean.size() ; i++) {
            NewMainBean.ResourcesBean beans=resourcesBean.get(i);
            inteds[i]=beans.getSort();
        }
        Arrays.sort(inteds);//升序排序
        for (int i = inteds.length-1; i >=0 ; i--) {
            for (NewMainBean.ResourcesBean bean:resourcesBean){
                if (bean.getSort()==inteds[i]){
                    myList.add(bean);
                }
            }
        }
        return myList;
    }
    private void initView(){
        set_topbar.setOnTopbarClickListenter(this);
        set_topbar.setTvTitle(columnId);
//        gv_maindetails.setAdapter(new NewMainAdapter(this));
//        lv_maindetails.setVisibility(View.GONE);
//        lv_maindetails.setAdapter(new NewMainDetailsLVAdapter(this));
    }

    @Override
    public void leftClick() {
         finish();
    }

    @Override
    public void rightClick() {

    }
}
