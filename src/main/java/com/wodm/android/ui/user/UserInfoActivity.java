package com.wodm.android.ui.user;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
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
import com.wodm.android.bean.UserBean;
import com.wodm.android.dialog.SexDialog;
import com.wodm.android.tools.JianpanTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.DeviceUtils;
import com.wodm.android.utils.FileUtils;
import com.wodm.android.utils.ImageUtils;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.DateWheelWindow;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.CircularImage;
import org.eteclab.ui.widget.menu.BottomPopupMenu;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wodm.R.id.user_icon;

/**
 * Created by moon1 on 2016/4/30.
 */
@Layout(R.layout.activity_user_info)
public class UserInfoActivity extends AppActivity {
    private static final String TITLE = "个人信息";
    private static final String BTN_RIGHT = "保存";
    @ViewIn(R.id.user_icon)
    private CircularImage mUserIcon;
    @ViewIn(R.id.user_name)
    private TextView mUserName;
    @ViewIn(R.id.user_sex)
    private TextView mUserSex;
    @ViewIn(R.id.user_brithday)
    private TextView mUserBrithday;

    @ViewIn(R.id.user_sign)
    private TextView mUserSign;

    private BottomPopupMenu bottomPopupMenu = null;
    private String mPhotoPath;
    private final static int TAKE_PRICTURE = 0x002;
    private final static int GET_PRICTURE = 0x001;
    private int sex;
    private String str_Birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle(TITLE);
        setTitleRight(BTN_RIGHT);
        onClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfo();
    }

    private void onClick(){
        mTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submint();
            }
        });
    }

    @TrackClick(value = R.id.btn_logout, location = TITLE, eventName = "用户退出登录")
    private void clickLogout(View view) {
        showLogout();
    }

    @TrackClick(value = R.id.layout_brithday, location = TITLE, eventName = "生日设置")
    private void clickBrith(View view) {
        DateWheelWindow wheelWindow = new DateWheelWindow();
        wheelWindow.showPopWindow(UserInfoActivity.this, mContentView, Gravity.BOTTOM, "yyyy年MM月dd日", new DateWheelWindow.DateResultCall() {
            void resultCall(String result) {
                mUserBrithday.setText(result);
                str_Birthday=result;
//                submint("birthday", result);
            }
        });

    }



    @TrackClick(value = R.id.layout_sex, location = TITLE, eventName = "性别设置")
    private void clickSex(View view) {
        final SexDialog dialog = new SexDialog(UserInfoActivity.this);
        dialog.setCheck(Constants.CURRENT_USER.getSex());
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sex=dialog.getSexResult();
                String sexResult="";
                if (sex == 0) {
                    sexResult="保密";
                } else if (sex == 1) {
                    sexResult="男";
                } else if (sex == 2) {
                    sexResult="女";
                }
                mUserSex.setText(sexResult);
//                submint("sex", dialog.getSexResult());
            }
        });
        dialog.show();
    }


    @TrackClick(value = user_icon)
    private void clickIcon(View view) {
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
                        mPhotoPath = FileUtils.saveBitmap(ImageUtils.revitionImageSize(mPhotoPath), mPhotoPath.substring(mPhotoPath.lastIndexOf("/")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String url = Constants.USER_UPLOAD_PORTRAIT + Constants.CURRENT_USER.getUserId();
                    httpUpload(url, null, new File(mPhotoPath), new HttpCallback() {

                        @Override
                        public void doUploadSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doUploadSuccess(result, obj);
                            updataUserInfo.getUserInfo(getApplicationContext(), Constants.CURRENT_USER.getUserId());
                        }

                        @Override
                        public void doUploadFailure(Exception exception, String msg) {
                            super.doUploadFailure(exception, msg);
                        }
                    });
                    break;
                default:
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

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
                    String url = Constants.USER_LOGOUT + "?token=" + Constants.CURRENT_USER.getToken();
                    httpGet(url, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            try {
                                Toast.makeText(UserInfoActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
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

    UpdataUserInfo updataUserInfo = new UpdataUserInfo() {
        @Override
        public void getUserInfo(UserBean bean) {
            Constants.CURRENT_USER = bean;
            setUserInfo();
        }
    };

    private void setUserInfo() {
        if (Constants.CURRENT_USER == null) {
            finish();
            return;
        }
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(mUserIcon, Constants.CURRENT_USER.getPortrait());
        mUserSign.setText(Constants.CURRENT_USER.getAutograph());
        mUserName.setText(Constants.CURRENT_USER.getNickName());
        mUserSign.setHint("留下你的签名");
//        mUserSign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus)
//            }
//        });
        mUserName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
//        mUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//            }
//        });
        if (Constants.CURRENT_USER.getSex() == 0) {
            mUserSex.setText("保密");
        } else if (Constants.CURRENT_USER.getSex() == 1) {
            mUserSex.setText("男");
        } else {
            mUserSex.setText("女");
        }
        mUserBrithday.setText(Constants.CURRENT_USER.getBirthday());
    }

    private void submint() {
        JSONObject object = new JSONObject();
        try {
            object.put("nickName", mUserName.getText().toString());
            object.put("sex", sex);
            object.put("birthday", str_Birthday);
            object.put("autograph", mUserSign.getText().toString());
            object.put("userId", Constants.CURRENT_USER.getUserId());
            Log.e("submint", object.toString() + "modifyName");
//            {"userId":1,"modifyName":"birthday","modifyValue":"2016/01/01"}
            httpPost(Constants.URL_USER, object, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        updataUserInfo.getUserInfo(getApplicationContext(), Constants.CURRENT_USER.getUserId());
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        JianpanTools.HideKeyboard(mUserSign);
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
}