package com.wodm.android.fragment.newfragment;




import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import com.wodm.android.ui.newview.SendMsgActivity;
import com.wodm.android.utils.UpdataUserInfo;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.pulltorefresh.listview.XListView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by ATRSnail on 2016/12/12.
 * 新的动漫画详情页面评论的碎片
 */


public class CommentFragment extends Fragment implements View.OnClickListener,XListView.IXListViewListener{
    private ArrayList<NewCommentBean> commentBeanList;
    private AppActivity appActivity;
    private XListView listView;
//    SwipeRefreshLayout refreshLayout;
    private int resourceId = -1;
    TextView foot;
    Handler handler=new Handler();
    TextView comment;
    SendMsgActivity sendMsgActivity =new SendMsgActivity();
    int pager=1;
    String url;
    int totalPages;
    private  boolean update=false;
    NewCommentAdapter adapter;
    private  boolean isLoadMore=false;//是否正在加载更多数据的标记
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        resourceId=  bundle.getInt("resourceId");

        View view = inflater.inflate(R.layout.commment, container,false);
        initData(view,inflater);

//        initRefresh();//下拉刷新

        initComment(pager);
//        loadMore();

//        listView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent arg0) {
//                if(arg0.getAction() == MotionEvent.ACTION_DOWN){
//           listView.getParent().requestDisallowInterceptTouchEvent(true);
//        }
//
//        if(arg0.getAction() == MotionEvent.ACTION_MOVE){
//            listView.getParent().requestDisallowInterceptTouchEvent(true);
//        }
//
//        if(arg0.getAction() == MotionEvent.ACTION_UP){
//            listView.getParent().requestDisallowInterceptTouchEvent(false);
//        }
//                return false;
//            }
//        });

        return view;
    }

    private void initData(View view, LayoutInflater inflater) {
        listView = (XListView) view.findViewById(R.id.list_comment);
        listView.setPullRefreshEnable(true); //设置可以下拉刷新，默认就是true
        listView.setPullLoadEnable(true); //设置可以上拉加载，默认是false
        listView.setXListViewListener(this);
//        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);


        comment = (TextView) view.findViewById(R.id.comment);
        comment.setOnClickListener(this);
        appActivity = new AppActivity();
        commentBeanList = new ArrayList<>();
        commentBeanList.clear();
//        View footerView =inflater.inflate(R.layout.footerview, null);
//        foot = (TextView) footerView.findViewById(R.id.footer);
//        listView.addFooterView(footerView);
    }

    private void loadMore() {
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (SCROLL_STATE_IDLE==scrollState){
//                       if(update) {
//                       if (pager<totalPages){
////                           try {
//                               pager++;
////                               Thread.sleep(1000);
//                               initComment(pager);
////                           } catch (InterruptedException e) {
////                               e.printStackTrace();
////                           }
//
//                       }
//
//                   }else {
//                           if (view.getFirstVisiblePosition()==adapter.getItemId(1)){
//
//                           }
//                       }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//              update=(firstVisibleItem+visibleItemCount==totalItemCount);
//
//            }
//        });
    }

    private void initComment(final int pager) {
        if (Constants.CURRENT_USER==null){
            url=Constants.CommentList + resourceId + "&page=" + pager+"&type="+1;
        }else {
            url=Constants.CommentList + resourceId + "&page=" + pager+"&type="+1+"&userId="+Constants.CURRENT_USER.getData().getAccount().getId();
        }
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    ArrayList<NewCommentBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<NewCommentBean>>() {
                    }.getType());
                    totalPages=obj.getInt("totalPages");
//                     if (totalPages==1){
//                         foot.setText("到底啦");
//                     }else  if (totalPages<1){
//                         foot.setText("暂时没有评论");
//                     } if (totalPages>1){
//                        if (totalPages==pager){
//                            foot.setText("到底啦");
//                        }else {
//                            foot.setText("加载更多......");
//                        }
//                    }
                    commentBeanList.addAll(beanList);
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
            commentBeanList.clear();
            initComment(pager);
        }
    }

//        private void initRefresh() {
//            //下拉刷新的方法
//        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
//        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (refreshLayout.isRefreshing()){
//                            pager=1;
//                            initComment(pager);
//                            refreshLayout.setRefreshing(false);
//                        }
//                    }
//                },2000);
//
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        if (!UpdataUserInfo.isLogIn(getActivity(),true,null)) {
            Toast.makeText(getActivity(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()){
            case R.id.comment:
                Intent intent2 = new Intent(getActivity(),SendMsgActivity.class);
                intent2.putExtra("resourceId",resourceId);
                getActivity().startActivity(intent2);
                break;
        }

    }


    @Override
    public void onRefresh() {
         //上拉刷新
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pager>1){
                    pager--;
                }
                initComment(pager);
                onLoad();
            }
        },1000);



    }

    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
    }

    @Override
    public void onLoadMore() {
//        if (pager>1){
//            if (pager==totalPages){
//                listView.stopRefresh();
//                listView.stopLoadMore();
//            }
//        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pager++;
                initComment(pager);
                onLoad();
            }
        },1000);



    }
}
