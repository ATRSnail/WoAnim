package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.MedalInfoBean;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.UpdataMedalInfo;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by songchenyu on 16/10/11.
 */
@Layout(R.layout.aty_my_medal)
public class MyMedalActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {
    @ViewIn(R.id.back_achieve)
    AtyTopLayout atyTopLayout;
    @ViewIn(R.id.activity_my_medal)
    ScrollView scrollView;
    @ViewIn(R.id.ll_attendance_medal)
    LinearLayout ll_attendance_medal;
    @ViewIn(R.id.ll_comment_medal)
    LinearLayout ll_comment_medal;
    @ViewIn(R.id.ll_register_medal)
    LinearLayout ll_register_medal;
    static final int ATTENDANCE = R.id.ll_attendance_medal;
    static final int COMMENT = R.id.ll_comment_medal;
    static final int REGISTER = R.id.ll_register_medal;
    int medalType = 0;
    private int medalImage[] = new int[]{R.mipmap.comer_achieve, R.mipmap.normal_forget_achieve, R.mipmap.violet_forget_achieve, R.mipmap.driver_achieve};
    private int clickedImage[] = new int[]{R.mipmap.click_commer_medal, R.mipmap.click_forget_medal, R.mipmap.click_violet_medal, R.mipmap.click_driver_medal};
    private int not_Image[] = new int[]{R.mipmap.not_commer_medal, R.mipmap.not_forget_medal, R.mipmap.not_forget_medal, R.mipmap.not_driver_medal};
    List<MedalInfoBean.DataBean> dataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atyTopLayout.setOnTopbarClickListenter(this);

        if (Constants.CURRENT_USER == null) {
            finish();
            return;
        } else {
            medalInfo.getMedalInfo(this, Constants.CURRENT_USER.getData().getAccount().getId());

        }
        if (Constants.MEDALINFOBEAN != null) {
            dataBeanList = Constants.MEDALINFOBEAN.getData();
            downData();
        } else {
            initLinearLayout(ll_attendance_medal, ATTENDANCE, 0);
        }

    }

    UpdataMedalInfo medalInfo = new UpdataMedalInfo() {

        @Override
        public void getMedalInfo(MedalInfoBean bean) {
            Constants.MEDALINFOBEAN = bean;

        }
    };

    private void downData() {
        /** medalSource 勋章来源(1：签到 2：节日活动 3：购买vip 4：新手任务 5：线下活动)
         * @param medalType 1：新手勋章 2：vip勋章 3：vvip勋章
         * 4：签到1天  5：签到3天 6：签到7天 7：签到30天 8：签到3个月 9：签到6个月 10：签到1年
         * @return
         */


        for (int i = 0; i < dataBeanList.size(); i++) {
            medalType = dataBeanList.get(i).getMedal().getMedalType();
            int medalScore = dataBeanList.get(i).getMedal().getMedalSource();
            initMedal(medalScore, medalType);
        }
    }

    private void initMedal(int medalScore, int medalType) {
        switch (medalScore) {
            case 1:
                initLinearLayout(ll_attendance_medal, ATTENDANCE, medalType);
                break;
            case 2:
//                initLinearLayout(ll_register_medal, REGISTER, medalType);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                initLinearLayout(ll_attendance_medal, ATTENDANCE, medalType);
                break;
        }
    }

    private void initMedalType(int medalType, ToggleButton image, int i) {
        switch (medalType) {
            case 4:
                if (i == 0) {
                    image.setBackgroundResource(medalImage[0]);
                }
                break;
            case 5:
                if (i == 1 || i == 0) {
                    image.setBackgroundResource(medalImage[i]);
                }
                break;
            case 6:
                if (i != 3) {
                    image.setBackgroundResource(medalImage[i]);
                }
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                image.setBackgroundResource(medalImage[i]);
                break;
        }
    }

    private void initLinearLayout(LinearLayout layout, int id, int medalType) {

        for (int i = 0; i < not_Image.length; i++) {
            ToggleButton image = new ToggleButton(this);
            image.setTextOn("");
            image.setTextOff("");
            image.setText("");
            int width = (Tools.getScreenWidth(this) - 60) / 12 * 4;
            int height = (int) ((Tools.getScreenWidth(this) - 60) / 12 * 4 * 1.35);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.setMargins(0, 0, 30, 0);

            image.setBackgroundResource(not_Image[i]);
            initMedalType(medalType, image, i);
            image.setLayoutParams(params);
            image.setTag(i);
            image.setId(id);
            image.setOnClickListener(this);
            layout.addView(image);
        }
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }
    }

    void panduan(LinearLayout linearLayout, int i, int key, int medalType) {
        ToggleButton image = (ToggleButton) linearLayout.getChildAt(i);

        if (key == i) {
            switch (medalType) {
                case 4:
                    if (i == 0) {
                        image.setBackgroundResource(image.isChecked() ? clickedImage[i] : medalImage[i]);
                    }
                    break;
                case 5:
                    if (i == 1 || i == 0) {
                        image.setBackgroundResource(image.isChecked() ? clickedImage[i] : medalImage[i]);
                    }
                    break;
                case 6:
                    if (i != 3) {
                        image.setBackgroundResource(image.isChecked() ? clickedImage[i] : medalImage[i]);
                    }
                    break;
                case 7:
                case 8:
                case 9:
                case 10:

                    image.setBackgroundResource(image.isChecked() ? clickedImage[i] : medalImage[i]);

                    break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (dataBeanList != null && dataBeanList.size() > 0) {

            int key = (int) v.getTag();
            int id = v.getId();

            for (int i = 0; i < not_Image.length; i++) {

                for (int j = 0; j < dataBeanList.size(); j++) {
                    int medalScore = dataBeanList.get(j).getMedal().getMedalSource();
                    switch (id) {
                        case ATTENDANCE:
                            if (medalScore == 1) {
                                panduan(ll_attendance_medal, i, key, dataBeanList.get(j).getMedal().getMedalType());
                            }
                            break;
                        case COMMENT:
//                        panduan(ll_comment_medal, i, key, 0);
                            break;
                        case REGISTER:
//                        panduan(ll_register_medal, i, key, 0);
                            break;
                    }
                }
            }

        }

    }


}
