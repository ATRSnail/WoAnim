package com.wodm.android.fragment.newfragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.CommentAdapter;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.DividerLine;

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
    private ArrayList<CommentBean> commentBeanList;
    private PullToLoadView pullToLoadView;
    private AppActivity appActivity;
    private boolean isLoadMore=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commment, null);
        pullToLoadView = (PullToLoadView) view.findViewById(R.id.pull_list_comm);
        appActivity = new AppActivity();
        DividerLine line = new DividerLine();
        line.setSize(getResources().getDimensionPixelSize(R.dimen.px_1));
        line.setColor(Color.rgb(0xD8, 0xD8, 0xD8));
        pullToLoadView.getRecyclerView().addItemDecoration(line);//设置分割条颜色
        pullToLoadView.setLoadingColor(R.color.colorPrimary);//加载颜色
        commentBeanList = new ArrayList<>();
//        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
//            @Override
//            protected void requestData(final int pager, final boolean b) {
//                //每次加载7条评论
//                if (commentBeanList.size() % 7 == 0 || isLoadMore) {
//                    String url;
//                    if (Constants.CURRENT_USER == null) {
//                        url = Constants.CommentList + resourceId + "&page=" + pager + "&type=" + 1;
//                    } else {
//                        url = Constants.CommentList + resourceId + "&page=" + pager + "&type=" + 1 + "&userId=" + Constants.CURRENT_USER.getData().getAccount().getId();
//                    }
//                    appActivity.httpGet(url, new HttpCallback() {
//
//                        @Override
//                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                            super.doAuthSuccess(result, obj);
//                            try {
//                                ArrayList<CommentBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<CommentBean>>() {
//                                }.getType());
//                                commentBeanList = beanList;
//                                handleData(pager, beanList, CommentAdapter.class, b, null);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
//                            super.doAuthFailure(result, obj);
//                        }
//                    });
//                } else {
//                    isLoadMore = false;
//                    pullToLoadView.setComplete();
//                }
//
//            }
//        });
        return view;
    }
}
