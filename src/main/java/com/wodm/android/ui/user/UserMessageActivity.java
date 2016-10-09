package com.wodm.android.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.MessageListAdapter;
import com.wodm.android.bean.MessageBean;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by moon1 on 2016/4/27.
 */
@Layout(R.layout.acitivity_user_message)
public class UserMessageActivity extends AppActivity {
    private static final String TITLE="我的消息";

    @ViewIn(R.id.message_list)
    private PullToLoadView mMessageList;
    @ViewIn(R.id.no_message)
    private TextView mNoMessage;

    private HolderAdapter mMsgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle(TITLE);

        mMessageList.setLoadingColor(R.color.colorPrimary);
        mMessageList.setPullCallback(new PullCallbackImpl(mMessageList) {
            @Override
            protected void requestData(final int pager, final boolean follow) {
                String url = Constants.URL_USER_NOTICE+"?profileId="+Constants.CURRENT_USER.getData().getAccount().getId()+"&page="+pager+"&size=20";
                httpGet(url,new HttpCallback(){
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        try {
                            List<MessageBean> list = new Gson().fromJson(obj.getString("data"),new TypeToken<List<MessageBean>>(){}.getType());
                            if(list==null||list.size()==0){
                                mNoMessage.setVisibility(View.VISIBLE);
                                mMessageList.setVisibility(View.GONE);
                            }else{
                                mNoMessage.setVisibility(View.GONE);
                                mMessageList.setVisibility(View.VISIBLE);
                                mMsgAdapter=handleData(pager,list, MessageListAdapter.class,follow);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        mMessageList.initLoad();
    }
}
