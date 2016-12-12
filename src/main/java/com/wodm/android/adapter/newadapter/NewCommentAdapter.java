package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wodm.android.bean.CommentBean;

import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by ATRSnail on 2016/12/12.
 * 新的动漫画详情页面 评论的适配
 */

public class NewCommentAdapter extends HolderAdapter<CommentBean>{
    public NewCommentAdapter(Context context, List<CommentBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return null;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {

    }
}
