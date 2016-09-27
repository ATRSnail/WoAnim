package com.wodm.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.bean.MessageBean;
import com.wodm.android.utils.DeviceUtils;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by moon1 on 2016/5/11.
 */
@Layout(R.layout.adapter_message_list)
public class MessageListAdapter extends HolderAdapter<MessageBean> {
    public MessageListAdapter(Context context, List<MessageBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        MessageBean bean = mData.get(i);
        ((ViewHolder)viewHolder).mMsgContent.setText(bean.getContent());
        ((ViewHolder) viewHolder).mMsgTime.setText(DeviceUtils.getTimes(bean.getCreateTime()));
    }

    class ViewHolder extends BaseViewHolder{

        @ViewIn(R.id.message_content)
        private TextView mMsgContent;
        @ViewIn(R.id.message_time)
        private TextView mMsgTime;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
