package com.wodm.android.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.SeriesAdapter;
import com.wodm.android.bean.CacheBean;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.DowmBean;
import com.wodm.android.bean.DowmStatus;
import com.wodm.android.db.dowms.CacheListDao;
import com.wodm.android.db.dowms.DowmListDao;
import com.wodm.android.service.DownLoadServices;
import com.wodm.android.utils.DeviceUtils;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.ui.widget.NoScrollGridView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;

/**
 * Created by json on 2016/4/27.
 */
public abstract class DownDialog extends Dialog {
    NoScrollGridView scrollGridView;
    TextView tv;
    List<ChapterBean> mChapterList;
    String paths = "";
    SeriesAdapter adapter;
    private Context mCtx;
    private TextView mStartView;
    //清晰度 0:原画质 1：超清 2：高清 3:标清 4:流畅 等等
    private int Quality = 0;
    private String resourceId = "";

    public abstract String getResourceId();

    public DownDialog(final Context context, final int type) {
        super(context, R.style.CustomDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_anim_dowm);
        resourceId = getResourceId();
        if (type == 1) {
            paths = (Constants.SAVE_PATH + "video/" + getTitle() + "/");
        } else if (type == 2) {
            paths = (Constants.SAVE_PATH + "cartoon/" + getTitle() + "/");
        }
        mCtx = context;
        tv = (TextView) findViewById(R.id.quality);
        mStartView = (TextView) findViewById(R.id.start_dowm);
        findViewById(R.id.more_format).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuality(v);
            }

        });
        scrollGridView = (NoScrollGridView) findViewById(R.id.grid_new);
        findViewById(R.id.close_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        findViewById(R.id.select_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ChapterBean> list = new ArrayList<>();
                mStartView.setEnabled(false);
                mStartView.setBackgroundResource(R.drawable.circle_button);
                for (ChapterBean bn : mChapterList) {
                    if (bn.IsCheck() != 1 && bn.IsCheck() != 2)
                        bn.setCheck(3);
                    list.add(bn);
                    if (bn.IsCheck() == 3) {
                        mStartView.setEnabled(true);
                        mStartView.setBackgroundResource(R.drawable.circle_button_pre);
                    }
                }
                adapter.setData(list);
                scrollGridView.setAdapter(adapter);
            }
        });

        mStartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<ChapterBean> list = new ArrayList<ChapterBean>();
                for (ChapterBean bean : adapter.getData()) {
                    if (bean.IsCheck() == 3) {
                        list.add(bean);
                    }
                }
                if (list.size() <= 0) {
                    return;
                }
                mStartView.setEnabled(false);

                try {
                    CacheListDao dao = new CacheListDao(mCtx, CacheBean.class);
                    CacheBean cacheBean = null;
                    List<CacheBean> lists = dao.findByWhere("path", "=", paths);
                    if (lists != null && lists.size() == 1) {
                        cacheBean = lists.get(0);
                        cacheBean.setSumChapter(cacheBean.getSumChapter() + list.size());
                        dao.update(cacheBean);
                    } else {
                        cacheBean = new CacheBean();
                        cacheBean.setLogo(getLogo());
                        cacheBean.setTitle(getTitle());
                        cacheBean.setChapters(0);
                        cacheBean.setUrl("");
                        cacheBean.setResourceType(type);
                        cacheBean.setSumChapter(list.size());
                        if (type == 1) {
                            cacheBean.setPath(Constants.SAVE_PATH + "video/" + cacheBean.getTitle() + "/");
                        } else if (type == 2) {
                            cacheBean.setPath(Constants.SAVE_PATH + "cartoon/" + cacheBean.getTitle() + "/");
                        }
                        dao.save(cacheBean);

                    }
                    final DowmListDao listDao = DowmListDao.getIntence(context);
                    final ArrayList<DowmBean> dowmBeanList = new ArrayList<DowmBean>();
                    String url = "http://wodm.9mobi.cn/api/v1/user/clientDownload?name=" + getTitle() + "&type=" + type;
                    for (ChapterBean cbean : list) {
                        DowmBean bn = new DowmBean();
                        bn.setQuality(Quality);
                        bn.setUrl(cbean.getContentUrl());
                        bn.setTitle(getTitle());
                        bn.setResourceId(resourceId);
                        bn.setChapter(cbean.getChapter() + "");
                        bn.setResourceType(type);
                        bn.setLogo(cbean.getShowImage());
                        bn.setStatus(DowmStatus.WAIT);
                        bn.setMaxSize(0);
                        bn.setProgress(0);
                        bn.setChapterId(cbean.getId());
                        bn.setPath(cacheBean.getPath() + (type == 1 ? "video-" : "car-") + cbean.getChapter());
                        url += ("&cartoonIds=" + cbean.getId());
                        dowmBeanList.add(bn);
                    }
                    if (type == 1) {
                        listDao.saveAll(dowmBeanList);
                        Intent intent = new Intent(mCtx, DownLoadServices.class);
                        intent.putExtra("data", dowmBeanList);
                        intent.setAction("com.unicom.addHandler");
                        mCtx.startService(intent);
                        cancel();
                    } else if (type == 2)
                        HttpUtil.httpGet(context, url, new HttpCallback() {
                            @Override
                            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                super.doAuthSuccess(result, obj);
                                try {
                                    ArrayList<String> stringList = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<String>>() {
                                    }.getType());
                                    ArrayList<DowmBean> datas = new ArrayList<DowmBean>();
                                    for (int index = 0; index < stringList.size(); index++) {
                                        DowmBean bean = dowmBeanList.get(index);
                                        bean.setUrl(stringList.get(index));
                                        listDao.save(bean);
                                        datas.add(bean);
                                    }
                                    Intent intent = new Intent(mCtx, DownLoadServices.class);
                                    intent.putExtra("data", datas);
                                    intent.setAction("com.unicom.addHandler");
                                    mCtx.startService(intent);
                                    dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public abstract String getTitle();

    public abstract String getLogo();

    public void setListViews(final List<ChapterBean> mList) {

        try {
            List<DowmBean> arrayList = DowmListDao.getIntence(mCtx).findByWhere("path", "LIKE", paths + "%");
            if (this.mChapterList == null) {
                this.mChapterList = new ArrayList<>();
            } else {
                this.mChapterList.clear();
            }
            for (ChapterBean bean : mList) {
                bean.setCheck(0);
                if (arrayList != null)
                    for (DowmBean bn : arrayList) {
                        if (bean.getId().equals(bn.getChapterId())) {
                            if (DowmStatus.valueOf(bn.getStatus()) == DowmStatus.FINISH) {
                                bean.setCheck(2);
                            } else /*if (bn.getStatus() == DowmBean.DowmStatus.WAIT || bn.getStatus() == DowmBean.DowmStatus.LOGDING)*/ {
                                bean.setCheck(1);
                            }
                        }
                    }
                this.mChapterList.add(bean);
            }
            this.mChapterList = mList;
            adapter = new SeriesAdapter(mCtx, this.mChapterList, 8);

            scrollGridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (((TextView) view.findViewById(R.id.button)).getText().equals("更多")) {
                        adapter.setShowAll();
                    } else {
                        ArrayList<ChapterBean> list = new ArrayList<>();
                        mStartView.setEnabled(false);
                        mStartView.setBackgroundResource(R.drawable.circle_button);
                        for (ChapterBean bn : adapter.getData()) {
                            if (bn.getId() == mChapterList.get(position).getId()) {
                                if (bn.IsCheck() == 0) {
                                    bn.setCheck(3);
                                } else if (bn.IsCheck() == 3) {
                                    bn.setCheck(0);
                                }
                            }
                            if (bn.IsCheck() == 3) {
                                mStartView.setEnabled(true);
                                mStartView.setBackgroundResource(R.drawable.circle_button_pre);
                            }
                            list.add(bn);
                        }
                        adapter.setData(list);
                        scrollGridView.setAdapter(adapter);
                    }
                }
            });


            scrollGridView.setAdapter(adapter);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void showQuality(View v) {
        View layout = View.inflate(mCtx, R.layout.layout_quality_pop, null);
        PopupWindow w = new PopupWindow(-2, -2);
        w.setContentView(layout);
        w.setFocusable(true);
        layout.findViewById(R.id.quality_yuan).setOnClickListener(new QualityListener(w));
        layout.findViewById(R.id.quality_chao).setOnClickListener(new QualityListener(w));
        layout.findViewById(R.id.quality_gao).setOnClickListener(new QualityListener(w));
        layout.findViewById(R.id.quality_biao).setOnClickListener(new QualityListener(w));
        w.showAsDropDown(v, -90, 0);
    }

    public void show(Boolean isLand) {
        Window window = getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = isLand ? DeviceUtils.getScreenWH((Activity) mCtx)[0] * 65 / 100 : ViewGroup.LayoutParams.MATCH_PARENT; // 宽度
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        getWindow().setGravity(isLand ? Gravity.CENTER : Gravity.BOTTOM);
        super.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }

    private class QualityListener implements View.OnClickListener {

        PopupWindow w;

        public QualityListener(PopupWindow w) {
            this.w = w;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.quality_yuan:
                    Quality = 0;
                    break;
                case R.id.quality_chao:
                    Quality = 1;
                    break;
                case R.id.quality_gao:
                    Quality = 2;
                    break;
                case R.id.quality_biao:
                    Quality = 3;
                    break;
            }
            TextView tvs = (TextView) v;
            tv.setText(tvs.getText());
            w.dismiss();
        }
    }
}