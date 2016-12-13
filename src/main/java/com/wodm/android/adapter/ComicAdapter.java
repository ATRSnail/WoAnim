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

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
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
@Layout(R.layout.adapter_car)
public class ComicAdapter extends HolderAdapter<ObjectBean> {
    private Boolean isShowDelete = false;


    public ComicAdapter(Context context, List<ObjectBean> data) {
        super(context, data);
        setOnItemClickListener(new OnItemClickListener<ObjectBean>() {
            @Override
            public void onItemClick(View view, ObjectBean o, int i) {
                mContext.startActivity(new Intent(mContext, o.getResourceType() == 1 ? AnimDetailActivity.class : CarDetailActivity.class).putExtra("resourceId", o.getId()));
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolders holders = (ViewHolders) viewHolder;
        final ObjectBean bean = mData.get(i);
        Uri uri = Uri.parse(bean.getShowImage());
        holders.img.setImageURI(uri);

        ResizeOptions options = new ResizeOptions(200, 200);
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(options)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(request)
                .setTapToRetryEnabled(true)
                .build();

        holders.img.setController(controller);


        holders.name.setText(bean.getName());
        holders.chapter.setText((bean.getType() == 1 ? "更新至" : "全") + bean.getChapter() + "集");
        holders.nick.setText(bean.getTitle());
        final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holders.cover.getLayoutParams();
        lp.height = (CartoonApplication.getScreenWidth() - 40) / 3;
        holders.cover.setLayoutParams(lp);
        holders.delete.setVisibility(isShowDelete ? View.VISIBLE : View.INVISIBLE);
        if (isShowDelete) {
            holders.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemDelete(i, bean);
                }
            });
        }
    }

    OnItemDeleteListener listener;

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.listener = listener;
        isShowDelete = (this.listener != null);
        notifyDataSetChanged();
    }

    public interface OnItemDeleteListener {
        void onItemDelete(int position, ObjectBean bean);
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