package com.wodm.android.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.WechatShareManager;
import com.wodm.android.bean.WeixinShareBean;
import com.wodm.android.tools.Tools;

import org.eteclab.OnkeyShare;
import org.eteclab.share.call.ShareResultCall;
import org.eteclab.share.ui.share.ShareQQ;

/**
 * Created by songchenyu on 16/10/30.
 */

public class ShareDialog extends Dialog implements View.OnClickListener {
    private Dialog dialog;
    private View inflate;
    private Context mContext;
    private LinearLayout ll_qq,ll_qq_zone,ll_weixin,ll_weixin_friend;
    private String title,description,targurl,imageUrl;
    public ShareDialog(Context context,String title,String description,String targurl,String imageUrl) {
        super(context);
        mContext=context;
        this.targurl=targurl;
        this.title=title;
        this.description=description;
        this.imageUrl=imageUrl;
    }
    public void show(){
        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(mContext).inflate(R.layout.share_layout, null);
        ll_qq= (LinearLayout) inflate.findViewById(R.id.ll_qq);
        ll_qq_zone= (LinearLayout) inflate.findViewById(R.id.ll_qq_zone);
        ll_weixin_friend= (LinearLayout) inflate.findViewById(R.id.ll_weixin_friend);
        ll_weixin= (LinearLayout) inflate.findViewById(R.id.ll_weixin);
        ll_qq.setOnClickListener(this);
        ll_qq_zone.setOnClickListener(this);
        ll_weixin_friend.setOnClickListener(this);
        ll_weixin.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width= Tools.getScreenWidth((Activity)mContext);
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
    public void dismiss(){
        dismiss();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (dialog!=null&&dialog.isShowing()){
                dialog.dismiss();
            }
        }
    };
    @Override
    public void onClick(View v) {
        OnkeyShare share = new OnkeyShare(mContext);
        share.addShareCall(new ShareResultCall() {
            @Override
            public void onShareSucess() {
                super.onShareSucess();
                handler.sendEmptyMessage(1);
                Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShareCancel() {
                super.onShareCancel();
                handler.sendEmptyMessage(1);
                Toast.makeText(mContext, "用户取消分享", Toast.LENGTH_SHORT).show();
            }
        });

        switch (v.getId()){
            case R.id.ll_weixin:
                WechatShareManager mShareManager1 = WechatShareManager.getInstance(mContext);
                WeixinShareBean weixinShareBean=new WeixinShareBean();
                weixinShareBean.setTargurl(targurl);
                weixinShareBean.setDescription(description);
                weixinShareBean.setImageUrl(imageUrl);
                weixinShareBean.setTitle(title);
                weixinShareBean.setScene(0);
                mShareManager1.shareWebPage(weixinShareBean);
//                ShareWX shareWX=new ShareWX(mContext);
//                shareWX.setScene(1);
//                //0 代表微信好友  1代表朋友圈
//                shareWX.shareWeb(targurl,title,description,imageUrl);
                handler.sendEmptyMessage(1);
                break;
            case R.id.ll_weixin_friend:
                WechatShareManager mShareManager = WechatShareManager.getInstance(mContext);
                WeixinShareBean weixinShareBean1=new WeixinShareBean();
                weixinShareBean1.setTargurl(targurl);
                weixinShareBean1.setDescription(description);
                weixinShareBean1.setImageUrl(imageUrl);
                weixinShareBean1.setTitle(title);
                weixinShareBean1.setScene(1);
                mShareManager.shareWebPage(weixinShareBean1);
//                ShareWX shareWX1=new ShareWX(mContext);
//                shareWX1.setScene(0);
//                shareWX1.shareWeb("http://chuye.cloud7.com.cn/1402148?from=singlemessage&isappinstalled=0",title,description,imageUrl);
                handler.sendEmptyMessage(1);
                break;
            case R.id.ll_qq:
                ShareQQ shareQQ=new ShareQQ(mContext);
                shareQQ.shareWeb(targurl,title,description,imageUrl);
                handler.sendEmptyMessage(1);
                break;
            case R.id.ll_qq_zone:
                ShareQQ shareQQ1=new ShareQQ(mContext);
                shareQQ1.setQZone();
                shareQQ1.shareWeb(targurl,title,description,imageUrl);
                handler.sendEmptyMessage(1);
                break;
        }
    }
}
