package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.WalletAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Layout(R.layout.activity_my_wallet)
public class MyWalletActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {

    @ViewIn(R.id.scrllow_mywallet)
    private ScrollView scrllow_mywallet;
    @ViewIn(R.id.back_wallet)
    private AtyTopLayout topLayout;
    WalletAdapter adapter;
    private String[] level = new String[]{"等级", "VIP", "VIP", "VIP"};
    private String[] rule = new String[]{"当您的等级越高，您的积分会根据等级成倍增长",
            "即可开通VIP特权可享受双倍积分优惠", "当您的等级越高，您的积分会根据等级成倍增长",
            "即可开通VIP特权可享受双倍积分优惠"};

    private List<Map<String, String>> list;
    @ViewIn(R.id.score_wallet)
    private TextView score_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        topLayout.setOnTopbarClickListenter(this);
    }

    private void init() {
        if (Constants.CURRENT_USER == null) {
            finish();
            return;
        }

        UserInfoBean.DataBean dataBean = Constants.CURRENT_USER.getData();
        String score = String.valueOf(dataBean.getScore());
        if (!TextUtils.isEmpty(score)) {
            score_wallet.setText(score);
        }


        scrllow_mywallet.scrollTo(0, 0);
        scrllow_mywallet.setFocusable(true);
        scrllow_mywallet.setFocusableInTouchMode(true);
        scrllow_mywallet.requestFocus();
        topLayout.setOnTopbarClickListenter(this);
        list = new ArrayList<>();
        for (int i = 0; i < level.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("level", level[i]);
            map.put("rule", rule[i]);
            list.add(map);
        }
        adapter = new WalletAdapter(getApplicationContext(), list);

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
