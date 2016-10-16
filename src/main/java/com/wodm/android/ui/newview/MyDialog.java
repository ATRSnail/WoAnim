package com.wodm.android.ui.newview;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.bean.UserInfoBean;

/**
 * Created by ATRSnail on 2016/10/14.
 */

public class MyDialog extends Dialog {
    Context context;


    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    static class Builder implements View.OnClickListener {
        private Context context;
        private View mcontentView;
        private LinearLayout alipay;
        private LinearLayout wenxinpay;
        private LinearLayout phonepay;
        private LinearLayout otherpay;
        private int money = 0;

        public void setMoney(int money) {
            this.money = money;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(View contentView) {
            this.mcontentView = contentView;
            return this;
        }


        public MyDialog create() {
            final MyDialog dialog = new MyDialog(context, R.style.CustomDialog);
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = inflater.inflate(R.layout.designdialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            initView(layout);
            return dialog;
        }

        private void initView(View view) {
            alipay = (LinearLayout) view.findViewById(R.id.alipay_dialog);
            wenxinpay = (LinearLayout) view.findViewById(R.id.weixinpay_dialog);
            phonepay = (LinearLayout) view.findViewById(R.id.phonepay_dialog);
            otherpay = (LinearLayout) view.findViewById(R.id.otherpay_dialog);
            wenxinpay.setOnClickListener(this);
            phonepay.setOnClickListener(this);
            otherpay.setOnClickListener(this);
            alipay.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.alipay_dialog:

                    Toast.makeText(context, "支付" + money, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.weixinpay_dialog:
                    Toast.makeText(context, "支付" + money, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.phonepay_dialog:
                    Toast.makeText(context, "支付" + money, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.otherpay_dialog:
                    Toast.makeText(context, "支付" + money, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


}
