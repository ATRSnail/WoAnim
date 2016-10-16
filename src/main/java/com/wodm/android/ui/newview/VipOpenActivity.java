package com.wodm.android.ui.newview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.VipOpenAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wodm.android.Constants.CURRENT_USER;

@Layout(R.layout.activity_vip_open)
public class VipOpenActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, AdapterView.OnItemClickListener, View.OnClickListener {
    int clickFlag = 0;
    @ViewIn(R.id.grid)
    MyGridView gridView;
    @ViewIn(R.id.back_vipopen)
    AtyTopLayout atyTopLayout;
    @ViewIn(R.id.pay_vipopen)
    Button pay_btn;
    @ViewIn(R.id.auto_renew)
    ImageButton auto_renew;
    VipOpenAdapter adapter;
    String[] month = new String[]{"12个月", "6个月", "3个月", "1个月"};
    String[] meng_money = new String[]{"萌币12000", "萌币6000", "萌币4000", "萌币1500"};
    List<Map<String, String>> list;
    private boolean flag = true;

    private int money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        atyTopLayout.setOnTopbarClickListenter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        auto_renew.setOnClickListener(this);
        pay_btn.setOnClickListener(this);


    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < month.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("month", month[i]);
            map.put("meng_money", meng_money[i]);
            list.add(map);
        }
        adapter = new VipOpenAdapter(getApplicationContext(), list);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        selectView(view);
        int array[] = new int[]{0, 1, 2, 3};
        for (int i = 0; i < array.length; i++) {
            if (position != i) {
                unSelectView(parent.getChildAt(i));
            }
        }

        switch (position) {
            case 0:
                money = 120;
                break;
            case 1:
                money = 60;
                break;
            case 2:
                money = 30;
                break;
            case 3:
                money = 10;
                break;
        }
        pay_btn.setText("立即支付" + money + "元");


    }

    private void selectView(View view) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_vipopen_dapter);
        TextView month = (TextView) view.findViewById(R.id.month_vipopen);
        TextView meng_mongey = (TextView) view.findViewById(R.id.meng_money_vipopen);
        meng_mongey.setTextColor(ContextCompat.getColor(this, R.color.color_fdd805));
        month.setTextColor(ContextCompat.getColor(this, R.color.color_fdd805));
        layout.setBackgroundResource(R.drawable.tvclick_vipopen);
    }

    private void unSelectView(View view) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_vipopen_dapter);
        TextView month = (TextView) view.findViewById(R.id.month_vipopen);
        TextView meng_mongey = (TextView) view.findViewById(R.id.meng_money_vipopen);
        month.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
        meng_mongey.setTextColor(ContextCompat.getColor(this, R.color.color_cccccc));
        layout.setBackgroundResource(R.drawable.tv_vipopen);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_renew:
                if (flag == true) {
                    auto_renew.setImageResource(R.mipmap.not_renew);
                    flag = false;
                } else if (flag == false) {
                    flag = true;
                    auto_renew.setImageResource(R.mipmap.renew_vipopen);
                }
                break;
            case R.id.pay_vipopen:
//                MyDialog.Builder builder = new MyDialog.Builder(this);
//                builder.setMoney(money);
//                builder.create().show();
                showDialog();
                break;


        }

    }

    private void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您确认要开通VIP吗?");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openVip();
            }
        });
        builder.show();
    }
     private void openVip(){
         httpGet(Constants.APP_GET_OPEN_VIP+CURRENT_USER.getData().getAccount().getId(), new HttpCallback() {

             @Override
             public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                 super.doAuthSuccess(result, obj);
                 String message=obj.optString("message");
                 Toast.makeText(VipOpenActivity.this, ""+message, Toast.LENGTH_SHORT).show();
             }

             @Override
             public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                 super.doAuthFailure(result, obj);
             }
         });
     }


}
