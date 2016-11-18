package com.wodm.android.fragment.newfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.FragmentMyPager;
import com.wodm.android.adapter.newadapter.MallAdapter;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by songchenyu on 16/11/16.
 */

public class AllGuaJianFragment extends Fragment implements FragmentMyPager.addClickIconListener  {
    private List<MallGuaJianBean> newsbeanList;
    private MyGridView new_grid_mall;
    private MallAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.from(getActivity()).inflate(R.layout.all_guajian_fragment,null,false);
        new_grid_mall= (MyGridView) view.findViewById(R.id.new_grid_mall);
        //Constants.CURRENT_USER.getData().getAccount().getId()
        httpGet(Constants.APP_GET_MALL_OF_PRODUCT_LIST +Constants.CURRENT_USER.getData().getAccount().getId()+"&productType=4" , new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                   newsbeanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MallGuaJianBean>>() {
                        }.getType());
                    adapter = new MallAdapter(getActivity(), newsbeanList);
                    new_grid_mall.setAdapter(adapter);
                    initClick();
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
        return view;
    }
    private void initClick(){
        adapter.setAddClickIconListener(this);
    }
    public void httpGet(String url, final HttpCallback callback) {

        try {
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpGet(getActivity(), url, TrackApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addImage(MallGuaJianBean mallGuaJianBean, boolean isVip, int index) {

    }
}
