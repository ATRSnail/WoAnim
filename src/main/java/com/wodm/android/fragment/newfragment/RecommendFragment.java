package com.wodm.android.fragment.newfragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.TuiJianAdapter;
import com.wodm.android.bean.ChapterPageBean;
import com.wodm.android.bean.TuiJianBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2017/1/3.
 */

public class RecommendFragment extends Fragment {
    private int resourceId = -1;
    private int resourceType = 1;
    private MyGridView gridView;
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        resourceId=  bundle.getInt("resourceId");
        resourceType=  bundle.getInt("resourceType");
        View view=inflater.from(getActivity()).inflate(R.layout.tuijian_fragment,null,false);
        gridView = (MyGridView) view.findViewById(R.id.gv_tuijian);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_tuijian);
        String url= Constants.tuijian+"精彩推荐"+"&resourceId="+resourceId+"&type="+resourceType;
        new AppActivity().httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                TuiJianBean bean = new Gson().fromJson(obj.toString(),TuiJianBean.class);
                List<TuiJianBean.DataBean> dataBeen=  bean.getData();
                if (dataBeen!=null&dataBeen.size()>0){
                    List<TuiJianBean.DataBean> dataBeen2=  new ArrayList<TuiJianBean.DataBean>();
                    if (dataBeen.size()>3){
                        for (int i = 0; i <3 ; i++) {
                            dataBeen2.add(dataBeen.get(i));
                        }
                    }else {
                        dataBeen2 = bean.getData();
                    }
                    gridView.setNumColumns(dataBeen2.size());
                    gridView.setAdapter(new TuiJianAdapter(getActivity(),dataBeen2));
                }else {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }


}
