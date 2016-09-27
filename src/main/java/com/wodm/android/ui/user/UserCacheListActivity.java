package com.wodm.android.ui.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.wodm.R;
import com.wodm.android.adapter.CacheListAdapter;
import com.wodm.android.bean.CacheBean;
import com.wodm.android.bean.DowmBean;
import com.wodm.android.bean.DowmStatus;
import com.wodm.android.db.dowms.CacheListDao;
import com.wodm.android.db.dowms.DowmListDao;
import com.wodm.android.service.DownLoadServices;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.DeviceUtils;
import com.wodm.android.utils.FileUtils;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.NoScrollGridView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by json on 2016/5/16.
 */
@Layout(R.layout.activity_cache_list)
public class UserCacheListActivity
        extends AppActivity {
    private final static String TITLE = "缓存详情列表";
    @ViewIn(R.id.cache_status)
    private TextView mStatusTv;
    @ViewIn(R.id.cache_iamge_status)
    private ImageView mStatusIv;
    @ViewIn(R.id.cache_edit)
    private TextView mCacheEdit;
    @ViewIn(R.id.cache_sdcart)
    private TextView mSdcard;

    @ViewIn(R.id.grid_new)
    private NoScrollGridView mCacheList;
    CacheListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle(getIntent().getStringExtra("title"));
        paths = getIntent().getStringExtra("paths");
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.wodm.android.service.progress");
        registerReceiver(receiver, filter);
        upData();
        mSdcard.setText("手机存储: 总空间" + FileUtils.FormetFileSize(DeviceUtils.SDcardInfo.getAllSize()) + "/剩余" + FileUtils.FormetFileSize(DeviceUtils.SDcardInfo.getAvailaleSize()));
    }

    String paths = "";

    private void upData() {
        try {
            ArrayList list = new ArrayList(DowmListDao.getIntence(getApplicationContext()).findByWhere("path", "LIKE", paths + "%"));

            adapter = new CacheListAdapter(this, (list));
            adapter.setcallLisenter(new CacheListAdapter.CAllLisenter() {
                @Override
                protected void callEdit(DowmBean bean) {
                    try {
                        DowmListDao.getIntence(getApplicationContext()).delete(bean);
                        FileUtils.deleteFile(bean.getPath());
                        ArrayList lists = new ArrayList<>();
                        lists.add(bean);
                        Intent intent = new Intent(getApplicationContext(), DownLoadServices.class);
                        intent.setAction("com.unicom.deleteHandler");
                        intent.putExtra("data", lists);
                        startService(intent);
                        upData();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                protected void callStatus(DowmBean bean) {
                    switch (DowmStatus.valueOf(bean.getStatus())) {
                        case PAUSE:
                            bean.setStatus(DowmStatus.WAIT);
                            break;
                        case LOGDING:
                            bean.setStatus(DowmStatus.PAUSE);
                            break;
                    }

                    try {
                        DowmListDao.getIntence(getApplicationContext()).update(bean, bean.getUrl());
                        ArrayList list = new ArrayList<DowmBean>();
                        list.add(bean);
                        startAcTion(DowmStatus.valueOf(bean.getStatus()), list);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            });
            adapter.setEdit(mCacheEdit.getTag().equals("1"));
            mCacheList.setAdapter(adapter);


            if (adapter.getItemCount() <= 0) {
                CacheListDao dao = new CacheListDao(this, CacheBean.class);
                dao.deleteByWhere("path", "=", paths);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @TrackClick(value = R.id.start_pause, location = TITLE, eventName = "全部开始、暂停")
    private void clickStartAndPause(View v) {
        try {
            if (v.getTag().equals("1")) {
                startAcTion(DowmStatus.PAUSE, (ArrayList<DowmBean>) adapter.getData());
                mStatusTv.setText("全部开始");
                mStatusIv.setImageResource(R.mipmap.cacher_all_start);
                v.setTag("0");
            } else if (v.getTag().equals("0")) {
                startAcTion(DowmStatus.WAIT, (ArrayList<DowmBean>) adapter.getData());
                mStatusTv.setText("全部暂停");
                mStatusIv.setImageResource(R.mipmap.cacher_all_pause);
                v.setTag("1");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }


    private void startAcTion(DowmStatus status, ArrayList<DowmBean> data) throws DbException {
        Intent intent = new Intent(this, DownLoadServices.class);
        ArrayList<DowmBean> lists = new ArrayList<>();
        for (DowmBean bn : data) {
            if (DowmStatus.valueOf(bn.getStatus()) != DowmStatus.FINISH) {
                bn.setStatus(status);
                DowmListDao.getIntence(getApplicationContext()).update(bn, bn.getUrl());
                lists.add(bn);
            }
        }
        intent.putExtra("data", lists);
        if (status == DowmStatus.PAUSE) {
            intent.setAction("com.unicom.pause");
        } else if (status == DowmStatus.WAIT) {
            intent.setAction("com.unicom.onResume");
        }
        startService(intent);
        upData();
    }

    @TrackClick(value = R.id.cache_edit, location = TITLE, eventName = "编辑、完成")
    private void clickEdit(View v) {
        if (mCacheEdit.getTag().equals("1")) {
            mCacheEdit.setText("编辑");
            mCacheEdit.setTag("0");
        } else if (mCacheEdit.getTag().equals("0")) {
            mCacheEdit.setText("完成");
            mCacheEdit.setTag("1");
        }
        upData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }

            DowmBean bean = (DowmBean) intent.getSerializableExtra("bean");
            if (bean == null) {
                return;
            }
            File file = new File(bean.getPath());
            if (mCacheEdit.getTag().equals("0") || file.getParent().equals(paths)) {
                upData();
            }
        }
    };

}
