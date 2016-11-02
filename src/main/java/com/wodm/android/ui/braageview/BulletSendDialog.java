package com.wodm.android.ui.braageview;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;

/**
 * Created by songchenyu on 16/9/28.
 */

public class BulletSendDialog extends DialogFragment{

    static BulletSendDialog mBulletSendDialog;
    private static SendBarrage sendBarrage;


    /**
     */
    public static BulletSendDialog newInstance(SendBarrage send) {
        mBulletSendDialog = new BulletSendDialog();
        sendBarrage=send;
        return mBulletSendDialog;
    }
    public interface SendBarrage{
        public void addBullet(String content);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Dialog_MinWidth);

    }

    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.TOP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_send_bullet, container, false);

        final EditText et_content = (EditText) v.findViewById(R.id.bullet_content);
        TextView send = (TextView) v.findViewById(R.id.send_bullet);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=et_content.getText().toString();
                if (content.length()>50){
                    Toast.makeText(getActivity(), "亲,最多只能输入50个字哦!", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendBarrage.addBullet(content);
                //TODO send the bullet screen
//                Bullet bullet = new Bullet();
//                bullet.setContent(content.getText().toString().trim());
//                bullet.randomFontColor(getResources().getColor(R.color.red_bullet));
//                bullet.randomFontSize();
//                mInterfaceListener.onSendBulletClick(bullet);
                //TODO reset eidt text

                //TODO close current dialog

            }
        });

        ImageView close = (ImageView) v.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBulletSendDialog.dismiss();
            }
        });

        return v;
    }
}
