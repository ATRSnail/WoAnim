package com.wodm.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wodm.R;
import com.wodm.android.CartoonApplication;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CarDetailActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/4/26.
 */
@Layout(R.layout.adapter_seacher_result)
public class SeacherResultAdapter extends HolderAdapter<ObjectBean> {
    public SeacherResultAdapter(final Context context, List<ObjectBean> data) {
        super(context, data);
        setOnItemClickListener(new OnItemClickListener<ObjectBean>() {
            @Override
            public void onItemClick(View view, ObjectBean o, int i) {

                Toast.makeText(context, o.getResourceType() +"",0).show();
                mContext.startActivity(new Intent(mContext, o.getResourceType() == 1 ? AnimDetailActivity.class : CarDetailActivity.class).putExtra("resourceId", o.getId()));
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolders holders = (ViewHolders) viewHolder;
        ObjectBean bean = mData.get(i);
        Uri uri = Uri.parse(bean.getShowImage());
        holders.img.setImageURI(uri);
        holders.name.setText(bean.getName());
        holders.chapter.setText((bean.getType() == 1 ? "更新至" : "全") + bean.getChapter() + "集");
        holders.nick.setText(bean.getDesp());
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holders.cover.getLayoutParams();
//        lp.height =  CartoonApplication.getScreenWidth() ;
//        holders.cover.setLayoutParams(lp);
    }


    class ViewHolders extends BaseViewHolder {

        @ViewIn(R.id.img)
        private SimpleDraweeView img;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.nick)
        private TextView nick;
//        @ViewIn(R.id.cover)
//        private RelativeLayout cover;
        @ViewIn(R.id.chapter_info)
        private TextView chapter;

        public ViewHolders(View view) {
            super(view);
        }
    }
}
