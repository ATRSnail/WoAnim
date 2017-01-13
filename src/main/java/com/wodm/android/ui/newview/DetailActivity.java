package com.wodm.android.ui.newview;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.MyFragmentAdapter;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.dialog.ShareDialog;
import com.wodm.android.fragment.newfragment.CommentFragment;
import com.wodm.android.fragment.newfragment.MuluFragment;
import com.wodm.android.tools.JujiDbTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.home.CartoonReadActivity;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.Tracker;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 动漫画详情页面
 * */
@Layout(R.layout.activity_detail)
public class DetailActivity extends AppActivity  implements AtyTopLayout.myTopbarClicklistenter,View.OnClickListener,MuluFragment.onJiShuNumClickListener {

//    @ViewIn(R.id.dra)
//    DragScaleImageView dragScaleImageView;
    @ViewIn(R.id.tablayout)
   TabLayout tabLayout;
    @ViewIn(R.id.viewpager)
    ViewPager viewpager;
//    @ViewIn(R.id.activity_detail)
//    ScrollView scrollView;
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.include_animdetail_head)
    RelativeLayout animHeadView;
    @ViewIn(R.id.include_cardetail_head)
    RelativeLayout carHeadView;
    @ViewIn(R.id.include_tran_head)
    View headView;
    @ViewIn(R.id.bg)
    ImageView bg;
    private int resourceId = -1;
    private int resourceType = 1;
    private ObjectBean bean = null;

    TextView renqi;
    TextView author;
    TextView score;//评分
    ImageView scoreDesp;//作品评分对应的描述
    ImageView category;
    CheckBox collect;
    ImageView download;
    ImageView share;
    ImageView pho;
    ImageView watch;
    ImageView play;
    private  String TITLE = "动画详情";
    private Dialog dialog=null;
    private float curX;
    private float curY;
    private float downX;
    private float downY;


    private Fragment muLu;
    private Fragment comment;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MyFragmentAdapter tabFragmentAdapter;
    public static ArrayList<ChapterBean> mChapterList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dragScaleImageView.setTouchEventListener(new DragScaleImageView.TouchEventListener() {
//
//            @Override
//            public void onTouchEvent(MotionEvent event) {
//                // TODO Auto-generated method stub
//                // do something here
//            }
//        });
//        // 回弹事件监听
//        dragScaleImageView.setBackScaleListener(new DragScaleImageView.BackScaleListener() {
//
//            @Override
//            public void onBackScale() {
//                // TODO Auto-generated method stub
//                // do something here
//            }
//        });
//        if (scrollView != null) {
//            scrollView.scrollTo(0, 0);
//            scrollView.setFocusable(true);
//            scrollView.setFocusableInTouchMode(true);
//            scrollView.requestFocus();
//        }
        resourceId = getIntent().getIntExtra("resourceId", -1);
        resourceType= getIntent().getIntExtra("resourceType", 1);


        if (resourceId==-1){
            //说明没有此ID
            Toast.makeText(DetailActivity.this,"没有此内容",Toast.LENGTH_SHORT).show();
            finish();
        }
        if (resourceType==1){
            //显示动画
            TITLE = "动画详情";
            initHeaderView(1,animHeadView);
            animHeadView.setVisibility(View.VISIBLE);
            carHeadView.setVisibility(View.GONE);
        }else if (resourceType==2){
            //显示漫画
            TITLE = "漫画详情";
            initHeaderView(2,carHeadView);
            animHeadView.setVisibility(View.GONE);
            carHeadView.setVisibility(View.VISIBLE);
        }
        initData();
        set_topbar.setOnTopbarClickListenter(this);

//        httpGet(Constants.APP_GETATERESOURCECOUNT+resourceId, new HttpCallback() {
//
//            @Override
//            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthSuccess(result, obj);
//                try {
//                    JSONObject jsonObject=new JSONObject(obj.optString("data"));
//                    int playcount=jsonObject.optInt("playCount");
//                    Log.e("AA","****************点击量:"+playcount);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthFailure(result, obj);
//            }
//        });
//        //更新点击量
//        httpGet(Constants.APP_UPDATERESOURCECOUNT + resourceId, new HttpCallback() {
//
//            @Override
//            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthSuccess(result, obj);
//            }
//
//            @Override
//            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthFailure(result, obj);
//            }
//        });
    }



    private void initHeaderView(int i, RelativeLayout view) {
        renqi = (TextView) headView.findViewById(R.id.renqi);
        collect = (CheckBox) headView.findViewById(R.id.collect);
        download = (ImageView) headView.findViewById(R.id.img_download);
        share = (ImageView) headView.findViewById(R.id.img_share);

        collect.setOnClickListener(this);
        download.setOnClickListener(this);
        share.setOnClickListener(this);

        author = (TextView) view.findViewById(R.id.author);
        score = (TextView) view.findViewById(R.id.score);
        scoreDesp = (ImageView) view.findViewById(R.id.scoreDesp);
        category = (ImageView) view.findViewById(R.id.category);
        pho = (ImageView) view.findViewById(R.id.pho);
        if (i==1){
            play = (ImageView) animHeadView.findViewById(R.id.play);
            play.setOnClickListener(this);
        }else {
            watch = (ImageView) carHeadView.findViewById(R.id.watch);
            watch.setOnClickListener(this);
        }


    }

    private void initData() {

        Bundle bundle = new Bundle();
        bundle.putInt("resourceId",resourceId);
        bundle.putInt("resourceType",resourceType);
        muLu =MuluFragment.getInstance(this);
        muLu.setArguments(bundle);


        comment =new CommentFragment();
        comment.setArguments(bundle);
        fragments.add(muLu);
        fragments.add(comment);
        mTitles.add("目录");
        mTitles.add("评论");
        tabFragmentAdapter = new MyFragmentAdapter(fragments,mTitles,getSupportFragmentManager(),getApplicationContext());
        viewpager.setAdapter(tabFragmentAdapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void leftClick() {
           finish();
    }

    @Override
    public void rightClick() {

    }

    /**
     * 获取作品详情
     */
    private void getCarToon() {
        String url = Constants.URL_CARTTON_DETAIL + resourceId;
        if (Constants.CURRENT_USER != null) {
            url += ("?userId=" + Constants.CURRENT_USER.getData().getAccount().getId());
        }
        httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    String message = obj.getString("message");
                    if ("204".equals(obj.getString("code"))){
                       Toast.makeText(DetailActivity.this,message,Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (obj.getString("code").equals("1000")){
                        bean = new Gson().fromJson(obj.getString("data"), ObjectBean.class);
                        setViews(bean);
                        getNewJuji(bean,1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void setViews(ObjectBean bean) {
        set_topbar.setTvTitle(bean.getName());

        renqi.setText(bean.getPopularity());

        author.setText("作者 ："+bean.getAuthor());
        score.setText(bean.getScore()+"");
        switch (bean.getCategoryName()){
           case "校园" :
               category.setImageResource(R.mipmap.img_xy);
               break;
            case "荒诞" :
                category.setImageResource(R.mipmap.img_hd);
                break;
            case "恋爱" :
                category.setImageResource(R.mipmap.img_la);
                break;
            case "冒险" :
                category.setImageResource(R.mipmap.img_mx);
                break;
            case "残酷" :
                category.setImageResource(R.mipmap.img_ck);
                break;
            case "恐怖" :
                category.setImageResource(R.mipmap.img_kb);
                break;
            case "搞笑" :
                category.setImageResource(R.mipmap.img_gx);
                break;
            case "其他" :
                category.setImageResource(R.mipmap.img_qt);
                break;
            case "清新" :
                category.setImageResource(R.mipmap.img_qx);
                break;
            case "经典" :
                category.setImageResource(R.mipmap.img_jd);
                break;
        }
        switch (bean.getScoreDesp()){
           case "糟糕番" :
               scoreDesp.setImageResource(R.mipmap.fan_zg);
               break;
            case "不错番" :
               scoreDesp.setImageResource(R.mipmap.fan_bc);
               break;
            case "神作番" :
               scoreDesp.setImageResource(R.mipmap.fan_shz);
               break;
            case "无感番" :
               scoreDesp.setImageResource(R.mipmap.fan_wg);
               break;
            case "优秀番" :
               scoreDesp.setImageResource(R.mipmap.fan_yx);
               break;
        }
        //bean.getIsCollect() == 1 收藏成功 0 没有收藏
        collect.setChecked(bean.getIsCollect() == 1);
        Glide.with(this).load(bean.getShowImage()).placeholder(R.mipmap.loading).into(bg);
        Glide.with(this).load(bean.getShowImage()).placeholder(R.mipmap.loading).into(pho);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCarToon();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        String eventName = "";
        if (bean==null){
            eventName = "resourceID无内容";
            Toast.makeText(DetailActivity.this,"没有此内容",Toast.LENGTH_SHORT).show();
            finish();
        }else {

            switch (v.getId()) {
                case R.id.watch:
                    eventName = "漫画观看首页";
                    if (bean.getType()==1){
                        startRead(0, bean.getChapter(),0);
                    }else {
                        startRead(0, 1,0);
                    }

                    break;
                case R.id.play:
                    eventName = "动漫观看首页";
                    if (bean.getType()==1){
                        play(0, bean.getChapter());
                    }else {
                        play(0, 1);
                    }


                    break;
                case R.id.img_download:
                    eventName = "弹出下载界面";
                    showDowmData();
                    break;
                case R.id.img_share:
                    eventName = "弹出分享界面";
                    showShare();
                    break;
                case R.id.collect:
                    eventName = "收藏/取消收藏 操作";
                    if (!UpdataUserInfo.isLogIn(DetailActivity.this,true,null)) {
                        Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showCollect();
                    break;
            }
        }
        Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, eventName);
    }

    private void play(int position, int i) {
        if (page!=null& page.size()>0){
            Intent intent = new Intent(DetailActivity.this, AnimPlayActivity.class );
            intent.putExtra("resourceId", resourceId);
            intent.putExtra("index", position);
            intent.putExtra("bean", bean);
            intent.putExtra("ChapterList",page);
            startActivity(intent);
        }else if ((mChapterList != null & mChapterList.size()>0  & position < mChapterList.size()  )) {
            Intent intent = new Intent(DetailActivity.this, AnimPlayActivity.class );
            intent.putExtra("resourceId", resourceId);
            intent.putExtra("index", position);
            intent.putExtra("bean", bean);
            intent.putExtra("ChapterList",mChapterList);
            startActivity(intent);
        }else {
            Toast.makeText(DetailActivity.this,"没有此内容",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 获取作品章节
     * @param bean
     * @param i
     */
    public  void getNewJuji(final ObjectBean bean, int i){
        int sortBy=0;
        if (bean.getType()==1){
            sortBy=1;
        }else {
            sortBy=0;//已完结的
        }
        String url= Constants.NEW_CHAPTER_LIST+resourceId+"&page="+i+"&sortBy="+sortBy;
        new AppActivity().httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    ArrayList<ChapterBean>  list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ChapterBean>>() {
                    }.getType());
                    JujiDbTools.getInstance(DetailActivity.this).insertAll(list,resourceId);//本地保存观看的状态
                    mChapterList.clear();
                    mChapterList.addAll(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public ArrayList<ChapterBean> getmChapterList() {
        return mChapterList;
    }

    public void setmChapterList(ArrayList<ChapterBean> mChapterList) {
        this.mChapterList.clear();
        this.mChapterList.addAll(mChapterList);
    }

    public void startRead(int index ,int num, int position) {
        if (page!=null& page.size()>0){
            Intent  intent = new Intent(DetailActivity.this,CartoonReadActivity.class);
            intent.putExtra("ChapterList",page);
            intent.putExtra("bean", bean);
            intent.putExtra("resourceId", resourceId);
            intent.putExtra("index", index);
            intent.putExtra("watchIndex", num);
            intent.putExtra("position", position);
            startActivity(intent);
        }else if ((mChapterList != null & mChapterList.size()>0  & index < mChapterList.size()  )) {
            Intent  intent = new Intent(DetailActivity.this,CartoonReadActivity.class);
            intent.putExtra("ChapterList",mChapterList);
            intent.putExtra("resourceId", resourceId);
            intent.putExtra("bean", bean);
            intent.putExtra("index", index);
            intent.putExtra("watchIndex", num);
            intent.putExtra("position", position);
            startActivity(intent);
        }else {
            Toast.makeText(DetailActivity.this,"没有此内容",Toast.LENGTH_SHORT).show();
        }

    }
    private void showDowmData() {
    }
    private void showShare() {
        dialog=new ShareDialog(this,bean.getName(),bean.getDesp(),Constants.SHARE_CARREAD_URL + resourceId,bean.getShowImage());
        dialog.show();
    }

    private void showCollect() {
        httpGet(Constants.ULR_COLLECT + Constants.CURRENT_USER.getData().getAccount().getId() + "&resourceId=" + resourceId, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    if (obj.getString("code").equals("1000")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT
                        ).show();
                        collect.setChecked(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                try {
                    if (obj.getString("code").equals("2000")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT
                        ).show();
                        collect.setChecked(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     *
     * @param position  观看点击的位置 0-16
     * @param num    观看的具体集数 例如106集
     * @param i      观看时横向目录被点击的位置
     */
    @Override
    public void clickNum(int position, int num,int i) {

        JujiDbTools.getInstance(DetailActivity.this).update(resourceId);//更新保存观看的状态

        if (resourceType==1){
            play(position,num);
        }else {
            startRead(position,num,i);
        }

    }



    ArrayList<ChapterBean> page=new ArrayList<>();
    @Override
    public void updatePager(boolean flag, ArrayList<ChapterBean> page) {
        if (flag){
            JujiDbTools.getInstance(DetailActivity.this).insertAll(page,resourceId);//本地保存观看的状态
            this.page.clear();
            this.page.addAll(page);
        }
    }


}
