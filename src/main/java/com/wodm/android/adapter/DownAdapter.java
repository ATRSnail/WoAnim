package com.wodm.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wodm.R;
import com.wodm.android.CartoonApplication;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.ui.home.AnimDetailActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by json on 2016/5/10.
 */
@Layout(R.layout.adapter_car)
public class DownAdapter extends HolderAdapter<ChapterBean> {

    public DownAdapter(final Context context, List<ChapterBean> data) {
        super(context, data);
        /*setOnItemClickListener(new OnItemClickListener<ChapterBean>() {
            @Override
            public void onItemClick(View view, ChapterBean bean, int i) {
                if (!new File(bean.getContentUrl()).exists()) {
                    Toast.makeText(context, "文件不存在", 0).show();
                    return;
                }
                context.startActivity(new Intent(context, AnimDetailActivity.class).putExtra("resourceId", bean.getId()).putExtra("beanPath", bean.getContentUrl()));
            }
        });*/
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolders holders = (ViewHolders) viewHolder;
        final ChapterBean bean = mData.get(i);
        Uri uri = Uri.parse(bean.getShowImage());
        holders.img.setImageURI(uri);
        holders.name.setText(bean.getTitle());
        holders.chapter.setText(bean.getId() + "集");
        holders.nick.setText(bean.getDesp());
        final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holders.cover.getLayoutParams();
        lp.height = (CartoonApplication.getScreenWidth() - 40) / 3;
        holders.cover.setLayoutParams(lp);
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.img)
        private SimpleDraweeView img;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.nick)
        private TextView nick;
        @ViewIn(R.id.cover)
        private RelativeLayout cover;
        @ViewIn(R.id.chapter_info)
        private TextView chapter;
        @ViewIn(R.id.icon_delete)
        private ImageButton delete;

        public ViewHolders(View view) {
            super(view);
        }
    }


}
