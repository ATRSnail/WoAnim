package com.wodm.android.tools;

import android.content.Context;

import org.eteclab.track.TrackApplication;
import org.json.JSONException;

import java.util.TreeMap;

/**
 * Created by songchenyu on 16/11/22.
 */

public class NetHeader {

    private static TreeMap<String, String> httpHeaders = null;
    private static boolean isFirst=true;
    public static String ip="";

    public static void getHeaders(Context con) {
        if (!isFirst){
            return;
        }
        try {
            TrackApplication.REQUEST_HEADER.put("Osversion", ""+GetPhoneState.getSysRelease());// 操作系统版本
            TrackApplication.REQUEST_HEADER.put("SysLanguage", ""+GetPhoneState.getSysLanguage());// 系统语言
            TrackApplication.REQUEST_HEADER.put("Brand", ""+GetPhoneState.getBrand());// 手机品牌
            TrackApplication.REQUEST_HEADER.put("Model", ""+GetPhoneState.getModel());// 手机型号
            TrackApplication.REQUEST_HEADER.put("IMSI", ""+GetPhoneState.readSimSerialNum(con));// sim卡序列号imsi
            TrackApplication.REQUEST_HEADER.put("ICCID", ""+GetPhoneState.readSimICCID(con));// iccid
            TrackApplication.REQUEST_HEADER.put("Mac", ""+GetPhoneState.getLocalMacAddress(con));// mac
            TrackApplication.REQUEST_HEADER.put("Carrier", ""+GetPhoneState.getCarrier(con));// 运营商
            TrackApplication.REQUEST_HEADER.put("NetworkType", ""+GetPhoneState.getAccessNetworkType(con));// 联网类型
            TrackApplication.REQUEST_HEADER.put("hasIccCard", ""+GetPhoneState.hasIccCard(con));// ICC卡是否存在
//			httpHeaders.put("PhoneNumber", ""+GetPhoneState.getNativePhoneNumber());// I手机号 可能为空
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isFirst=false;
    }
}
