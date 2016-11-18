package com.wodm.android.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.Constants;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.utils.DialogUtils;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songchenyu on 16/11/18.
 */

public class BuyingTools {
    private static BuyingTools buyingTools;
    private static Context mContext;
    private static MallGuaJianBean mAllGuaJianBean;
    public static BuyingTools getInstance(Context context,MallGuaJianBean mallGuaJianBean){
        mContext=context;
        mAllGuaJianBean=mallGuaJianBean;
        if (buyingTools==null){
            buyingTools=new BuyingTools();
        }
        return buyingTools;
    }
    private BuyingTools (){

    }
    public void BuyingGoods(){
        if (mAllGuaJianBean==null){
            return;
        }
        sendResuest();
    }
    private void sendResuest(){
        httpGet(Constants.APP_GET_PRODUCT_IS_BUY +Constants.CURRENT_USER.getData().getAccount().getId()+"&productCode="+mAllGuaJianBean.getProductCode() , new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                if (obj.optInt("data")==1){
                    BuyingGoodsDialog();
                }else {
                    NoScore();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Toast.makeText(mContext, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void NoScore(){
        new DialogUtils.Builder(mContext)
                .setMessage("您的积分不足").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private void BuyingGoodsDialog(){
        new DialogUtils.Builder(mContext)
                .setTitle("砖石头像框")
                .setMessage("确定使用"+mAllGuaJianBean.getNeedScore()+"积分兑换？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        BuyingGoods(mAllGuaJianBean);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    private void BuyingGoods(MallGuaJianBean mallGuaJianBean){
        httpGet(Constants.APP_GET_BUY_PRODUCT +Constants.CURRENT_USER.getData().getAccount().getId()+"&productCode="+mallGuaJianBean.getProductCode()+"&payType=1" , new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                Toast.makeText(mContext, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Toast.makeText(mContext, ""+obj.optString("message"), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void httpGet(String url, final HttpCallback callback) {

        try {
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpGet(mContext, url, TrackApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
