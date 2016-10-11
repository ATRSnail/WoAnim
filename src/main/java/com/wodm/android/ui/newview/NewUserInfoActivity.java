package com.wodm.android.ui.newview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.DeviceUtils;
import com.wodm.android.utils.Preferences;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/10/11.
 */
@Layout(R.layout.aty_newuserinfo)
public class NewUserInfoActivity extends AppActivity implements View.OnClickListener,AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;
    @ViewIn(R.id.img_circle)
    private CircularImage img_circle;
    @ViewIn(R.id.nickname_user)
    private TextView nickname_user;
    @ViewIn(R.id.sex_user)
    private TextView sex_user;
    @ViewIn(R.id.birthday_user)
    private TextView birthday_user;
    @ViewIn(R.id.sign_user)
    private TextView sign_user;
    @ViewIn(R.id.btn_exit_login)
    private Button btn_exit_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);
        btn_exit_login.setOnClickListener(this);
        if (CURRENT_USER == null){
            finish();
        }
        UserInfoBean.DataBean.AccountBean accountBean= CURRENT_USER.getData().getAccount();
        nickname_user.setText(accountBean.getNickName());
        String str_sex="";
        if (Constants.CURRENT_USER.getData().getAccount().getSex() == 0) {
            str_sex="保密";
        } else if (Constants.CURRENT_USER.getData().getAccount().getSex() == 1) {
            str_sex="男";
        } else {
            str_sex="女";
        }
        sex_user.setText(str_sex);
        birthday_user.setText(accountBean.getBirthday());
        sign_user.setText(accountBean.getAutograph());
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(img_circle, accountBean.getPortrait());
    }
    private void showLogout() {
        View view = getLayoutInflater().inflate(R.layout.layout_popupwindow, null);
        TextView mPopText = (TextView) view.findViewById(R.id.popup_text);
        TextView mPopTextTitle = (TextView) view.findViewById(R.id.popup_text_title);
        Button mPopBtnOne = (Button) view.findViewById(R.id.popup_btn_one);
        Button mPopBtnTwo = (Button) view.findViewById(R.id.popup_btn_two);
        mPopTextTitle.setText("提示");
        mPopText.setText("确认现在退出登录吗？");
        mPopBtnOne.setText(getResources().getText(R.string.user_sure));
        mPopBtnTwo.setText(getResources().getText(R.string.user_fail));
        final Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        mPopBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = Constants.USER_LOGOUT + "?token=" + Constants.CURRENT_USER.getData().getToken();
                    httpGet(url, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            try {
                                Toast.makeText(NewUserInfoActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Constants.CURRENT_USER = null;
                                Preferences.getInstance(getApplicationContext()).setPreference("token", "");
                                builder.cancel();
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mPopBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        Window window = builder.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = (int) (DeviceUtils.getScreenWH(this)[0] * 0.9); // 宽度
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        builder.getWindow().setGravity(Gravity.CENTER);

        builder.show();
        builder.setCanceledOnTouchOutside(false);
    }
    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {
        Toast.makeText(this, "保存", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit_login:
                showLogout();
                break;
        }
    }
}
