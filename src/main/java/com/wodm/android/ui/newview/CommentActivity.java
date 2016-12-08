package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.CommentAdapter2;
import com.wodm.android.adapter.newadapter.DianZanAdapter;
import com.wodm.android.adapter.newadapter.SystemInformAdapter;
import com.wodm.android.bean.DianZanBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_comment2)
public class CommentActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,
        View.OnClickListener {
    /**
     * 消息中心的评论
     */
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.choice_bottom)
    LinearLayout choice_bottom;
    @ViewIn(R.id.listView_atwo)
    ListView listView_atwo;

//    private List<CommentBean2> list = new ArrayList<>();
    private CommentAdapter2 adapter;
    MessageUtils messageUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);
        choice_bottom.setOnClickListener(this);
        if (Constants.CURRENT_USER==null){finish();return;}
        messageUtils = new MessageUtils(new CommentAdapter2(),choice_bottom,listView_atwo,set_topbar,CommentActivity.this);
        adapter = new CommentAdapter2(CommentActivity.this, false);


        adapter.setUtils(messageUtils);
//        messageUtils.getLikeMessageList(adapter);
        adapter.setSet_topbar(set_topbar);
        listView_atwo.setAdapter(adapter);
    }

    @Override
    public void leftClick() {
       finish();
    }

    @Override
    public void rightClick() {
        String  text = set_topbar.getTv_right().getText().toString();
        if("完成".equals(text)){
            messageUtils.updateData("勾选",false,View.GONE);
        }else   if("勾选".equals(text)){
            messageUtils.updateData("完成",true,View.VISIBLE);
        }else  if("删除".equals(text)){
//            messageUtils.deleteMessage(adapter.ids);

            if (adapter.delete)
            {
                messageUtils.updateData("勾选",false,View.GONE);
            }
            else {
                messageUtils.updateData("完成",true,View.VISIBLE);
            }

            adapter.ids.clear();
            adapter.delete=false;
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice_bottom:
//                messageUtils.deleteAllMessage(2);
                messageUtils.updateData("勾选",false,View.GONE);
                break;
        }
    }
}
