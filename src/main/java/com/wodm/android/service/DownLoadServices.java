package com.wodm.android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.util.PreferencesCookieStore;
import com.wodm.android.bean.CacheBean;
import com.wodm.android.bean.DowmBean;
import com.wodm.android.bean.DowmStatus;
import com.wodm.android.db.dowms.CacheListDao;
import com.wodm.android.db.dowms.DowmListDao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by json on 2016/5/17.
 */
public class DownLoadServices extends Service {

    CacheListDao cacheListDao;
    DowmListDao dowmListDao;

    private final int HTTP_HANDLERS = 1;
    // 下载全部队列
    private List<DowmBean> mAllList;
    // 正在下载的队列
    private List<DowmBean> mLoadingList;

    HashMap<String, HttpHandler> handlers = new HashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAllList = new ArrayList<>();
        mLoadingList = new ArrayList<>();
        cacheListDao = new CacheListDao(getApplicationContext(), CacheBean.class);
        dowmListDao = DowmListDao.getIntence(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction() + "";
            ArrayList<DowmBean> Datas = (ArrayList) intent.getSerializableExtra("data");
            if (("com.unicom.pause").equals(action)) {
                //暂停
                deleteHandler(Datas);
            } else if (("com.unicom.onResume").equals(action)) {
                //继续下载
                checkRepeat(Datas);
                addHttpHandler();
            } else if (("com.unicom.addHandler").equals(action)) {
                // 添加下载任务
                checkRepeat(Datas);
                addHttpHandler();
            } else if (("com.unicom.deleteHandler").equals(action)) {
                deleteHandler(Datas);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 检查队列中是否有重复
     *
     * @param datas
     */
    private void checkRepeat(List<DowmBean> datas) {
        ArrayList<DowmBean> lists = new ArrayList<>();
        for (DowmBean bean : datas) {
            for (DowmBean bn : mAllList) {
                if (bn.getUrl().equals(bean.getUrl())) {
                    lists.add(bn);
                    bean = bn;
                }
            }
        }
        mAllList.remove(lists);
        mAllList.addAll(datas);
    }


    private void deleteHandler(List<DowmBean> datas) {
        ArrayList<DowmBean> lists = new ArrayList<>();
        for (DowmBean bean : datas) {
            if (handlers.containsKey(bean.getUrl())) {
                HttpHandler handler = handlers.get(bean.getUrl());
                handler.cancel();
                handler = null;
                handlers.remove(bean.getUrl());
            }
            for (DowmBean bn : mAllList) {
                if (bn.getUrl().equals(bean.getUrl())) {
                    lists.add(bn);
                    bean = bn;
                }
            }
            mLoadingList.remove(bean);
        }
        mAllList.removeAll(lists);
        addHttpHandler();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mAllList = dowmListDao.findByWhere("status", "<>", DowmStatus.FINISH.name());
            if (mAllList != null) {
                for (DowmBean bn : mAllList) {
                    bn.setStatus(DowmStatus.WAIT);
                    DowmListDao.getIntence(getApplicationContext()).update(bn);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


    }


    /**
     * 添加下载队列
     */
    public void addHttpHandler() {
        ArrayList<DowmBean> arrayList = new ArrayList(mLoadingList);
        mLoadingList.clear();
        for (DowmBean bean : arrayList) {
            if (bean.getStatus() == DowmStatus.WAIT.name() || bean.getStatus() == DowmStatus.LOGDING.name()) {
                mLoadingList.add(bean);
            }
        }

        if (mAllList != null)
            for (DowmBean bean : mAllList) {
                if (bean.getStatus().equals(DowmStatus.WAIT.name()) && mLoadingList.size() < HTTP_HANDLERS) {
                    mLoadingList.add(bean);
                }
            }
        startCache();
    }

    /**
     * 开始下载
     */
    private void startCache() {
        initialize(getApplicationContext());
        for (int index = 0; index < mLoadingList.size(); index++) {
            final DowmBean bean = mLoadingList.get(index);
            File f = new File(bean.getPath()).getParentFile();
            if (!f.exists()) {
                f.mkdirs();
            }

            if (DowmStatus.valueOf(bean.getStatus()) == DowmStatus.WAIT) {
                if (handlers.containsKey(bean.getUrl())) {
                    HttpHandler handle = handlers.get(bean.getUrl());
                    handle.resume();
                    try {
                        bean.setStatus(DowmStatus.LOGDING);
                        dowmListDao.update(bean);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                } else {
                    final int indexs = index;
                    final HttpHandler handle = mHttpUtils.download(bean.getUrl(), bean.getPath(), true, new RequestCallBack<File>() {

                        @Override
                        public void onStart() {
                            super.onStart();
                            try {
                                bean.setStatus(DowmStatus.LOGDING);
                                dowmListDao.update(bean, getRequestUrl());
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }

                        public void deleteHandlers() {
                            ArrayList<DowmBean> lists = new ArrayList<DowmBean>();
                            lists.add(bean);
                            deleteHandler(lists);
                        }

                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            LogUtils.e("下载成功 -- > " + responseInfo.result.getAbsolutePath());
                            // TODO 下载成功
                            try {
                                sendProgress(bean);
                                bean.setStatus(DowmStatus.FINISH);
                                dowmListDao.update(bean, getRequestUrl());
                                deleteHandlers();
                            } catch (DbException e) {
                                e.printStackTrace();
                                addHttpHandler();
                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            // TODO 下载失败
                            LogUtils.e(msg + "下载失败 -- >" + bean.getUrl());
                            try {
                                sendProgress(bean);
                                bean.setStatus(DowmStatus.ERROR);
                                dowmListDao.update(bean, getRequestUrl());
                                deleteHandlers();
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            super.onLoading(total, current, isUploading);
                            int progress = (int) (current * 100L / total);
                            LogUtils.e("下载中... -- >" + bean.getPath());
                            LogUtils.e("当前进度" + progress + "%");
                            if (current / 1024 / 1024 * 10 % 20 == 0)
                                sendProgress(bean);
                            // TODO 下载进度
                            try {
                                bean.setMaxSize(total);
                                bean.setProgress(current);
                                if (getRequestUrl().equals(bean.getUrl()))
                                    dowmListDao.update(bean, getRequestUrl());
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    if (!handlers.containsKey(bean.getUrl())) {
                        handlers.put(bean.getUrl(), handle);
                        startCache();
                    }
                }
            }
        }
    }

    HttpUtils mHttpUtils;

    public void initialize(Context context) {
        if (mHttpUtils == null) {
            mHttpUtils = new HttpUtils(15000);
            mHttpUtils.configResponseTextCharset("UTF-8");
            mHttpUtils.configCurrentHttpCacheExpiry(0L);
            mHttpUtils.configDefaultHttpCacheExpiry(0L);
            mHttpUtils.configRequestThreadPoolSize(10);
            PreferencesCookieStore cookieStore = new PreferencesCookieStore(context);
            cookieStore.clear();
            mHttpUtils.configCookieStore(cookieStore);
        }
    }


    private void sendProgress(DowmBean bean) {
        sendBroadcast(new Intent("com.wodm.android.service.progress").putExtra("bean", bean));
    }

}