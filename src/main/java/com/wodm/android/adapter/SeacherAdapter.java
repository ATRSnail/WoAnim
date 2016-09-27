package com.wodm.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.R;
import com.wodm.android.bean.SeacherBean;
import com.wodm.android.db.WoDbUtils;
import com.wodm.android.ui.SeacherActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/5/5.
 */
@Layout(R.layout.adapter_seacher)
public class SeacherAdapter extends HolderAdapter<SeacherBean> {
    public SeacherAdapter(Context context, List<SeacherBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolders holders = (ViewHolders) viewHolder;
        final SeacherBean bn = mData.get(i);
        holders.content.setText(bn.getContent()+"");
        holders.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WoDbUtils.initialize(mContext).delete(SeacherBean.class, WhereBuilder.b("content","=",bn.getContent()));
                    ((SeacherActivity)(mContext)).uphostortList();
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    class ViewHolders extends  BaseViewHolder{
        @ViewIn(R.id.conntent)
        private TextView content;
        @ViewIn(R.id.delete)
        private ImageView delete;
        public ViewHolders(View view) {
            super(view);
        }
    }
}
