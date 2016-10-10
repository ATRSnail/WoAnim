package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.view.Window;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.WalletAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Layout(R.layout.activity_my_wallet)
public class MyWalletActivity extends AppActivity {
    @ViewIn(R.id.gv_wallet)
    private MyGridView gridView;
    WalletAdapter adapter;
    private String[] level = new String[]{"等级", "VIP", "VIP", "VIP"};
    private String[] rule = new String[]{"当您的等级越高，您的积分会根据等级成倍增长",
            "即可开通VIP特权可享受双倍积分优惠", "当您的等级越高，您的积分会根据等级成倍增长",
            "即可开通VIP特权可享受双倍积分优惠"};

    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        for (int i = 0; i < level.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("level", level[i]);
            map.put("rule", rule[i]);
            list.add(map);
        }
        adapter = new WalletAdapter(getApplicationContext(), list);

        gridView.setAdapter(adapter);
    }


}
