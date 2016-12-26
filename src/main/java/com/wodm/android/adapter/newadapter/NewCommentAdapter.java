package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/12.
 * 新的动漫画详情页面 评论的适配
 */

public class NewCommentAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private ViewHolder holder = null;
    List<NewCommentBean> data;
    private NewCommentBean bean;
    private int i;
    private List<Boolean> list=new ArrayList<>();
    private List<Boolean> list2=new ArrayList<>();
    public NewCommentAdapter(Context mContext, List<NewCommentBean> mData) {
        this.context = mContext;
        this.data=mData;
    }

    @Override
    public int getCount() {
        return data.size();
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
            convertView.setTag(holder);
        }else {
          holder = (ViewHolder) convertView.getTag();
        }
        i=position;
        bean =data.get(position);
//        holder.allView.setOnClickListener(this);


        holder.name.setText(bean.getSendNickName());
        TextColorUtils.getInstance().setGradeColor(context, holder.grade_comment, bean.getGradeValue());
        DegreeTools.getInstance(context).getDegree(context, bean.getGradeValue(), holder.gradename_comment);
        if (bean.getIsLike()==1){
            holder.zanBtn.setImageResource(R.mipmap.zan);
            list.add(true);
        }else {
            list.add(false);
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
        holder.dianzanNum_comment.setText(bean.getGoodCount()+"");
        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(context, bean.getSendCommentContent());
        holder.content.setText(spannableString);
        new AsyncImageLoader(context, R.mipmap.default_header, R.mipmap.default_header).display(holder.img, bean.getSendPortrait());


        if (!UpdataUserInfo.isLogIn(context, true, null)) {
            Toast.makeText(context, "未登录，请先登录", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            holder.atwo_name.setText("@"+bean.getReceiveNickName()+" ：");
            holder.atwo_info.setText(String.valueOf(bean.getReceiveCommentContent()));
        }
        holder.zanBtn.setOnClickListener(this);
        holder.guanzhu.setOnClickListener(this);
        holder.content.setOnClickListener(this);
        return convertView;
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
                context.startActivity(intent);
                break;
            case R.id.dianzan_comment:
                if (list.get(i) ){
                    holder.zanBtn.setImageResource(R.mipmap.un_zan);
                }else {
                    holder.zanBtn.setImageResource(R.mipmap.zan);
                }
                list.set(i,!list.get(i));
                break;
            case R.id.guanzhu_comment:
                if (list2.get(i) ){
                    holder.guanzhu.setImageResource(R.mipmap.guanzhu);
                }else {
                    holder.guanzhu.setImageResource(R.mipmap.no_guanzhu);
                }
                list2.set(i,!list2.get(i));
                break;
            case R.id.content_comment:
                intent.setClass(context, SendMsgActivity.class);
                context.startActivity(intent);
                break;
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
        ImageView zanBtn;
        ImageView guanzhu;


    }
}
