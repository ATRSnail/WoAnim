package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.VipOpenAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Layout(R.layout.activity_vip_open)
public class VipOpenActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, AdapterView.OnItemClickListener {
    int clickFlag = 0;
    @ViewIn(R.id.grid)
    MyGridView gridView;
    @ViewIn(R.id.back_vipopen)
    AtyTopLayout atyTopLayout;
    @ViewIn(R.id.pay_vipopen)
    Button pay_btn;
    VipOpenAdapter adapter;
    String[] month = new String[]{"12个月", "6个月", "3个月", "1个月"};
    String[] meng_money = new String[]{"萌币12000", "萌币6000", "萌币4000", "萌币1500"};
    List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        atyTopLayout.setOnTopbarClickListenter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
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
        int money = 0;
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
}
