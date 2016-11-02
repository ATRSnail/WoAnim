package com.wodm.android.ui.newview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.umeng.analytics.MobclickAgent;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.dialog.SexDialog;
import com.wodm.android.tools.JianpanTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.DeviceUtils;
import com.wodm.android.utils.FileUtils;
import com.wodm.android.utils.ImageUtils;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.DateWheelWindow;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;
import org.eteclab.ui.widget.menu.BottomPopupMenu;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/10/11.
 */
@Layout(R.layout.aty_newuserinfo)
public class NewUserInfoActivity extends AppActivity implements View.OnClickListener, AtyTopLayout.myTopbarClicklistenter {
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
    private EditText sign_user;
    @ViewIn(R.id.btn_exit_login)
    private Button btn_exit_login;
    @ViewIn(R.id.rl_birthday)
    private RelativeLayout rl_birthday;
    private String str_Birthday;
    private int sex;
    @ViewIn(R.id.rl_sex)
    private RelativeLayout rl_sex;
    private BottomPopupMenu bottomPopupMenu = null;
    private String mPhotoPath;
    private final static int TAKE_PRICTURE = 0x002;
    private final static int GET_PRICTURE = 0x001;
//    boolean saveFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);

//        set_topbar.setRightTextMargin((int) getResources().getDimension(R.dimen.px_40));
        set_topbar.setLeftImageMargin((int) getResources().getDimension(R.dimen.px_40));
        btn_exit_login.setOnClickListener(this);
        if (CURRENT_USER == null) {
            finish();
        }
        img_circle.setOnClickListener(this);
        rl_birthday.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        serUserInfo();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        JianpanTools.HideKeyboard(nickname_user);
        return super.onTouchEvent(event);
    }

    private void serUserInfo() {
        UserInfoBean.DataBean.AccountBean accountBean = CURRENT_USER.getData().getAccount();
        nickname_user.setText(accountBean.getNickName());
        String str_sex = "";
        if (accountBean.getSex() == 0) {
            str_sex = "保密";
        } else if (accountBean.getSex() == 1) {
            str_sex = "男";
        } else {
            str_sex = "女";
        }
        sex_user.setText(str_sex);
        birthday_user.setText(accountBean.getBirthday());
        if (accountBean.getAutograph().trim().equals("")) {
            sign_user.setHint(getResources().getString(R.string.qianming));
        } else {
            sign_user.setText(accountBean.getAutograph());
        }
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

        submint();
        savePhoto();
    }

    private void savePhoto() {
             //头像更换修改

            String url = Constants.USER_UPLOAD_PORTRAIT + Constants.CURRENT_USER.getData().getAccount().getId();
        if(!TextUtils.isEmpty(url)&&!TextUtils.isEmpty(mPhotoPath))
            httpUpload(url, null, new File(mPhotoPath), new HttpCallback() {

                @Override
                public void doUploadSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doUploadSuccess(result, obj);
                    updataUserInfo.getUserInfo(getApplicationContext(), Constants.CURRENT_USER.getData().getAccount().getId());
                }

                @Override
                public void doUploadFailure(Exception exception, String msg) {
                    super.doUploadFailure(exception, msg);
                }
            });

    }

    private void submint() {
        JSONObject object = new JSONObject();
        try {
            object.put("nickName", nickname_user.getText().toString());
            object.put("sex", sex);
            object.put("birthday", str_Birthday);
            object.put("autograph", sign_user.getText().toString());
            object.put("userId", Constants.CURRENT_USER.getData().getAccount().getId());
//            object.put("taskType", "2");
//            object.put("taskValue","4");
//            {"userId":1,"modifyName":"birthday","modifyValue":"2016/01/01"}
            httpPost(Constants.URL_USER, object, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        updataUserInfo.getUserInfo(getApplicationContext(), Constants.CURRENT_USER.getData().getAccount().getId());
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        JianpanTools.HideKeyboard(sign_user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_login:
                //统计登录时，必须在登出时调用此方法
                MobclickAgent.onProfileSignOff();
                showLogout();
                break;
            case R.id.rl_birthday:
                DateWheelWindow wheelWindow = new DateWheelWindow();
                wheelWindow.showPopWindow(NewUserInfoActivity.this, mContentView, Gravity.BOTTOM, "yyyy年MM月dd日", new DateWheelWindow.DateResultCall() {
                    void resultCall(String result) {
                        birthday_user.setText(result);
                        str_Birthday = result;
//                submint("birthday", result);
                    }
                });

                break;
            case R.id.rl_sex:
                final SexDialog dialog = new SexDialog(NewUserInfoActivity.this);
                dialog.setCheck(Constants.CURRENT_USER.getData().getAccount().getSex());
                dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        sex = dialog.getSexResult();
                        String sexResult = "";
                        if (sex == 0) {
                            sexResult = "保密";
                        } else if (sex == 1) {
                            sexResult = "男";
                        } else if (sex == 2) {
                            sexResult = "女";
                        }
                        sex_user.setText(sexResult);
//                submint("sex", dialog.getSexResult());
                    }
                });
                dialog.show();
                break;
            case R.id.img_circle:
                clickIcon();
                break;
        }
    }

    private void clickIcon() {
        List<BottomPopupMenu.TagAndEvent> list = new ArrayList<>();
        list.add(new BottomPopupMenu.TagAndEvent("拍照", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomPopupMenu != null)
                    bottomPopupMenu.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mPhotoPath = "img-" + System.currentTimeMillis() + ".jpg";
                if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                    mPhotoPath = Environment.getExternalStorageDirectory().getPath() + "/" + mPhotoPath;
                } else {
                    return;
                }
//                File file=new File(mPhotoPath);
//                if (!file.exists()){
//                    mPhotoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mPhotoPath;
//                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPhotoPath)));
                startActivityForResult(intent, TAKE_PRICTURE);

            }
        }));
        list.add(new BottomPopupMenu.TagAndEvent("从相册选取", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomPopupMenu != null)
                    bottomPopupMenu.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GET_PRICTURE);
            }
        }));
        list.add(new BottomPopupMenu.TagAndEvent("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomPopupMenu != null)
                    bottomPopupMenu.dismiss();
            }
        }));
        bottomPopupMenu = BottomPopupMenu.showMenu(this, getContentView(), list);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case GET_PRICTURE:
                    mPhotoPath = getSelectMediaPath(data);
                case TAKE_PRICTURE:
                    try {
                        Bitmap bitmap=ImageUtils.revitionImageSize(mPhotoPath);
                        mPhotoPath = FileUtils.saveBitmap(bitmap, mPhotoPath.substring(mPhotoPath.lastIndexOf("/")));
                        img_circle.setImageBitmap(bitmap);
                        Toast.makeText(this, "点击保存后才能更换您喜欢的头像哦!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        super.onActivityResult(requestCode, resultCode, data);


    }

    UpdataUserInfo updataUserInfo = new UpdataUserInfo() {
        @Override
        public void getUserInfo(UserInfoBean bean) {
            Constants.CURRENT_USER = bean;
            serUserInfo();
        }
    };

    private String getSelectMediaPath(Intent data) {
        String path = "";
        Uri uri = data.getData(); // 获取别选中图片的uri
        if (uri.toString().contains("file:///")) {
            path = uri.toString().replace("file:///", "");
        } else {
            String[] filePathColumn = {MediaStore.Images.Media.DATA}; // 获取图库图片路径
            Cursor cursor = getContentResolver().query(uri,
                    filePathColumn, null, null, null); // 查询选中图片路径
            cursor.moveToFirst();
            path = cursor.getString(cursor
                    .getColumnIndex(filePathColumn[0]));
            cursor.close();
        }
        return path;
    }
}
