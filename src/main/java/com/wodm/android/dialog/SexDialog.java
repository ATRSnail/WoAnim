package com.wodm.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;

import com.wodm.R;

/**
 * Created by json on 2016/4/27.
 */
public class SexDialog extends Dialog {
    private RadioGroup radioGroup;

    public SexDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_sex);
        radioGroup = (RadioGroup) findViewById(R.id.sex_group);
    }

    public int getSexResult() {
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == R.id.girl) {
            return 2;
        } else if (id == R.id.man) {
            return 1;
        } else if (id == R.id.bao) {
            return 0;
        }
        return 0;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        findViewById(R.id.submint).setOnClickListener(onClickListener);
    }

    public void show() {
        super.show();
        Window window = getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }

    public void setCheck(int check) {
        if (check == 1) {
            radioGroup.check(R.id.man);
        } else if (check == 2) {
            radioGroup.check(R.id.girl);
        } else if (check == 0) {
            radioGroup.check(R.id.bao);
        }
    }
}