package com.wodm.android.ui.user;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;


@Layout(R.layout.activity_contact_us)
public class ContactUsActivity extends AppActivity {

    private final static String Title = "关于我们";
    @ViewIn(R.id.version)
    private TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle(Title);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            mVersion.setText("版本号:"+packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}