package com.wodm.android.fragment.newfragment;



import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.CommentAdapter;
import com.wodm.android.adapter.newadapter.NewCommentAdapter;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.bean.NewCommentBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.DividerLine;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/12.
 */


public class CommentFragment extends Fragment {
    private ArrayList<NewCommentBean> commentBeanList;
    private AppActivity appActivity;
    private boolean isLoadMore=true;
    private  ListView listView;
    SwipeRefreshLayout refreshLayout;
    private int resourceId = -1;
    int pager=1;
    String url;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commment, container,false);
         listView = (ListView) view.findViewById(R.id.list_comment);
         refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        appActivity = new AppActivity();
        commentBeanList = new ArrayList<>();
//        resourceId = mContext.getIntent().getIntExtra("resourceId", -1);//引用评论碎片需要传递resourceId
        initRefresh();
        if (Constants.CURRENT_USER==null){
            url=Constants.CommentList + 554 + "&page=" + pager+"&type="+1;
        }else {
            url=Constants.CommentList + 554 + "&page=" + pager+"&type="+1+"&userId="+Constants.CURRENT_USER.getData().getAccount().getId();
        }
       appActivity.httpGet(url,new HttpCallback(){
           @Override
           public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
               super.doAuthSuccess(result, obj);
               try {
                   ArrayList<NewCommentBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<NewCommentBean>>() {
                   }.getType());
                   commentBeanList = beanList;
                   listView.setAdapter(new NewCommentAdapter(getActivity(),beanList));
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
       });

        return view;
    }

    private void initRefresh() {
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                  if (refreshLayout.isRefreshing()){
                      refreshLayout.setRefreshing(false);
                      pager++;
                  }
            }
        });
    }
}
