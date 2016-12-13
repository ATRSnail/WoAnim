package com.wodm.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.gestures.GestureDetector;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.newview.PersionActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.biaoqing.FaceConversionUtil;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by json on 2016/4/29.
 */
@Layout(R.layout.adapter_commnet)
public class CommentAdapter extends HolderAdapter<CommentBean> {
    private Context context;


    public CommentAdapter(Context context, List<CommentBean> data) {
        super(context, data);
        this.context = context;
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
        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context, bean.getSendCommentContent());
        holder.content.setText(spannableString);
//        holder.content.setText(bean.getContent());
        new AsyncImageLoader(mContext, R.mipmap.default_header, R.mipmap.default_header).display(holder.img, bean.getSendPortrait());
        holder.name.setText(bean.getSendNickName());
        holder.time.setText(bean.getTimes());
        holder.zanBtn.setImageResource(bean.isZan() ? R.mipmap.zan : R.mipmap.un_zan);
        holder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UpdataUserInfo.isLogIn(mContext, true, null)) {
                    Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(mContext, PersionActivity.class);
                long followUserId = bean.getSendId();
                intent.putExtra("anotherId", followUserId);
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
            this.allView = view;
        }
    }

    class ZanClick implements View.OnClickListener {

        private CommentBean bean;

        public ZanClick(CommentBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
            if (!UpdataUserInfo.isLogIn(mContext, true, null)) {
                Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
                return;
            }
            /**1:表示点赞的是个人信息  2:表示的是动漫画评论的信息 3:表示的是新闻资讯的评论信息*/
            ((AppActivity) context).httpPost(bean.isZan() ? Constants.DELETEUSERLIKE : Constants.SAVEUSERLIKE
                    , zanObj(Constants.CURRENT_USER.getData().getAccount().getId(),bean.getSendCommentId(), 2)
                    , new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            try {
                                if (obj.getString("code").equals("1000")) {
//                                    Toast.makeText(context,obj.get("message").toString(),Toast.LENGTH_SHORT).show();
                                    bean.setZan();
                                    notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthFailure(result, obj);
                            Toast.makeText(context,"网络异常,请稍后重试",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void doRequestFailure(Exception exception, String msg) {
                            super.doRequestFailure(exception, msg);
                            Toast.makeText(context,"网络异常,请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        private JSONObject zanObj(long userId, long contentId, int type) {
            //保存用户点赞的、 用户取消点赞的、内容的方法
            /**1:表示点赞的是个人信息  2:表示的是动漫画评论的信息 3:表示的是新闻资讯的评论信息*private int type*/


            JSONObject object = new JSONObject();
            try {
                object.put("userId", userId);
                object.put("contentId", contentId);
                object.put("type", type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object;
        }
    }
}
