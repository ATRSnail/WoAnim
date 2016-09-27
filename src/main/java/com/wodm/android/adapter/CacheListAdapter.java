package com.wodm.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.R;
import com.wodm.android.bean.DowmBean;
import com.wodm.android.bean.DowmStatus;
import com.wodm.android.db.dowms.DowmListDao;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CartoonReadActivity;
import com.wodm.android.utils.FileUtils;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2016/5/16.
 */
@Layout(R.layout.adapter_cache)
public class CacheListAdapter extends HolderAdapter<DowmBean> {
    Boolean isEdit = false;

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public CacheListAdapter(final Context context, List<DowmBean> data) {
        super(context, data);
        setOnItemClickListener(new OnItemClickListener<DowmBean>() {
            @Override
            public void onItemClick(View view, DowmBean dowmBean, int i) {
                if (DowmStatus.valueOf(dowmBean.getStatus()) == DowmStatus.FINISH) {
                    if (!new File(dowmBean.getPath()).exists()) {
                        Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (dowmBean.getResourceType() == 1) {
                        Intent intent = new Intent(context, AnimDetailActivity.class);
                        intent.putExtra("beanPath", dowmBean.getPath());
                        intent.putExtra("resourceId", dowmBean.getResourceId());
                        context.startActivity(intent);
                    } else if (dowmBean.getResourceType() == 2) {

                        try {
                            Intent intent = new Intent(context, CartoonReadActivity.class);
                            intent.putExtra("beanPath", dowmBean.getPath());
                            intent.putExtra("pathList", (ArrayList<DowmBean>) DowmListDao.getIntence(context).findByWhereAnd("status", "=", DowmStatus.FINISH.name(), WhereBuilder.b("resourceId", "=", dowmBean.getResourceId())));

                            intent.putExtra("resourceId", dowmBean.getResourceId());
                            context.startActivity(intent);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolders holders = (ViewHolders) viewHolder;

        final DowmBean bean = mData.get(i);
        holders.cacheTitle.setText(bean.getTitle() + " " + bean.getChapter() + "集");
        new AsyncImageLoader(mContext, R.mipmap.loading, R.mipmap.loading).display(holders.cacheImg, bean.getLogo());
        final String maxSize = FileUtils.FormetFileSize(bean.getMaxSize());
        final String progSize = FileUtils.FormetFileSize(bean.getProgress());

        switch (DowmStatus.valueOf(bean.getStatus())) {
            case ERROR: //失败、错误
                holders.cacheStatus.setImageLevel(0);
                holders.cacheStatus.setImageResource(R.mipmap.cache_failure);
                holders.cachePro.setText("缓存失败:" + progSize + "/" + maxSize);
                break;
            case WAIT: //等待
                holders.cacheStatus.setImageResource(R.mipmap.cache_wait);
                holders.cachePro.setText("等待中:" + progSize + "/" + maxSize);
                break;
            case PAUSE: //暂停下载
                holders.cacheStatus.setImageLevel(1);
                holders.cacheStatus.setImageResource(R.mipmap.cache_start);
                holders.cachePro.setText("已暂停:" + progSize + "/" + maxSize);
                break;
            case FINISH: //完成
                holders.cacheStatus.setVisibility(View.INVISIBLE);
                holders.cacheProBar.setVisibility(View.INVISIBLE);
                holders.cachePro.setText("缓存完成:" + maxSize);
                break;
            case LOGDING: // 正在下载
                holders.cacheStatus.setImageResource(R.mipmap.cache_pause);
                holders.cacheStatus.setImageLevel(2);
                holders.cachePro.setText("缓存中:" + progSize + "/" + maxSize);
                break;
        }
        if (isEdit) {
            holders.cacheStatus.setVisibility(View.VISIBLE);
            holders.cacheStatus.setImageResource(R.mipmap.cache_delete);
        }
        holders.cacheProBar.setMax((int) bean.getMaxSize());
        holders.cacheProBar.setProgress((int) bean.getProgress());
        holders.cacheStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
//                    Toast.makeText(mContext, "编辑" + i, 0).show();
                    if (callLisenter != null) {
                        callLisenter.callEdit(bean);
                    }
                    return;
                }
                if (callLisenter != null) {
                    callLisenter.callStatus(bean);
                }
                switch (DowmStatus.valueOf(bean.getStatus())) {
//                    case ERROR: //失败、错误
//                        Toast.makeText(mContext, "ERROR" + i, 0).show();
//                        break;
//                    case WAIT: //等待
//                        Toast.makeText(mContext, "WAIT" + i, 0).show();
//                        break;
                    case PAUSE: //暂停下载
//                        Toast.makeText(mContext, "点击开始下载" + i, 0).show();
                        break;
//                    case FINISH: //完成
//                        Toast.makeText(mContext, "WAIT" + i, 0).show();
//                        break;
                    case LOGDING: // 正在下载
//                        Toast.makeText(mContext, "点击暂停下载" + i, 0).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    class ViewHolders extends BaseViewHolder {

        @ViewIn(R.id.cache_logo)
        private ImageView cacheImg;

        @ViewIn(R.id.cache_title)
        private TextView cacheTitle;

        @ViewIn(R.id.cache_progress)
        private TextView cachePro;

        @ViewIn(R.id.progressBars)
        private ProgressBar cacheProBar;

        @ViewIn(R.id.imag_button)
        private ImageView cacheStatus;

        public ViewHolders(View view) {
            super(view);
        }
    }

    CAllLisenter callLisenter;

    public void setcallLisenter(CAllLisenter callLisenter) {
        this.callLisenter = callLisenter;
    }

    public static abstract class CAllLisenter {

        protected abstract void callEdit(DowmBean bean);

        protected abstract void callStatus(DowmBean bean);

    }
}
