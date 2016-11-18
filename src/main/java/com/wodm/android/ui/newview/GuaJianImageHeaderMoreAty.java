package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.FragmentMyPager;
import com.wodm.android.adapter.newadapter.MallAdapter;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by songchenyu on 16/11/17.
 */
@Layout(R.layout.aty_guajianimageheader)
public class GuaJianImageHeaderMoreAty extends AppActivity implements FragmentMyPager.addClickIconListener , AtyTopLayout.myTopbarClicklistenter{
    private List<MallGuaJianBean> newsbeanList;
    @ViewIn(R.id.new_grid_mall)
    private MyGridView new_grid_mall;
    private MallAdapter adapter;
    private MallGuaJianBean clickBean;
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);
        httpGet(Constants.APP_GET_MALL_TOUXIANG +Constants.CURRENT_USER.getData().getAccount().getId()+"&page="+1+"&size=1000" , new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    newsbeanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<MallGuaJianBean>>() {
                    }.getType());

                    adapter = new MallAdapter(GuaJianImageHeaderMoreAty.this, newsbeanList);
                    new_grid_mall.setAdapter(adapter);
                    initClick();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Toast.makeText(GuaJianImageHeaderMoreAty.this, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initClick(){
        adapter.setAddClickIconListener(this);
    }

    @Override
    public void addImage(MallGuaJianBean mallGuaJianBean, boolean isVip, int index) {
//        if (clickBean==null){
//            clickBean=mallGuaJianBean;
//            BuyingTools.getInstance(GuaJianImageHeaderMoreAty.this,mallGuaJianBean).BuyingGoods();
//        }else if (mallGuaJianBean.getId()==clickBean.getId()){
//            clickBean=null;
//            adapter.onUnselect();
//        }else {
//            clickBean=mallGuaJianBean;
//            BuyingTools.getInstance(GuaJianImageHeaderMoreAty.this,mallGuaJianBean).BuyingGoods();
//        }
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
