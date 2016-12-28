package com.wodm.android.fragment.newfragment;



import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.NewCommentAdapter;
import com.wodm.android.bean.NewCommentBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.newview.SendMsgActivity;
import com.wodm.android.utils.UpdataUserInfo;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.pulltorefresh.listview.XListView;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by ATRSnail on 2016/12/12.
 * 新的动漫画详情页面评论的碎片
 */


public class CommentFragment extends Fragment implements View.OnClickListener{
    private ArrayList<NewCommentBean> commentBeanList;
    private AppActivity appActivity;
    private boolean isLoadMore=true;
    private ListView listView;
    SwipeRefreshLayout refreshLayout;
    private int resourceId = -1;
    TextView foot;
    Handler handler=new Handler();
    TextView comment;
    SendMsgActivity sendMsgActivity =new SendMsgActivity();
    int pager=1;
    String url;
    private  boolean update=false;
    NewCommentAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commment, container,false);
         listView = (ListView) view.findViewById(R.id.list_comment);
         refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        comment = (TextView) view.findViewById(R.id.comment);
        comment.setOnClickListener(this);
//        comment.setVisibility(View.GONE);
        appActivity = new AppActivity();
        commentBeanList = new ArrayList<>();
        View footerView =inflater.inflate(R.layout.footerview, null);
        foot = (TextView) footerView.findViewById(R.id.footer);
        listView.addFooterView(footerView);
//        resourceId = mContext.getIntent().getIntExtra("resourceId", -1);//引用评论碎片需要传递resourceId
        initRefresh();
        initData(pager);
        loadMore();

        return view;
    }

    private void loadMore() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (SCROLL_STATE_IDLE==scrollState){
                       if(update) {
                       if (commentBeanList!=null&&commentBeanList.size()>0){
                           pager++;
                           initData(pager);
                       }

                   }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
              update=(firstVisibleItem+visibleItemCount==totalItemCount);

            }
        });
    }

    void nextData(){
        //获取下一页的数据
        if (Constants.CURRENT_USER==null){
            url=Constants.CommentList + 554 + "&page=" + (pager+1)+"&type="+1;
        }else {
            url=Constants.CommentList + 554 + "&page=" + (pager+1)+"&type="+1+"&userId="+Constants.CURRENT_USER.getData().getAccount().getId();
        }
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    ArrayList<NewCommentBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<NewCommentBean>>() {
                    }.getType());
                    commentBeanList = beanList;
                    if (commentBeanList!=null&&commentBeanList.size()>0){
                        foot.setText("加载更多......");
                    }else {
                        foot.setText("到底啦");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void initData(final int pager) {
        nextData();

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

                        if(beanList!=null&&beanList.size()>0){
                            if (beanList.size()<7){
                                foot.setText("到底啦");
                            }
                        }else {
                            if (pager==1){
                                foot.setText("暂时没有评论");
                            }
                        }
                      adapter =new NewCommentAdapter(getActivity(),beanList,url);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sendMsgActivity.update){
            pager=1;
            initData(pager);
        }
    }

        private void initRefresh() {
            //下拉刷新的方法
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshLayout.isRefreshing()){
                            pager=1;
                            initData(pager);
                            refreshLayout.setRefreshing(false);
                        }
                    }
                },2000);

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!UpdataUserInfo.isLogIn(getActivity(),true,null)) {
            Toast.makeText(getActivity(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()){
            case R.id.comment:
                Intent intent2 = new Intent(getActivity(),SendMsgActivity.class);
                intent2.putExtra("resourceId",554);
                getActivity().startActivity(intent2);
                break;
        }

    }


}
