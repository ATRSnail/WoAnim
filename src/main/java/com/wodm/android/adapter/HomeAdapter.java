package com.wodm.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.ObjectDataBean;
import com.wodm.android.ui.home.CartoonsActivity;

import org.eteclab.ui.widget.NoScrollGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/4/26.
 */


@Layout(R.layout.adapter_home_items)
public class HomeAdapter extends HolderAdapter<ObjectDataBean> {
    public HomeAdapter(Context context, List<ObjectDataBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, final int index) {
        ViewHolders holders = (ViewHolders) viewHolder;
        final ObjectDataBean bean = mData.get(index);
        ComicAdapter hotAdapter = new ComicAdapter(mContext, bean.getResources());
        new AsyncImageLoader(mContext, R.mipmap.tubiao, R.mipmap.tubiao).display(holders.icon, bean.getIcon());
        holders.name.setText(bean.getName());
        holders.gridView.setAdapter(hotAdapter);
        holders.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CartoonsActivity.class);
                intent.putExtra("columnId", bean.getId());
                intent.putExtra("title", bean.getName());
                mContext.startActivity(intent);
            }
        });
    }


    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.icon)
        private ImageView icon;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.more)
        private TextView more;
        @ViewIn(R.id.grid_new)
        private NoScrollGridView gridView;
        public ViewHolders(View view) {
            super(view);
        }
    }
}
