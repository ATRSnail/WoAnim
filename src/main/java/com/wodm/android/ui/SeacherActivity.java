package com.wodm.android.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.SeacherAdapter;
import com.wodm.android.adapter.SeacherResultAdapter;
import com.wodm.android.bean.HotWordBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.bean.SeacherBean;
import com.wodm.android.db.WoDbUtils;
import com.wodm.android.tools.JianpanTools;
import com.wodm.android.view.FlowLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.NoScrollGridView;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Layout(R.layout.activity_seacher)
public class SeacherActivity extends AppActivity {

    private final static String TITLE = "搜索界面";
    @ViewIn(R.id.text)
    private EditText mDataText;

    @ViewIn(R.id.search_type)
    private TextView mTypeView;
    @ViewIn(R.id.clear_layout)
    private View mClearView;

    @ViewIn(R.id.clear)
    private View mClear;
    @ViewIn(R.id.pull_list)
    PullToLoadView pullToLoadView;
    @ViewIn(R.id.layout_views)
    private View mLayout;

    @ViewIn(R.id.flow)
    private FlowLayout layouts;

    @ViewIn(R.id.hostory_list)
    private NoScrollGridView hostoryList;
    private SeacherAdapter adapter;
    private List<HotWordBean> hotWordBeanList;

    @TrackClick(value = R.id.search_type, location = TITLE, eventName = "选择类型")
    private void clickType(View view) {
        showLogout(view);
    }


    private void showLogout(View view) {
        final PopupWindow builder = new PopupWindow(this);
        View layout = getLayoutInflater().inflate(R.layout.layout_search_type, null);
        builder.setBackgroundDrawable(new BitmapDrawable());
        builder.setFocusable(true);
        builder.setOutsideTouchable(true);
        builder.setContentView(layout);
        builder.setWidth(-2);
        builder.setHeight(-2);
        TextView mPopBtnOne = (TextView) layout.findViewById(R.id.carToon1);
        TextView mPopBtnTwo = (TextView) layout.findViewById(R.id.carToon2);
        if (view.getTag().toString().equals("1")) {
            mPopBtnOne.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            mPopBtnTwo.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        mPopBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                mTypeView.setText(((TextView) v).getText());
                mTypeView.setTag(v.getTag());

            }
        });
        mClear.setVisibility(mTypeView.getText().length() > 0 ? View.VISIBLE : View.INVISIBLE);
        mPopBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                mTypeView.setText(((TextView) v).getText());
                mTypeView.setTag(v.getTag());
            }
        });
        builder.showAsDropDown(mTypeView);
//        builder.showAsDropDown(mTypeView, 0, 10, Gravity.CENTER);
    }

    String key = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        JianpanTools.HideKeyboard(mDataText);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uphostortList();
        mDataText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //开始搜索
                    startSeacher(mDataText.getText().toString());
                    return true;
                }

                return false;
            }
        });


        mDataText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
//                if (s.length()==0){
//                    clearEdittext();
//                }
            }
        });
        pullToLoadView.setLoadingColor(R.color.colorPrimary);
        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int page, final boolean b) {
                String url=Constants.URL_SEARCH + key + "&page=" + page + "&type=" + mTypeView.getTag().toString()+"&taskType=2&taskValue=5";
                if (Constants.CURRENT_USER!=null){
                    url=url+"&userId="+Constants.CURRENT_USER.getData().getAccount().getId();
                }

                httpGet(url, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            List<ObjectBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ObjectBean>>() {
                            }.getType());
                            pullToLoadView.setVisibility(View.VISIBLE);
                            HolderAdapter adapter = handleData(page, list, SeacherResultAdapter.class, b);
                            if (list.size()==0){
                                adapter.setListData(new ArrayList<ObjectBean>());
                                adapter.notifyDataSetChanged();
                            }
                            if (adapter!=null&&adapter.getItemCount() <= 0){
                                mLayout.setVisibility(View.VISIBLE);
                                Toast.makeText(SeacherActivity.this, "未搜索到相关内容!", Toast.LENGTH_SHORT).show();
                            }
//                            adapter.setOnItemClickListener(new HolderAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(View view, Object o, int i) {
//                                    ObjectBean bn = (ObjectBean) o;
//                                    startActivity(new Intent(getApplicationContext(), AnimDetailActivity.class).putExtra("resourceId", bn.getId()));
//                                }
//                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doRequestFailure(Exception exception, String msg) {
                        super.doRequestFailure(exception, msg);
                        loadOK();
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        loadOK();
                    }
                });
            }
        });
        getHotWord();

    }


    private void getHotWord() {

        httpGet(Constants.HOST + "search/hotword", new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    List<HotWordBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<HotWordBean>>() {
                    }.getType());
                    hotWordBeanList=beanList;
                    setFlowViews(beanList);
                    mLayout.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setFlowViews(List<HotWordBean> beanList) {
        if (beanList != null) {
            layouts.removeAllViews();
            for (HotWordBean bean : beanList) {
                layouts.addView(getView(bean));
            }
        }
    }

    private View getView(HotWordBean item) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 10;
        View view = getLayoutInflater().inflate(R.layout.layout_hot,
                null);
        TextView textView = (TextView) view.findViewById(R.id.service_name);
        textView.setText(item.getName());
        textView.setTag(item);

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HotWordBean item = (HotWordBean) v.getTag();
                startSeacher(item.getName());
                CommonUtil.hideKeyboard(getApplicationContext(), mDataText);
                mDataText.setText(item.getName());
            }
        });
        view.setLayoutParams(lp);
        return view;
    }


    @TrackClick(R.id.clear)
    private void clear(View v) {
        mDataText.setText("");
        setFlowViews(hotWordBeanList);
        uphostortList();
        CommonUtil.hideKeyboard(getApplicationContext(), mDataText);
        mLayout.setVisibility(View.VISIBLE);
        pullToLoadView.setVisibility(View.GONE);
    }



    @TrackClick(R.id.clear_jilu)
    private void clearData(View v) {
        try {
            WoDbUtils.initialize(getApplicationContext()).deleteAll(SeacherBean.class);
            uphostortList();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void uphostortList() {
        try {
            List<SeacherBean> list = WoDbUtils.initialize(getApplicationContext()).findAll(Selector.from(SeacherBean.class).orderBy("createTime", true));
            adapter = new SeacherAdapter(this, list);
            adapter.setOnItemClickListener(new HolderAdapter.OnItemClickListener<SeacherBean>() {
                @Override
                public void onItemClick(View view, SeacherBean seacherBean, int i) {
                    startSeacher(seacherBean.getContent());
                }
            });
            mClearView.setVisibility(list != null && list.size() > 0 ? View.VISIBLE : View.GONE);
            hostoryList.setAdapter(adapter);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @TrackClick(R.id.start_sou)
    private void submint(View v) {
        if (!TextUtils.isEmpty(mDataText.getText().toString())) {
            startSeacher(mDataText.getText().toString());
        }
    }

    private void saveSeacherHos(String data) {
        try {
            SeacherBean seacherBean = new SeacherBean();
            seacherBean.setContent(data);
            seacherBean.setCreateTime(System.currentTimeMillis());
            WoDbUtils.initialize(getApplicationContext()).delete(SeacherBean.class, WhereBuilder.b().and("content", "=", seacherBean.getContent()));
            WoDbUtils.initialize(getApplicationContext()).save(seacherBean);
            uphostortList();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void startSeacher(final String key) {
        if (TextUtils.isEmpty(key.trim())) {
            Toast.makeText(getApplicationContext(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        saveSeacherHos(key);
        CommonUtil.hideKeyboard(getApplicationContext(), mDataText);
        mLayout.setVisibility(View.GONE);
        this.key = key;
        pullToLoadView.initLoad();
    }

}