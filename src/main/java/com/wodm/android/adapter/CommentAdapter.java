package com.wodm.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.gestures.GestureDetector;
import com.wodm.R;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.newview.PersionActivity;
import com.wodm.android.utils.UpdataUserInfo;
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
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {

        return new ViewHolder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        final CommentBean bean = mData.get(i);
        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context,bean.getContent());
        holder.content.setText(spannableString);
//        holder.content.setText(bean.getContent());
        new AsyncImageLoader(mContext, R.mipmap.default_header, R.mipmap.default_header).display(holder.img, bean.getSendPortrait());
        holder.name.setText(bean.getSendAccountName());
        holder.time.setText(bean.getCreateTimeStr());
        holder.zanBtn.setImageResource(bean.isZan()?R.mipmap.zan:R.mipmap.un_zan);
        holder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UpdataUserInfo.isLogIn( mContext,true,null)) {
                    Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(mContext, PersionActivity.class);
                long followUserId = bean.getSendId();
                intent.putExtra("anotherId",followUserId);
                intent.putExtra("guanzhu", false);
                intent.putExtra("anotherInfo", true);
                mContext.startActivity(intent);
            }
        });
        holder.zanBtn.setOnClickListener(new ZanClick(bean));
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
        @ViewIn(R.id.img_zan)
        ImageView zanBtn;
        private View allView;
        public ViewHolder(View view) {
            super(view);
            this.allView =view;
        }
    }

    class ZanClick implements View.OnClickListener{

        private CommentBean bean;

        public ZanClick(CommentBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
           if (bean.isZan()){

           }
        }
    }
}
