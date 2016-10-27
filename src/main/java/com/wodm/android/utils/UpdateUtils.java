package com.wodm.android.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UpgradeBean;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.json.JSONObject;

import java.io.File;

/**
 * 软件升级工具类
 *
 * @author ldm
 */
public class UpdateUtils {

    Context mCtx;
    private String SD_MALL_PATH = "";

    public UpdateUtils(Context context) {
        mCtx = context;
        SD_MALL_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
    }

    public void checkUpdate(final boolean showToast) {
        try {
            final RemoteViews mRemoteView = new RemoteViews(mCtx.getPackageName(), R.layout.layout_notification_download);
            mRemoteView.setProgressBar(R.id.progress_horizontal, 100, 0, false);
            mRemoteView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
            mRemoteView.setTextViewText(R.id.text, "新版本下载中");
            JSONObject o = new JSONObject();
            o.put("appkey", Constants.APPKEY);
            o.put("versionCode", getVersionCode(mCtx));
            o.put("packageName", mCtx.getPackageName());
            o.put("osVersion", android.os.Build.VERSION.SDK_INT);
            o.put("platform", 1);

            ((AppActivity) (mCtx)).httpPost(Constants.APP_UPGRADE_URL, o, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Log.e("checkUpdate", "" + obj.toString());
                        final UpgradeBean bean = new Gson().fromJson(obj.getString("data"), UpgradeBean.class);
                        if (bean.getVersionCode() > getVersionCode(mCtx)) {
                            android.support.v7.app.AlertDialog
                                    dialog = new android.support.v7.app.AlertDialog.Builder(mCtx).setTitle("发现新版本").setMessage(bean.getDescription())
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final String savePath = SD_MALL_PATH + "uicom.dm.apk";
                                            final NotificationManager manager = (NotificationManager) mCtx.getSystemService(
                                                    Context.NOTIFICATION_SERVICE);
                                            Notification.Builder builder = new Notification.Builder(mCtx);
                                            builder.setSmallIcon(R.mipmap.ic_launcher);
                                            builder.setWhen(System.currentTimeMillis());
                                            builder.setAutoCancel(true);
                                            builder.setContentIntent(PendingIntent.getActivity(mCtx, 0,
                                                    new Intent(Intent.ACTION_DELETE), 0));

                                            final Notification notification = builder.getNotification();
                                            manager.notify(R.string.app_name, notification);
                                            ((AppActivity) (mCtx)).httpDownload(bean.getFileUrl(), savePath, new HttpCallback() {
                                                @Override
                                                public void doDownloadSuccess(File file) {
                                                    manager.cancel(R.string.app_name);
                                                    intallApk(file);
                                                }

                                                @Override
                                                public void doDownloadLoading(int progress) {
                                                    mRemoteView.setProgressBar(R.id.progress_horizontal, 100, progress, false);
                                                    mRemoteView.setTextViewText(R.id.text, "新版本下载中" + progress + "%");
                                                    notification.contentView = mRemoteView;
                                                    manager.notify(R.string.app_name, notification);
                                                }
                                            });
                                        }
                                    }).show();
                            dialog.setCanceledOnTouchOutside(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    if (showToast) {
                        Toast.makeText(mCtx, "已经是最新版", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkUpdate(final Context mCtx, String url) {

        final RemoteViews mRemoteView = new RemoteViews(mCtx.getPackageName(), R.layout.layout_notification_download);
        mRemoteView.setProgressBar(R.id.progress_horizontal, 100, 0, false);
        mRemoteView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        mRemoteView.setTextViewText(R.id.text, "新版本下载中");

        final String savePath = Environment.getExternalStorageDirectory().getPath() + "/" + "uicom.dm.apk";
        final NotificationManager manager = (NotificationManager) mCtx.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(mCtx);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setContentIntent(PendingIntent.getActivity(mCtx, 0,
                new Intent(Intent.ACTION_DELETE), 0));

        final Notification notification = builder.getNotification();
        manager.notify(R.string.app_name, notification);
        HttpUtil.httpDownload(mCtx, url, savePath, new HttpCallback() {
            @Override
            public void doDownloadSuccess(File file) {
                manager.cancel(R.string.app_name);
//                    intallApk(file);
                Toast.makeText(mCtx, file.getAbsolutePath() + " -- file", 0).show();
            }

            @Override
            public void doDownloadLoading(int progress) {
                mRemoteView.setProgressBar(R.id.progress_horizontal, 100, progress, false);
                mRemoteView.setTextViewText(R.id.text, "新版本下载中" + progress + "%");
                notification.contentView = mRemoteView;
                manager.notify(R.string.app_name, notification);
            }
        });
    }

    /**
     * 下载成功进行安装
     *
     * @param temp
     */
    private void intallApk(File temp) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(temp),
                "application/vnd.android.package-archive");
        mCtx.startActivity(intent);
    }


    /*
     * 获取应用的版本号
     */
    private int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(),
                    0);
            return packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /*
    * 获取应用的版本号
    */
    private String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(),
                    0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}