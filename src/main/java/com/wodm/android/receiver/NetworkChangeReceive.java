package com.wodm.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.eteclab.base.http.HttpUtil;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/11/4
 */
public class NetworkChangeReceive extends BroadcastReceiver{

    private NetworkChangeListener listener;

    public void setNetworkChangeListener(NetworkChangeListener listener){
        this.listener = listener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            listener.networkChangeListener();
        }
    }
}
