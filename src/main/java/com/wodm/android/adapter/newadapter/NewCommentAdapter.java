package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.NewCommentBean;
import com.wodm.android.tools.DegreeTools;
import com.wodm.android.ui.newview.PersionActivity;
import com.wodm.android.ui.newview.SendMsgActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.utils.TextColorUtils;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.biaoqing.FaceConversionUtil;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by ATRSnail on 2016/12/12.
 * 新的动漫画详情页面 评论的适配
 */
@Layout(R.layout.new_adapter_comment)
public class NewCommentAdapter extends HolderAdapter<NewCommentBean> implements View.OnClickListener {
    private Context context;
    private ViewHolder holder = null;
    private NewCommentBean bean = null;
    private int position;

    public NewCommentAdapter(Context mContext, List<NewCommentBean> data) {
        super(mContext, data);
        this.context = mContext;
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new NewCommentAdapter.ViewHolder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        holder = (ViewHolder) viewHolder;
        bean = mData.get(i);
        position = i;

        holder.allView.setOnClickListener(this);
        holder.zanBtn.setOnClickListener(this);
        holder.guanzhu.setOnClickListener(this);
        holder.content.setOnClickListener(this);

        holder.name.setText(bean.getSendNickName());
        TextColorUtils.getInstance().setGradeColor(mContext, holder.grade_comment, bean.getGradeValue());
        DegreeTools.getInstance(mContext).getDegree(mContext, bean.getGradeValue(), holder.gradename_comment);
        if (bean.getIsLike() == 1) {
            bean.setZan(true);
        } else {
            bean.setZan(false);
        }
        holder.zanBtn.setImageResource(bean.isZan() ? R.mipmap.zan : R.mipmap.un_zan);
        if (bean.getIsFollow() == 1) {
            bean.setGuanzhu(true);
        } else {
            bean.setGuanzhu(false);
        }
        holder.guanzhu.setImageResource(bean.isGuanzhu() ? R.mipmap.guanzhu : R.mipmap.no_guanzhu);
        holder.time.setText(bean.getTimes());
        holder.hour_comment.setText("");
        holder.dianzanNum_comment.setText(bean.getGoodCount());
        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context, bean.getSendCommentContent());
        holder.content.setText(spannableString);
        new AsyncImageLoader(mContext, R.mipmap.default_header, R.mipmap.default_header).display(holder.img, bean.getSendPortrait());


        if (!UpdataUserInfo.isLogIn(context, true, null)) {
            Toast.makeText(context, "未登录，请先登录", Toast.LENGTH_SHORT).show();
            return;
        } else {
            holder.atwo_name.setText(bean.getReceiveNickName().toString());
            holder.atwo_info.setText(bean.getReceiveCommentContent().toString());
        }


    }

    @Override
    public void onClick(View v) {
        if (!UpdataUserInfo.isLogIn(context, true, null)) {
            Toast.makeText(context, "未登录，请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        long followUserId = bean.getSendId();
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_new_comm:
                intent.setClass(context, PersionActivity.class);
                intent.putExtra("anotherId", followUserId);
                intent.putExtra("guanzhu", false);
                intent.putExtra("anotherInfo", true);
                mContext.startActivity(intent);
                break;
            case R.id.dianzan_comment:
                new MessageUtils().dianZan(bean.isZan() ? Constants.DELETEUSERLIKE : Constants.SAVEUSERLIKE
                        , Constants.CURRENT_USER.getData().getAccount().getId(), bean.getSendCommentId(), 2);
                break;
            case R.id.guanzhu_comment:
                new FansAdapter().saveOrDeleteUserFollow(bean.isGuanzhu() ? 0 : 1, followUserId);
                break;
            case R.id.content_comment:
                intent.setClass(context, SendMsgActivity.class);
                mContext.startActivity(intent);
                break;
        }
        bean = mData.get(position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @ViewIn(R.id.pho_comment)
        private CircularImage img;
        @ViewIn(R.id.time_comment)
        private TextView time;
        @ViewIn(R.id.name_comment)
        private TextView name;
        @ViewIn(R.id.grade_comment)
        private TextView grade_comment;
        @ViewIn(R.id.gradename_comment)
        private TextView gradename_comment;
        @ViewIn(R.id.content_comment)
        private TextView content;
        @ViewIn(R.id.hour_comment)
        private TextView hour_comment;
        @ViewIn(R.id.dianzanNum_comment)
        private TextView dianzanNum_comment;
        @ViewIn(R.id.atwo_name)
        private TextView atwo_name;
        @ViewIn(R.id.atwo_info)
        private TextView atwo_info;
        @ViewIn(R.id.dianzan_comment)
        ImageView zanBtn;
        @ViewIn(R.id.guanzhu_comment)
        ImageView guanzhu;
        @ViewIn(R.id.ll_new_comm)
        LinearLayout allView;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
