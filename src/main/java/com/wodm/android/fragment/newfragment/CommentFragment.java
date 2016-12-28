package com.wodm.android.fragment.newfragment;



import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.NewCommentAdapter;
import com.wodm.android.bean.NewCommentBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.newview.SendMsgActivity;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.pulltorefresh.listview.XListView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by ATRSnail on 2016/12/12.
 */


public class CommentFragment extends Fragment implements View.OnClickListener,XListView.IXListViewListener{
    private ArrayList<NewCommentBean> commentBeanList;
    private AppActivity appActivity;
    private boolean isLoadMore=true;
    private XListView listView;
    SwipeRefreshLayout refreshLayout;
    private int resourceId = -1;
    TextView foot;
    Handler handler=new Handler();
    TextView comment;
    int pager=1;
    String url;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commment, container,false);
         listView = (XListView) view.findViewById(R.id.list_comment);
//         refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        comment = (TextView) view.findViewById(R.id.comment);
        comment.setOnClickListener(this);
        appActivity = new AppActivity();
        commentBeanList = new ArrayList<>();
        View footerView =inflater.inflate(R.layout.footerview, null);
        foot = (TextView) footerView.findViewById(R.id.footer);
        listView.addFooterView(footerView);
//        resourceId = mContext.getIntent().getIntExtra("resourceId", -1);//引用评论碎片需要传递resourceId
//        initRefresh();
        if (Constants.CURRENT_USER==null){
            url=Constants.CommentList + 554 + "&page=" + pager+"&type="+1;
        }else {
            url=Constants.CommentList + 554 + "&page=" + pager+"&type="+1+"&userId="+Constants.CURRENT_USER.getData().getAccount().getId();
        }
        initData(url);


        return view;
    }

    private void initData(final String url) {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);//给xListView设置监听
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    ArrayList<NewCommentBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<NewCommentBean>>() {
                    }.getType());
                    commentBeanList = beanList;
                    if(beanList!=null&&beanList.size()>0){
                        if (beanList.size()<7){
                            foot.setText("到底啦");
                        }
                    }else {
                        if (pager==1){
                            foot.setText("暂时没有评论");
                        }else {
                            foot.setText("到底啦");
                        }

                    }
                    listView.setAdapter(new NewCommentAdapter(getActivity(),beanList,url));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

//    private void initRefresh() {
//        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
//        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                  if (refreshLayout.isRefreshing()){
//                      refreshLayout.setRefreshing(false);
//                  }
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comment:
                Intent intent2 = new Intent(getActivity(),SendMsgActivity.class);
                getActivity().startActivity(intent2);
                break;
        }

    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoad();// 必须调用此方法，结束加载状态
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               onLoad();
           }
       },2000);
    }
    void onLoad(){
        // 获得数据后一定要调用onLoad()方法，否则刷新会一直进行，根本停不下来
      listView.stopRefresh();
        listView.stopLoadMore();
    }
}
