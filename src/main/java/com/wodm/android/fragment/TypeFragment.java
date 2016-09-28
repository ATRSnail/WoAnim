package com.wodm.android.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.ComicAdapter;
import com.wodm.android.adapter.TabTypeAdapter;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.bean.TabItemBean;
import com.wodm.android.bean.TypeBean;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.NoScrollGridView;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.fragment_type)
public class TypeFragment extends TrackFragment {

    @ViewIn(R.id.type_list)
    private View mTypeHeaderOne;

    @ViewIn(R.id.opus_list)
    private PullToLoadView mOpusList;

    private ArrayList<TypeBean> retList;

    String utrdata = "";
    ComicAdapter comicAdapter;

    @Override
    protected void setDatas(Bundle bundle) {

        GridLayoutManager lmGrid = new GridLayoutManager(getActivity(), 3);
        comicAdapter = new ComicAdapter(getActivity(), new ArrayList<ObjectBean>());
        mOpusList.setLoadingColor(R.color.colorPrimary);
        mOpusList.setPullCallback(new PullCallbackImpl(mOpusList, lmGrid) {
            @Override
            protected void requestData(final int pager, final boolean follow) {
                String url = Constants.GET_CATRESOURCE + "?page=" + pager + utrdata;
                HttpUtil.httpGet(getActivity(), url, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
//                            List<ObjectBean> objectBeen=new ArrayList<ObjectBean>();
//                            comicAdapter.setListData(objectBeen);
                            List<ObjectBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ObjectBean>>() {
                            }.getType());
                            utrdata = "";
                            if (beanList.size()==0){
                                ComicAdapter adapter = (ComicAdapter) mOpusList.getRecyclerView().getAdapter();
                                adapter.setListData(new ArrayList<ObjectBean>());
                                adapter.notifyDataSetChanged();
                                mOpusList.setComplete();
                            }else {
                                handleData(pager, beanList, ComicAdapter.class, follow/*, mTypeHeaderOne*/);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        initpageOne();
    }

    private void initpageOne() {
        final NoScrollGridView mTabList = (NoScrollGridView) mTypeHeaderOne.findViewById(R.id.tab_list_type);
        mTabList.setVisibility(View.VISIBLE);
        final ImageButton imageButton = (ImageButton) mTypeHeaderOne.findViewById(R.id.shou_zhe);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (retList != null && retList.size() > 0 && "1".equals(v.getTag())) {
                    ArrayList list = new ArrayList();
                    list.add(retList.get(0));
                    setColums(mTabList, list);
                    imageButton.setTag("0");
                    imageButton.setImageResource(R.mipmap.type_xiala);
                } else {
                    imageButton.setTag("1");
                    setColums(mTabList, retList);
                    imageButton.setImageResource(R.mipmap.type_shouqi);
                }
            }
        });

        ((AppActivity) getActivity()).httpGet(Constants.GET_CATEGORY, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                try {
                    retList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<TypeBean>>() {
                    }.getType());
                    setColums(mTabList, retList);
                    mOpusList.initLoad();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setColums(NoScrollGridView mTabList, List retList) {
        TabTypeAdapter adapter = new TabTypeAdapter(getActivity(), retList);
        mTabList.setAdapter(adapter);
        adapter.setOnClickListener(new TabTypeAdapter.OnClickListener() {
            @Override
            public void onTypaAll(TypeBean bean) {
                if (utrdata.indexOf(bean.getParameter()) >= 0) {
                    utrdata = utrdata.replace(bean.getParameter(), "");
                    String[] datas = utrdata.split("&");
                    utrdata = "";
                    for (String str : datas) {
                        if (!str.startsWith("=") && !TextUtils.isEmpty(str)) {
                            utrdata += ("&" + str);
                        }
                    }
                }
                mOpusList.initLoad();
            }

            @Override
            public void onTypaOne(TabItemBean tabItemBean, TypeBean bean) {
                String data = "&" + bean.getParameter() + "=";
                if (-1 == utrdata.indexOf(data)) {
                    utrdata += (data + tabItemBean.getId());
                } else {
                    utrdata = utrdata.replace(bean.getParameter(), "");
                    String[] datas = utrdata.split("&");
                    utrdata = "";
                    for (String str : datas) {
                        if (!str.startsWith("=") && !TextUtils.isEmpty(str)) {
                            utrdata += ("&" + str);
                        }
                    }
                    utrdata += (data + tabItemBean.getId());
                }
                mOpusList.initLoad();
            }
        });
    }

}