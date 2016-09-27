package com.wodm.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.view.biaoqing.FaceConversionUtil;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/4/29.
 */
@Layout(R.layout.adapter_commnet)
public class CommentAdapter extends HolderAdapter<CommentBean> {
    private Context context;


    public CommentAdapter(Context context, List<CommentBean> data) {
        super(context, data);
        this.context=context;
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        CommentBean bean = mData.get(i);
        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context,bean.getContent());
        holder.content.setText(spannableString);
//        holder.content.setText(bean.getContent());
        new AsyncImageLoader(mContext, R.mipmap.default_header, R.mipmap.default_header).display(holder.img, bean.getSendPortrait());
        holder.name.setText(bean.getSendAccountName());
        holder.time.setText(bean.getCreateTimeStr());
    }

    class ViewHolder extends BaseViewHolder {
        @ViewIn(R.id.logo)
        private CircularImage img;
        @ViewIn(R.id.time)
        private TextView time;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.content)
        private TextView content;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
