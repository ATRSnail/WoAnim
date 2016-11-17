package com.wodm.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wodm.R;
import com.wodm.android.bean.CarBean;
import com.wodm.android.tools.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2016/4/30.
 */

public class ReadCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<CarBean> mData = new ArrayList<>();
    private static int type=0;
    private int screenWidth=0,screenHigh=0;

//    List<String> mUrls = new ArrayList();


    public ReadCarAdapter(Context context) {
        mContext = context;
//        mUrls.add("http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg");
//        mUrls.add("http://c.hiphotos.baidu.com/image/pic/item/30adcbef76094b36de8a2fe5a1cc7cd98d109d99.jpg");
//        mUrls.add("http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg");
//        mUrls.add("http://g.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d5277fd5d0628535e5dd6f4a.jpg");
//        mUrls.add("http://e.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7e41f5cfe760e0cf3d6cad6ee.jpg");
//        mUrls.add("http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg");
//        mUrls.add("http://e.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31c1badd5a685d6277f9e2ff81e.jpg");
//        mUrls.add("http://www.huabian.com/uploadfile/2014/1202/20141202025659854.jpg");
//        mUrls.add("http://www.huabian.com/uploadfile/2014/1202/20141202025700989.jpg");
//        mUrls.add("http://g.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c87a3add4d52a6059252da61e.jpg");
//        mUrls.add("http://a.hiphotos.baidu.com/image/pic/item/f2deb48f8c5494ee5080c8142ff5e0fe99257e19.jpg");
//        mUrls.add("http://f.hiphotos.baidu.com/image/pic/item/4034970a304e251f503521f5a586c9177e3e53f9.jpg");
//        mUrls.add("http://b.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fbb3586c0168224f4a20a4dd7e.jpg");
//        mUrls.add("http://img2.xkhouse.com/bbs/hfhouse/data/attachment/forum/corebbs/2009-11/2009113011534566298.jpg");
//        mUrls.add("http://a.hiphotos.baidu.com/image/pic/item/e824b899a9014c087eb617650e7b02087af4f464.jpg");
//        mUrls.add("http://c.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de1e296fa390eef01f3b29795a.jpg");
//        mUrls.add("http://d.hiphotos.baidu.com/image/pic/item/b58f8c5494eef01f119945cbe2fe9925bc317d2a.jpg");
//        mUrls.add("http://h.hiphotos.baidu.com/image/pic/item/902397dda144ad340668b847d4a20cf430ad851e.jpg");
//        mUrls.add("http://b.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea5c0e3c23d139b6003bf3b374.jpg");
//        mUrls.add("http://a.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a292d2472199d25bc315d607c7c.jpg");
//        mUrls.add("http://b.hiphotos.baidu.com/image/pic/item/e824b899a9014c08878b2c4c0e7b02087af4f4a3.jpg");
//        mUrls.add("http://g.hiphotos.baidu.com/image/pic/item/6d81800a19d8bc3e770bd00d868ba61ea9d345f2.jpg");
    }

    public List<CarBean> getData() {
        return mData;
    }

    public void setListData(List<CarBean> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolders(LayoutInflater.from(mContext).inflate(R.layout.adapter_readcar,parent,false));
    }

    /**
     * 0代表横屏  上下
     * 1代表横屏  左右
     * 2代表竖屏  上下
     * 3代表竖屏  左右
     * @param type
     */
    public void setType(int type){
        this.type=type;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolders holders = (ViewHolders) holder;
        final CarBean bean = mData.get(position);
//        Uri url = bean.getContentUrl().startsWith("http") ? Uri.parse(bean.getContentUrl()) :
//                Uri.fromFile(new File(bean.getContentUrl()));
        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holders.imageView.getLayoutParams();
//        int org = mContext.getResources().getConfiguration().orientation;
        if (screenWidth==0||screenHigh==0){
            screenWidth= Tools.getScreenWidth((Activity) mContext);
            screenHigh= Tools.getScreenHeight((Activity) mContext);
        }
//        if (org == Configuration.ORIENTATION_PORTRAIT) {
//            type=4;
//            params.height =(int)(DeviceUtils.getScreenWH((Activity) mContext)[1]);
//            params.width = DeviceUtils.getScreenWH((Activity) mContext)[0];
//        } else {
//            params.height = DeviceUtils.getScreenWH((Activity) mContext)[1]*3;
//            params.width = DeviceUtils.getScreenWH((Activity) mContext)[0];
//            if (type==1){
//                params.height = DeviceUtils.getScreenWH((Activity) mContext)[1];
//                params.width = DeviceUtils.getScreenWH((Activity) mContext)[0]/3;
//            }
//        }
        Glide.with(mContext).load(bean.getContentUrl()).asBitmap().placeholder(R.mipmap.loading).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
               int img_width=resource.getWidth();
               int img_height=resource.getHeight();
               LinearLayout.LayoutParams img_params = (LinearLayout.LayoutParams) holders.imageView.getLayoutParams();
                float num=0;
                if (type==0){
                    int width=screenWidth-10;
//                    if (width>img_width){
//                        num=(float)img_width/width;
//                    }else {
                        num=(float)width/img_width;
//                    }
                    int height=(int)(num*img_height);
                    img_params.width=width;
                    img_params.height=height;
                }else if (type==1){
                    int height =screenHigh-5;
                    if (height>img_height){
                        num=(float)img_height/height;
                    }else {
                    num=(float)height/img_height;
                    }
                    int width=(int)(num*img_width);
                    img_params.width=width;
                    img_params.height=height;
                }
//                if (type==1||type==3){
//                    int height=screenHigh-5;
//                    if (height>img_height){
//                        num=((float)height/img_height);
//                    }else {
//                        num=((float)img_height/height);
//
//                    }
//                    int width=(int)(num*img_width);
//                    img_params.width=height;
//                    img_params.height=width;
//               } else if (type==2||type==0){
//                    int width=screenWidth-10;
//                    if (width>img_width){
//                        num=(float)img_width/width;
//                    }else {
//                        num=(float)width/img_width;
//                    }
//                    int height=(int)(num*img_height);
//                    img_params.width=width;
//                    img_params.height=height;
//
//                }else if (type==3){
//                    int width=screenWidth-10;
//                    if (width>img_width){
//                        num=(float)img_width/width;
//                    }else {
//                        num=(float)width/img_width;
//                    }
//                    int height=(int)(num*img_height);
//                    img_params.width=width;
//                    img_params.height=height;
//
//                }
//                else {
//                    int width=screenWidth-5;
//                    int height=screenHigh;
//                    params.width=width;
//                    params.height=height;
//                }
                if (resource!=null){
                    holders.img_read.setImageBitmap(resource);
                    holders.img_read.setLayoutParams(img_params);
                }
            }
        });
//        Glide.with(mContext).load(bean.getContentUrl()).asBitmap().placeholder(R.mipmap.loading).into(holders.img_read);
//        params.gravity= Gravity.CENTER;
        holders.img_read.setLayoutParams(params);
//        holders.imageView.setImageURI(url);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolders extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageView;
        private ImageView img_read;

        public ViewHolders(android.view.View view) {
            super(view);
            init(view);
        }

        private void init(View view) {
            imageView = (SimpleDraweeView) view.findViewById(R.id.read_img);
            img_read = (ImageView) view.findViewById(R.id.img_read);
        }
    }


//    public ReadCarAdapter(Context context, List<CarBean> data) {
//        super(context, data);
//    }
//
//    @Override
//    protected RecyclerView.ViewHolder generateViewHolder(View view) {
//        return new ViewHolders(view);
//    }
//
//    @Override
//    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
//        ViewHolders holders = (ViewHolders) viewHolder;
//        CarBean bean = mData.get(i);
////        holders.button2.setText(bean.getChapterId() + "-->" + bean.getContentUrl() + "");
//        new AsyncImageLoader(mContext, R.mipmap.loading, R.mipmap.loading).
//                display(holders.imageView, bean.getContentUrl()/*
//                        "http://n.kukudm.com/kuku5comic5/msdfl/11/comic.kukudm.com_001036.jpg"*/);
//    }
//
//    class ViewHolders extends BaseViewHolder {
//        @ViewIn(R.id.read_img)
//        private ImageView imageView;
//        public ViewHolders(View view) {
//            super(view);
//        }
//    }
}
