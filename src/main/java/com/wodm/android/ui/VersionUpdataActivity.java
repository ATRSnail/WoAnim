package com.wodm.android.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.utils.UpdateUtils;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.track.annotation.TrackClick;

@Layout(R.layout.activity_version_updata)
public class VersionUpdataActivity extends AppActivity {
    @ViewIn(R.id.version)
    private TextView mVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVersion.setText(getVersionCode());

    }

    @TrackClick(value = R.id.btnSubmit, location = "检查更新", eventName = "检查更新")
    private void checkversionCode(View view) {
        new UpdateUtils(this).checkUpdate(true);
    }


    /*
 * 获取应用的版本号
 */
    private String getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(getPackageName(),
                    0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "unkown";
        }
    }
}