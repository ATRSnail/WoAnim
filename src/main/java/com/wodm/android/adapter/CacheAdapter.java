package com.wodm.android.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.R;
import com.wodm.android.CartoonApplication;
import com.wodm.android.bean.CacheBean;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.DowmStatus;
import com.wodm.android.db.dowms.DowmListDao;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/5/10.
 */
@Layout(R.layout.adapter_car)
public class CacheAdapter extends HolderAdapter<CacheBean> {

    public CacheAdapter(final Context context, List<CacheBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolders holders = (ViewHolders) viewHolder;
        final CacheBean bean = mData.get(i);
        Uri uri = Uri.parse(bean.getLogo());
        holders.img.setImageURI(uri);
        holders.name.setText(bean.getTitle());
        final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holders.cover.getLayoutParams();
        lp.height = (CartoonApplication.getScreenWidth() - 40) / 3;
        holders.cover.setLayoutParams(lp);
        try {
            List list = DowmListDao.getIntence(mContext).findByWhereAnd("path", "LIKE", bean.getPath()+"%", WhereBuilder.b("status", "=", DowmStatus.FINISH.name()));
            List All = DowmListDao.getIntence(mContext).findByWhere("path", "LIKE", bean.getPath()+"%");
            holders.nick.setText("(已完成：" + (list == null ? 0 : list.size()) + ")");
            holders.chapter.setText((All == null ? 0 : All.size())  + "集");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.img)
        private SimpleDraweeView img;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.nick)
        private TextView nick;
        @ViewIn(R.id.cover)
        private RelativeLayout cover;
        @ViewIn(R.id.chapter_info)
        private TextView chapter;
        @ViewIn(R.id.icon_delete)
        private ImageButton delete;

        public ViewHolders(View view) {
            super(view);
        }
    }


}