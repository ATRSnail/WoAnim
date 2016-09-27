package com.wodm.android.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wodm.R;
import com.wodm.android.bean.NewsBean;
import com.wodm.android.tools.TimeTools;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/4/28.
 */
@Layout(R.layout.adapter_news)
public class NewsAdapter extends HolderAdapter<NewsBean> {
    public NewsAdapter(Context context, List<NewsBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolders holders = (ViewHolders) viewHolder;
        NewsBean bn = mData.get(i);
        Uri uri = Uri.parse(bn.getLogo());
        holders.logo.setImageURI(uri);
        holders.title.setText(bn.getTitle());
        holders.desp.setText("资讯来源: "+bn.getSource());
        holders.time.setText(TimeTools.getTime(bn.getCreateTime()));
    }


    class ViewHolders extends BaseViewHolder {

        @ViewIn(R.id.logo)
        private SimpleDraweeView logo;
        @ViewIn(R.id.desp)
        private TextView desp;
        @ViewIn(R.id.title)
        private TextView title;
        @ViewIn(R.id.time)
        private TextView time;
        public ViewHolders(View view) {
            super(view);
        }
    }
}