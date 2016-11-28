package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.MineCircleAdapter;
import com.wodm.android.adapter.newadapter.PersionAdapter;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.bean.MedalInfoBean;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.tools.DegreeTools;
import com.wodm.android.tools.DisplayUtil;
import com.wodm.android.tools.MallConversionUtil;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.user.RecordActivity;
import com.wodm.android.utils.DialogUtils;
import com.wodm.android.utils.UpdataMedalInfo;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;

import java.util.List;


/**
 * Created by songchenyu on 16/10/8.
 */
@Layout(R.layout.aty_persion)
public class PersionActivity extends AppActivity implements View.OnClickListener, AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;
    @ViewIn(R.id.gv_comments)
    private MyGridView gv_comments;
    @ViewIn(R.id.gv_quanzi)
    private MyGridView gv_quanzi;
    private PersionAdapter persionAdapter;
    @ViewIn(R.id.scrllow_mine)
    private ScrollView scrollow;
    @ViewIn(R.id.btn_user_info)
    private ImageButton btn_user_info;
    @ViewIn(R.id.user_head_imgs)
    private CircularImage user_head_imgs;
    @ViewIn(R.id.img_vip_circle)
    private ImageView img_vip_circle;
    @ViewIn(R.id.user_txk)
    private ImageView user_txk;
    @ViewIn(R.id.user_gj)
    private ImageView user_gj;
    @ViewIn(R.id.tv_nickname)
    private TextView tv_nickname;
    @ViewIn(R.id.img_sex)
    private ImageView img_sex;
    private MineCircleAdapter mineCircleAdapter;
    @ViewIn(R.id.tv_num)
    TextView tv_num;
    @ViewIn(R.id.empiral_degree)
    TextView empiral_degree;
    @ViewIn(R.id.grade_name_persion)
    TextView grade_name_persion;
    @ViewIn(R.id.my_medal_persion)
    LinearLayout linearLayout;
    @ViewIn(R.id.img_persion_progress)
    ImageView img_persion_progress;
    @ViewIn(R.id.tv_edit)
    TextView editTv;
    @ViewIn(R.id.tv_attention)
    TextView tv_attention;
    @ViewIn(R.id.edit_persion)
    ImageButton edit_persion;
    @ViewIn(R.id.tv_likes)
    TextView tv_likes;

    //    @ViewIn(R.id.btn_degree)
//    private Button btn_degree;
    @ViewIn(R.id.tv_sign)
    private TextView tv_sign;
//    @ViewIn(R.id.degree_progress)
//    private ProgressBar degree_progress;

    int[] medalImage = new int[]{R.mipmap.comer_persion, R.mipmap.forget_persion, R.mipmap.driver_persion, R.mipmap.not_get_persion};
    @ViewIn(R.id.my_medal_persion)
    LinearLayout my_medal_persion;
    List<MedalInfoBean.DataBean> dataBeanList;
    @ViewIn(R.id.show_more_persion)
    TextView show_more_persion;
    @ViewIn(R.id.img_more)
    ImageView img_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        persionAdapter = new PersionAdapter(this);
        gv_comments.setAdapter(persionAdapter);
        mineCircleAdapter = new MineCircleAdapter(this);
        gv_quanzi.setAdapter(mineCircleAdapter);
        scrollow.setFocusable(true);
        scrollow.setFocusableInTouchMode(true);
        scrollow.requestFocus();
        btn_user_info.setOnClickListener(this);
        editTv.setOnClickListener(this);
        edit_persion.setOnClickListener(this);
//        btn_degree.setOnClickListener(this);
        set_topbar.setOnTopbarClickListenter(this);


//        getData();

        if (Constants.getMEDALINFOBEAN() != null) {
            dataBeanList = Constants.getMEDALINFOBEAN().getData();
            if(dataBeanList!=null&&dataBeanList.size()>0)
            {initMyMedal();}
            else {
                initLinearLayout(my_medal_persion, 0);
            }


        } else {
            initLinearLayout(my_medal_persion, 0);
            getData();

        }
//        show_more_persion.setOnClickListener(this);
        img_more.setOnClickListener(this);
    }



    private void initMyMedal() {
                boolean flag =false;
        for (int i = 0; i < dataBeanList.size(); i++) {
            int medalType = dataBeanList.get(i).getMedal().getMedalType();
            int medalScore = dataBeanList.get(i).getMedal().getMedalSource();
            if(medalScore==1){
                initLinearLayout(my_medal_persion, medalType);
                flag =true;
            }else {
                if(!flag)
                initLinearLayout(my_medal_persion, 0);
            }


//            switch (medalScore) {
//                case 1:
//                    initLinearLayout(my_medal_persion, medalType);
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    break;
//                case 4:
//                    break;
//                case 5:
//                    break;
//                default:
//                    initLinearLayout(my_medal_persion, 0);
//                    break;
//            }

        }
    }

    private void initLinearLayout(LinearLayout layout, int medalType) {
        layout.removeAllViews();
        int size = 1;
        switch (medalType) {
            case 4:
                size = 2;
                break;
            case 5:
                size = 3;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                size = 4;
                break;
        }

        for (int i = 0; i < size; i++) {
            ImageView image = new ImageView(this);

            int width = (Tools.getScreenWidth(this) - 60) / 12 * 4;
//            int height = (int) ((Tools.getScreenWidth(this) - 60) / 12 * 4 * 1.35);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 30, 0);
            image.setImageResource(medalImage[medalImage.length - 1]);
            initMedalType(medalType, image, i);
            image.setLayoutParams(params);
            image.setOnClickListener(this);
            layout.addView(image);


        }

    }

    private void initMedalType(int medalType, ImageView image, int i) {
        switch (medalType) {
            case 4:
                if (i == 0) {
                    image.setImageResource(medalImage[0]);
                }
                break;
            case 5:
                if (i == 1 || i == 0) {
                    image.setImageResource(medalImage[i]);
                }
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                if (i != 3) {
                    image.setImageResource(medalImage[i]);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
    }

    private void getData() {
        if (Constants.CURRENT_USER != null) {
            UpdataMedalInfo.getMedalInfo(this, Constants.CURRENT_USER.getData().getAccount().getId());
        }
    }


    private void setUserInfo(){
        if (Constants.CURRENT_USER == null) {
            finish();
            return;
        }
        UserInfoBean.DataBean dataBean=Constants.CURRENT_USER.getData();
        UserInfoBean.DataBean.AccountBean accountBean = dataBean.getAccount();
        if (!TextUtils.isEmpty(accountBean.getPortrait()))
            new AsyncImageLoader(this, R.mipmap.touxiang_moren, R.mipmap.moren_header).display(user_head_imgs, accountBean.getPortrait());


        try {
            MallConversionUtil.getInstace().dealExpression(this,dataBean.getPandentDetail().getNameTXK(),user_txk,dataBean.getPandentDetail().getImgUrlTXK());
        } catch (Exception e) {
            Glide.with(this).load(dataBean.getPandentDetail().getNameGJ()).placeholder(R.mipmap.loading).into(user_txk);
            e.printStackTrace();
        }
        try {
            MallConversionUtil.getInstace().dealExpression(this,dataBean.getPandentDetail().getNameGJ(),user_gj,dataBean.getPandentDetail().getImgUrlGJ());
        } catch (Exception e) {
            Glide.with(this).load(dataBean.getPandentDetail().getNameGJ()).placeholder(R.mipmap.loading).into(user_gj);
            e.printStackTrace();
        }


        int vip=accountBean.getVip();
        if (vip==1){
            img_vip_circle.setImageResource(R.mipmap.vip_circle);
        }else if (vip==2){
            img_vip_circle.setImageResource(R.mipmap.vvip_circle);
        }else {
            img_vip_circle.setVisibility(View.INVISIBLE);
        }

//        tv_attention.setText(dataBean.get);
//        tv_likes.setText();


        tv_nickname.setText(accountBean.getNickName());
        int sex_value = accountBean.getSex();
        if (sex_value == 0) {
            img_sex.setBackgroundResource(R.drawable.sex_radio_baomi);
        } else if (sex_value == 1) {
            img_sex.setBackgroundResource(R.mipmap.sex_man);
        } else {
            img_sex.setBackgroundResource(R.mipmap.sex_women);
        }
//        btn_degree.setText("LV"+accountBean.getGradeValue());
        if (!TextUtils.isEmpty(accountBean.getAutograph() + "")) {
            tv_sign.setText(accountBean.getAutograph() + "");
        }


        int total = dataBean.getAccount().getEmpiricalValue() + dataBean.getNeedEmpirical();

        int progress = 0;
        if (total != 0) {
            progress = dataBean.getAccount().getEmpiricalValue() / total;
        }
//        degree_progress.setProgress(progress);
        int next_num = dataBean.getNextGradeEmpirical();
        int need_num = dataBean.getNeedEmpirical();
        int num_sc = DisplayUtil.px2dip(this, 110);
//        int num = (int) (num_sc * (1 - ((float) need_num / next_num)));
        int num= (int) (110*(1-((float)need_num/next_num)));
//        int num_sc = DisplayUtil.px2dip(this, 30);
//        RelativeLayout.LayoutParams img_progress_params = new RelativeLayout.LayoutParams(num, num_sc);
//        int margin= DisplayUtil.px2dip(this,5);
//        img_progress_params.setMargins(0, margin, 0, margin);
//        img_persion_progress.setLayoutParams(img_progress_params);

        RelativeLayout.LayoutParams img_progress_params = new RelativeLayout.LayoutParams(num, RelativeLayout.LayoutParams.MATCH_PARENT);
        img_progress_params.setMargins(0, 2, 0, 2);
        img_persion_progress.setLayoutParams(img_progress_params);

        String gradename = accountBean.getGradeName();
        if (!TextUtils.isEmpty(gradename)) {
//            grade_name_persion.setText(gradename);
            DegreeTools.getInstance(this).getDegree(this,accountBean.getGradeValue(),grade_name_persion);
        }

        String grad = String.valueOf(accountBean.getGradeValue());
        if (!TextUtils.isEmpty(grad)) {
            tv_num.setText(grad);
        }
        empiral_degree.setText(dataBean.getAccount().getEmpiricalValue() + "/" + dataBean.getNextGradeEmpirical());
//        initMyMedal(accountBean);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_persion:
                break;
            case R.id.btn_user_info:
                startActivity(new Intent(PersionActivity.this, NewUserInfoActivity.class));
                break;
            case R.id.show_more_persion:
            case R.id.img_more:
                startActivity(new Intent(PersionActivity.this, MyMedalActivity.class));
                break;
            case R.id.tv_edit:
                Intent i=new Intent();
                i.putExtra("tid", R.id.my_collcet);
                i.putExtra("title", R.string.collect);
                i.putExtra("position","动画");
                i.setClass(PersionActivity.this, RecordActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
//
//
//    private void loadCollectNum(){
//        httpGet(url + "&type=" + type + "&page=" + page, new HttpCallback() {
//
//            @Override
//            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthSuccess(result, obj);
//                try {
//                    List<ObjectBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ObjectBean>>() {
//                    }.getType());
//
//                    final ComicAdapter adapter = (ComicAdapter) handleData(page, beanList, ComicAdapter.class, b);
//                    edit.setVisibility(adapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
//                    edit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (edit.getTag().toString().equals("0")) {
//                                edit.setText("完成");
//                                edit.setTag("1");
//                                adapter.setOnItemDeleteListener(new ComicAdapter.OnItemDeleteListener() {
//                                    @Override
//                                    public void onItemDelete(int position, ObjectBean bean) {
//                                        delete(type, adapter, position, bean);
//                                    }
//                                });
//                            } else {
//                                edit.setText("编辑");
//                                edit.setTag("0");
//                                adapter.setOnItemDeleteListener(null);
//                            }
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthFailure(result, obj);
//                edit.setVisibility(View.GONE);
//            }
//        });
//    }
}
