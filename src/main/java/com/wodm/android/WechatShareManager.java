package com.wodm.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.enetic.share.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wodm.R;
import com.wodm.android.bean.WeixinShareBean;

import org.eteclab.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by songchenyu on 16/12/26.
 */

public class WechatShareManager {
    private static final int THUMB_SIZE = 150;

    public static final int WECHAT_SHARE_WAY_TEXT = 1;   //文字
    public static final int WECHAT_SHARE_WAY_PICTURE = 2; //图片
    public static final int WECHAT_SHARE_WAY_WEBPAGE = 3;  //链接
    public static final int WECHAT_SHARE_WAY_VIDEO = 4; //视频
    public static final int WECHAT_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;  //会话
    public static final int WECHAT_SHARE_TYPE_FRENDS = SendMessageToWX.Req.WXSceneTimeline; //朋友圈

    private static WechatShareManager mInstance;
    private ShareContent mShareContentText, mShareContentPicture, mShareContentWebpag, mShareContentVideo;
    private  IWXAPI mWXApi;
    private  Context mContext;
    private  WeixinShareBean shareBean;

    private WechatShareManager(Context context){
        this.mContext = context;
        //初始化数据
        //初始化微信分享代码
        initWechatShare(context);
    }

    /**
     * 获取WeixinShareManager实例
     * 非线程安全，请在UI线程中操作
     * @return
     */
    public static WechatShareManager getInstance(Context context){
        if(mInstance == null){
            synchronized (WechatShareManager.class){
                if (mInstance==null){
                    mInstance = new WechatShareManager(context);
                }
            }
        }
        return mInstance;
    }

    private void initWechatShare(Context context){
        if (mWXApi == null) {
            mWXApi = WXAPIFactory.createWXAPI(context, org.eteclab.Constants.WX_APPKEY, true);
        }
        mWXApi.registerApp(Constants.WX_APPKEY);
    }

    /**
     * 通过微信分享
     * @param shareType 分享的类型（朋友圈，会话）
     */
    public void shareByWebchat(ShareContent shareContent, int shareType){
        switch (shareContent.getShareWay()) {
            case WECHAT_SHARE_WAY_TEXT:
                shareText(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_PICTURE:
                sharePicture(shareContent, shareType);
                break;
            case WECHAT_SHARE_WAY_WEBPAGE:
//                shareWebPage();
                break;
            case WECHAT_SHARE_WAY_VIDEO:
                shareVideo(shareContent, shareType);
                break;
        }
    }

    private abstract class ShareContent {
        protected abstract int getShareWay();
        protected abstract String getContent();
        protected abstract String getTitle();
        protected abstract String getURL();
        protected abstract int getPictureResource();
    }

    /**
     * 设置分享文字的内容
     * @author chengcj1
     *
     */
    public class ShareContentText extends ShareContent {
        private String content;

        /**
         * 构造分享文字类
         */
        public ShareContentText(String content){
            this.content = content;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_TEXT;
        }

        @Override
        protected String getContent() {
            return content;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return null;
        }

        @Override
        protected int getPictureResource() {
            return -1;
        }
    }

    /*
     * 获取文本分享对象
     */
    public ShareContent getShareContentText(String content) {
        if (mShareContentText == null) {
            mShareContentText = new ShareContentText(content);
        }
        return (ShareContentText) mShareContentText;
    }

    /**
     * 设置分享图片的内容
     * @author chengcj1
     *
     */
    public class ShareContentPicture extends ShareContent {
        private int pictureResource;
        public ShareContentPicture(int pictureResource){
            this.pictureResource = pictureResource;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_PICTURE;
        }

        @Override
        protected int getPictureResource() {
            return pictureResource;
        }

        @Override
        protected String getContent() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return null;
        }
    }

    /*
     * 获取图片分享对象
     */
    public ShareContent getShareContentPicture(int pictureResource) {
        if (mShareContentPicture == null) {
            mShareContentPicture = new ShareContentPicture(pictureResource);
        }
        return (ShareContentPicture) mShareContentPicture;
    }

    /**
     * 设置分享链接的内容
     * @author chengcj1
     *
     */
    public class ShareContentWebpage extends ShareContent {
        private String title;
        private String content;
        private String url;
        private int pictureResource;
        public ShareContentWebpage(String title, String content, String url, int pictureResource){
            this.title = title;
            this.content = content;
            this.url = url;
            this.pictureResource = pictureResource;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_WEBPAGE;
        }

        @Override
        protected String getContent() {
            return content;
        }

        @Override
        protected String getTitle() {
            return title;
        }

        @Override
        protected String getURL() {
            return url;
        }

        @Override
        protected int getPictureResource() {
            return pictureResource;
        }
    }

    /*
     * 获取网页分享对象
     */
    public ShareContent getShareContentWebpag(String title, String content, String url, int pictureResource) {
        if (mShareContentWebpag == null) {
            mShareContentWebpag = new ShareContentWebpage(title, content, url, pictureResource);
        }
        return (ShareContentWebpage) mShareContentWebpag;
    }

    /**
     * 设置分享视频的内容
     * @author chengcj1
     *
     */
    public class ShareContentVideo extends ShareContent {
        private String url;
        public ShareContentVideo(String url) {
            this.url = url;
        }

        @Override
        protected int getShareWay() {
            return WECHAT_SHARE_WAY_VIDEO;
        }

        @Override
        protected String getContent() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return url;
        }

        @Override
        protected int getPictureResource() {
            return -1;
        }
    }

    /*
     * 获取视频分享内容
     */
    public ShareContent getShareContentVideo(String url) {
        if (mShareContentVideo == null) {
            mShareContentVideo = new ShareContentVideo(url);
        }
        return (ShareContentVideo) mShareContentVideo;
    }

    /*
     * 分享文字
     */
    private void shareText(ShareContent shareContent, int shareType) {
        String text = shareContent.getContent();
        //初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        //用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction = buildTransaction("textshare");
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /*
     * 分享图片
     */
    private void sharePicture(ShareContent shareContent, int shareType) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPictureResource());
        WXImageObject imgObj = new WXImageObject(bitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);  //设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("imgshareappdata");
        req.message = msg;
        req.scene = shareType;
        mWXApi.sendReq(req);
    }
    /*
     * 分享链接
     */

    public void shareWebPage(final WeixinShareBean shareBean) {
        this.shareBean=shareBean;
          new Thread(new Runnable() {
              @Override
              public void run() {
                  GetLocalOrNetBitmap(shareBean.getImageUrl());
              }
          }).start();
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "http://chuye.cloud7.com.cn/1402148?from=singlemessage&isappinstalled=0";
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = "哈哈哈哈哈啊哈";
//        msg.description = "尼玛怎么还不现实缩略图";
//        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.left_logo);
//        msg.thumbData = Util.bmpToByteArray(thumb, true);
//        SendMessageToWX.Req req1 = new SendMessageToWX.Req();
//        req1.transaction = this.buildTransaction("");
//        req1.message = msg;
//        req1.scene = 1;
//        mWXApi.sendReq(req1);
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "http://chuye.cloud7.com.cn/1402148?from=singlemessage&isappinstalled=0";
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = "哈哈哈哈哈啊哈";
//        msg.description = "尼玛怎么还不现实缩略图";
////        getImageBitmap("");
////        Bitmap thumb =Bitmap.createScaledBitmap(bitmap, 120, 120, true);//压缩Bitmap
//        Bitmap thumb =BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher);//压缩Bitmap
////        msg.thumbData = Util.bmpToByteArray(thumb, true);
////        if(thumb == null) {
////            Toast.makeText(mContext, "图片不能为空", Toast.LENGTH_SHORT).show();
////        } else {
////            msg.thumbData = Util.bmpToByteArray(thumb, true);
////        }
//
//        msg.setThumbImage(thumb);
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("");
//        req.message = msg;
//        req.scene = 1;
//        mWXApi.sendReq(req);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            shareWebPage1();
        }
    };
    /*
     * 分享链接
     */
    private  void shareWebPage1() {
        if (shareBean==null){
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareBean.getTargurl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareBean.getTitle();
        msg.description = shareBean.getDescription();
        Bitmap thumb=null;
        if (imgbitmap!=null){
            thumb =  Bitmap.createScaledBitmap(imgbitmap, THUMB_SIZE, THUMB_SIZE, true);
            msg.setThumbImage(thumb);
        }else {
            thumb =  BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher);
            msg.setThumbImage(thumb);
            thumb.recycle();
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("");
        req.message = msg;
        req.scene = 1;
        mWXApi.sendReq(req);
        thumb.recycle();
    }
    private static Bitmap imgbitmap=null;
    /**
     * 把网络资源图片转化成bitmap
     * @param url  网络资源图片
     * @return  Bitmap
     */
    public  Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            imgbitmap=bitmap;
            handler.sendEmptyMessage(1);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
    /*
     * 分享视频
     */
    private void shareVideo(ShareContent shareContent, int shareType) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = shareContent.getURL();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();
        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.left_logo);
//		BitmapFactory.decodeStream(new URL(video.videoUrl).openStream());
        /**
         * 测试过程中会出现这种情况，会有个别手机会出现调不起微信客户端的情况。造成这种情况的原因是微信对缩略图的大小、title、description等参数的大小做了限制，所以有可能是大小超过了默认的范围。
         * 一般情况下缩略图超出比较常见。Title、description都是文本，一般不会超过。
         */
        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
        thumb.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene =  shareType;
        mWXApi.sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
