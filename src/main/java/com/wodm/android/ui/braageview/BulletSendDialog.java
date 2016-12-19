package com.wodm.android.ui.braageview;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.adapter.TextAdapter;
import com.wodm.android.adapter.newadapter.BulletsPositionAdapter;
import com.wodm.android.view.newview.HorizontalListView;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by songchenyu on 16/9/28.
 */

public class BulletSendDialog extends DialogFragment implements TextAdapter.setColorListener,BulletsPositionAdapter.setPositionListener {

    static BulletSendDialog mBulletSendDialog;
    private static SendBarrage sendBarrage;
    private EditText et_content;
    private static ArrayList<String> listdata=new ArrayList<>();
    private TextAdapter textadapter;
    private BulletsPositionAdapter positionAdapter;
    private TextView tv_select_color;
    private String color="#ffffff";
    private int bullet_position=1;

    /**
     */
    public static BulletSendDialog newInstance(SendBarrage send) {
        mBulletSendDialog = new BulletSendDialog();
        sendBarrage=send;

        return mBulletSendDialog;
    }

    @Override
    public void initColor(int clickPosition) {
        color=listdata.get(clickPosition);
        int color= Color.parseColor(listdata.get(clickPosition));
        tv_select_color.setTextColor(color);
    }

    @Override
    public void initPosition(int position) {
        bullet_position=position+1;
    }

    public interface SendBarrage{
        public void addBullet(String content, String color, int position,TextView textView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Dialog_MinWidth);
        if (listdata.size()==0){
            for (int i = 0; i < 7; i++) {
                String color="#ffffff";
                if (i==1){
                    color="#27b1f0";
                }else if (i==2){
                    color="#a40035";
                }else if (i==3){
                    color="#8956a1";
                }else if (i==4){
                    color="#f59210";
                }else if (i==5){
                    color="#f5c92f";
                }else if (i==6){
                    color="#008b2e";
                }
                listdata.add(color);
            }
        }
    }

    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        initDialog();
    }
    private void initDialog(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_content, 0); //显示软键盘
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); //显示软键盘
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_send_bullet, container, false);
        et_content = (EditText) v.findViewById(R.id.bullet_content);
        final HorizontalListView hor_lv= (HorizontalListView) v.findViewById(R.id.hor_lv);
        HorizontalListView hor_lv_position= (HorizontalListView) v.findViewById(R.id.hor_lv_position);
        tv_select_color= (TextView) v.findViewById(R.id.tv_select_color);
        textadapter=new TextAdapter(getActivity(),listdata);
        hor_lv.setAdapter(textadapter);
        positionAdapter=new BulletsPositionAdapter(getActivity());
        hor_lv_position.setAdapter(positionAdapter);
        textadapter.setTextColorListener(this);
        positionAdapter.setPositionListener(this);
        tv_select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if (hor_lv.getVisibility()==View.INVISIBLE){
                       hor_lv.setVisibility(View.VISIBLE);
                   }else {
                       hor_lv.setVisibility(View.INVISIBLE);
                   }
            }
        });
        final TextView send = (TextView) v.findViewById(R.id.send_bullet);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    send.setEnabled(false);
                    String content=et_content.getText().toString();
                    if (content.length()>50){
                        send.setEnabled(true);
                        Toast.makeText(getActivity(), "亲,最多只能输入50个字哦!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    sendBarrage.addBullet(content,color,bullet_position,send);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(send.getApplicationWindowToken(), 0);
            }
        });


        return v;
    }
}
