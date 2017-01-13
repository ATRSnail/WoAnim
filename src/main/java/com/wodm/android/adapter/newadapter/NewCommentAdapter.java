package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.CommentAdapter;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.bean.NewCommentBean;
import com.wodm.android.tools.DegreeTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.newview.PersionActivity;
import com.wodm.android.ui.newview.SendMsgActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.utils.TextColorUtils;
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

import java.util.ArrayList;
import java.util.List;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by ATRSnail on 2016/12/12.
 * 新的动漫画详情页面 评论的适配
 */

public class NewCommentAdapter extends BaseAdapter {
    private Context context;
    List<NewCommentBean> data=new ArrayList<>();
    private List<Boolean> list=new ArrayList<>();
    private List<Boolean> list2=new ArrayList<>();
    public NewCommentAdapter(Context mContext) {
        this.context = mContext;
    }

    public void setData(List<NewCommentBean> data, boolean b) {
        if (b){
            this.data.clear();
        }
        this.data.addAll(data);
    }

    @Override
    public int getCount() {
//        Log.e("AA","*****************"+data.size());
        return data.size()>0? data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.new_adapter_comment,null);
            holder.img = (CircularImage) convertView.findViewById(R.id.pho_comment);
            holder.name = (TextView) convertView.findViewById(R.id.name_comment);
            holder.grade_comment = (TextView) convertView.findViewById(R.id.grade_comment);
            holder.gradename_comment = (TextView) convertView.findViewById(R.id.gradename_comment);
            holder.time = (TextView) convertView.findViewById(R.id.time_comment);
            holder.dianzanNum_comment = (TextView) convertView.findViewById(R.id.dianzanNum_comment);
            holder.zanBtn = (ImageView) convertView.findViewById(R.id.dianzan_comment);
            holder.guanzhu = (ImageView) convertView.findViewById(R.id.guanzhu_comment);
            holder.content = (TextView) convertView.findViewById(R.id.content_comment);
            holder.atwo_name = (TextView) convertView.findViewById(R.id.atwo_name);
            holder.atwo_info = (TextView) convertView.findViewById(R.id.atwo_info);
            holder.hour_comment = (TextView) convertView.findViewById(R.id.hour_comment);
            holder.line = (View) convertView.findViewById(R.id.line_pl);
            holder.other_pl = (LinearLayout) convertView.findViewById(R.id.other_pl);
            convertView.setTag(holder);
        }else {
          holder = (ViewHolder) convertView.getTag();
        }
         NewCommentBean  bean =data.get(position);

        holder.name.setText(bean.getSendNickName());
        TextColorUtils.getInstance().setGradeColor(context, holder.grade_comment, bean.getGradeValue());
        DegreeTools.getInstance(context).getDegree(context, bean.getGradeValue(), holder.gradename_comment);
        if (bean.getIsLike()==1){
            holder.zanBtn.setImageResource(R.mipmap.zan);
            holder.dianzanNum_comment.setVisibility(View.VISIBLE);
            list.add(true);
        }else {
            list.add(false);
            holder.dianzanNum_comment.setVisibility(View.INVISIBLE);
            holder.zanBtn.setImageResource(R.mipmap.un_zan);
        }

        if (bean.getIsFollow() == 1) {
            holder.guanzhu.setImageResource( R.mipmap.guanzhu);
            list2.add(true);
        } else {
            holder.guanzhu.setImageResource( R.mipmap.no_guanzhu);
            list2.add(false);
        }
        holder.time.setText(bean.getTimes());
        holder.hour_comment.setText("");
        if (bean.getGoodCount()>0){
            holder.dianzanNum_comment.setVisibility(View.VISIBLE);
        }else {
            holder.dianzanNum_comment.setVisibility(View.INVISIBLE);
        }
        holder.dianzanNum_comment.setText(bean.getGoodCount()+"");
        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context, bean.getSendCommentContent());
        holder.content.setText(spannableString);
        new AsyncImageLoader(context, R.mipmap.default_header, R.mipmap.default_header).display(holder.img, bean.getSendPortrait());

         String content=String.valueOf(bean.getReceiveCommentContent());
         String name=String.valueOf(bean.getReceiveNickName());
        if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(content)){
            if (!name.equals("null")&!content.equals("null")){
                holder.other_pl.setVisibility(View.VISIBLE);
                holder.line.setVisibility(View.VISIBLE);
                holder.atwo_name.setText("@"+name+" ：");
                SpannableString spannableString2 = FaceConversionUtil.getInstace().getExpressionString(context, content);
                holder.atwo_info.setText(spannableString2);
            }else {
                holder.other_pl.setVisibility(View.GONE);
                holder.line.setVisibility(View.GONE);
            }
        }


        holder.img.setOnClickListener(new MyClick(holder,position,bean));
        holder.zanBtn.setOnClickListener(new MyClick(holder,position,bean));
        holder.guanzhu.setOnClickListener(new MyClick(holder,position,bean));
        holder.content.setOnClickListener(new MyClick(holder,position,bean));
        return convertView;
    }


    class    MyClick implements View.OnClickListener {
     private NewCommentBean mBean;
     private  int i;
     private ViewHolder mHolder;
     public MyClick(ViewHolder holder, int pos, NewCommentBean bean) {
         this.mBean = bean;
         this.mHolder = holder;
         this.i=pos;
     }

     @Override
     public void onClick(View v) {
         if (!UpdataUserInfo.isLogIn(context, true, null)) {
             Toast.makeText(context, "未登录，请先登录", Toast.LENGTH_SHORT).show();
             return;
         }
         long followUserId = mBean.getSendId();
         switch (v.getId()) {
             case R.id.pho_comment:
                 Intent intent = new Intent(context, PersionActivity.class);
                 intent.putExtra("anotherId", followUserId);
                 intent.putExtra("anotherInfo", true);
                 context.startActivity(intent);
                 break;
             case R.id.dianzan_comment:
                 dianZan(mBean,mHolder,i);
                 break;
             case R.id.guanzhu_comment:
                 guanzhu(mBean,mHolder,i,followUserId);
                 break;
             case R.id.content_comment:
                 Intent intent2 = new Intent(context,SendMsgActivity.class);
                 intent2.putExtra("flag",2);
                 intent2.putExtra("content",mBean.getSendCommentContent());
                 intent2.putExtra(SendMsgActivity.CommentBEAN, mBean);
                 intent2.putExtra("name",mBean.getSendNickName());
                 context.startActivity(intent2);
                 break;
         }
     }
 }

    private void guanzhu(NewCommentBean mBean, final ViewHolder mHolder, final int i, long followUserId) {
        try {
            if (Constants.CURRENT_USER == null||followUserId==0) return;
            AppActivity appActivity = new AppActivity();
            String url;

            if (list2.get(i)) {
                url = Constants.REMOVE_USER_ATTENTION;
            } else {
                url = Constants.SAVE_USER_ATTENTION;
            }

            JSONObject post = new JSONObject();
            long id = CURRENT_USER.getData().getAccount().getId();
            post.put("userId", id);
            post.put("followUserId", followUserId);
            appActivity.httpPost(url, post, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        if (obj.getString("code").equals("1000")) {
                            if (list2.get(i) ){
                                mHolder.guanzhu.setImageResource(R.mipmap.no_guanzhu);
                            }else {
                                mHolder.guanzhu.setImageResource(R.mipmap.guanzhu);
                            }
                            list2.set(i,!list2.get(i));
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void dianZan(final NewCommentBean bean, final ViewHolder mHolder, final int postion) {
        /**1:表示点赞的是个人信息  2:表示的是动漫画评论的信息 3:表示的是新闻资讯的评论信息*private int type*/
        try {
            JSONObject object = new JSONObject();
            object.put("userId", Constants.CURRENT_USER.getData().getAccount().getId());
            object.put("contentId", bean.getSendCommentId());
            object.put("type", 2);
            ((AppActivity) context).httpPost(list.get(postion) ? Constants.DELETEUSERLIKE : Constants.SAVEUSERLIKE
                    , object
                    , new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            try {
                                if (obj.getString("code").equals("1000")) {
                                    if (list.get(postion) ){
                                        bean.setGoodCount(bean.getGoodCount()-1);//取消点赞成功-人数-1（不用重复请求数据）
                                        mHolder.zanBtn.setImageResource(R.mipmap.un_zan);
                                    }else {
                                        bean.setGoodCount(bean.getGoodCount()+1);//点赞成功人数手动加1（不用重复请求数据）
                                        mHolder.zanBtn.setImageResource(R.mipmap.zan);
                                    }
                                    list.set(postion,!list.get(postion));
                                    mHolder.dianzanNum_comment.setText(bean.getGoodCount()+"");
                                    if (bean.getGoodCount()>0){
                                        mHolder.dianzanNum_comment.setVisibility(View.VISIBLE);
                                    }else {
                                        mHolder.dianzanNum_comment.setVisibility(View.INVISIBLE);
                                    }

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
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public class ViewHolder  {
        private CircularImage img;
        private TextView time;
        private TextView name;
        private TextView grade_comment;
        private TextView gradename_comment;
        private TextView content;
        private TextView hour_comment;
        private TextView dianzanNum_comment;
        private TextView atwo_name;
        private TextView atwo_info;
        private ImageView zanBtn;
        private ImageView guanzhu;
        private View line;
        private LinearLayout other_pl;

    }
}
