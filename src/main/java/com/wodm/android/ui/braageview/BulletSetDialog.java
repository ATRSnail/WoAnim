package com.wodm.android.ui.braageview;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.utils.Preferences;

/**
 * Created by songchenyu on 16/12/1.
 */

public class BulletSetDialog extends DialogFragment {
    private SeekBar seekbar_toumingdu;
    private TextView tv_toumingdu,tv_sudu;
    private ImageView img_sudu_line;
    final int RIGHT = 0;
    final int LEFT = 1;
    private ImageView img_white_select;
    private View view,view_left,view_right;
    private static setBulletSetDialogListener listener;

    static BulletSetDialog mBulletSendDialog;
    public static BulletSetDialog newInstance(setBulletSetDialogListener listener1){
        listener=listener1;
        mBulletSendDialog=new BulletSetDialog();
        return mBulletSendDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Dialog_MinWidth);

    }
    public void setBulletListener(setBulletSetDialogListener listener){
        this.listener=listener;
    }

    public interface setBulletSetDialogListener{
        public void setDialogListener(int progress);
    }

    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.BOTTOM);
    }
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.bulletsetdialog, container, false);
        final LinearLayout ll_bulletsdialog= (LinearLayout) v.findViewById(R.id.ll_bulletsdialog);
        final LinearLayout ll_danmu_sudu= (LinearLayout) v.findViewById(R.id.ll_danmu_sudu);
        view= (View) v.findViewById(R.id.view);
        view_left= (View) v.findViewById(R.id.view_left);
        view_right= (View) v.findViewById(R.id.view_right);
        img_white_select= (ImageView) v.findViewById(R.id.img_white_select);
        img_sudu_line= (ImageView) v.findViewById(R.id.img_sudu_line);
        ll_bulletsdialog.getBackground().setAlpha(0);
        seekbar_toumingdu= (SeekBar) v.findViewById(R.id.seekbar_toumingdu);
        tv_toumingdu= (TextView) v.findViewById(R.id.tv_toumingdu);
        tv_sudu= (TextView) v.findViewById(R.id.tv_sudu);
        int bullet_sudu=Preferences.getInstance(getActivity()).getPreference("bullet_sudu", 4);
        if (bullet_sudu==2){
            view.setVisibility(View.GONE);
            view_right.setVisibility(View.GONE);
            initRight();
        }else if (bullet_sudu==4){
            view.setVisibility(View.VISIBLE);
            view_right.setVisibility(View.GONE);
            initRight();
        }
        ll_danmu_sudu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //继承了Activity的onTouchEvent方法，直接监听点击事件
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
//                    if(y1 - y2 > 50) {
//                        Toast.makeText(getActivity(), "向上滑", Toast.LENGTH_SHORT).show();
//                    } else if(y2 - y1 > 50) {
//                        Toast.makeText(getActivity(), "向下滑", Toast.LENGTH_SHORT).show();
//                    } else
                    if(x1 - x2 > 50) {
                        initLeft();
                    } else if(x2 - x1 > 50) {
                        initRight();
                    }
                }
                return true;
            }
        });
        int progress=Preferences.getInstance(getActivity()).getPreference("bullet_toumingdu", 0);
        seekbar_toumingdu.setProgress(progress);
        seekbar_toumingdu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_toumingdu.setText(progress+"%");
                listener.setDialogListener(progress);
                ll_bulletsdialog.getBackground().setAlpha(progress);
                Preferences.getInstance(getActivity()).setPreference("bullet_toumingdu", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return v;
    }
    private void initLeft(){
        boolean isCenter=false;
        if (view.getVisibility()==View.VISIBLE&&view_left.getVisibility()==View.GONE){
            isCenter=true;
        }else if (view.getVisibility()==View.VISIBLE&&view_left.getVisibility()==View.VISIBLE){
            isCenter=false;
        }
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (isCenter){
            view.setVisibility(View.GONE);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            Preferences.getInstance(getActivity()).setPreference("bullet_sudu", 4);
        }else {
            view.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            Preferences.getInstance(getActivity()).setPreference("bullet_sudu", 6);
        }
        img_white_select.setLayoutParams(params);
    }
    private void initRight(){
        boolean isCenter=false;
        if (view.getVisibility()==View.VISIBLE&&view_right.getVisibility()==View.GONE){
            isCenter=true;
        }else if (view.getVisibility()==View.GONE&&view_right.getVisibility()==View.GONE){
            isCenter=false;
            view.setVisibility(View.VISIBLE);
        }
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (isCenter){
            view.setVisibility(View.GONE);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            Preferences.getInstance(getActivity()).setPreference("bullet_sudu",4);
        }else {
            Preferences.getInstance(getActivity()).setPreference("bullet_sudu", 2);
            view.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.ALIGN_RIGHT,R.id.img_sudu_line);
            params.addRule(RelativeLayout.ALIGN_END,R.id.img_sudu_line);
        }
        img_white_select.setLayoutParams(params);
    }
}