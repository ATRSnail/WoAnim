package com.wodm.android.ui.user;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.dialog.RerestPassDialog;
import com.wodm.android.receiver.AlarmReceiver;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.DataCacheManager;
import com.wodm.android.utils.FileUtils;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.TimeWindow;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.track.annotation.TrackClick;

@Layout(R.layout.activity_us_set)
public class UsSetActivity extends AppActivity {
    Preferences pref;
    private final static String TITLE = "设置";

    @ViewIn(R.id.play_box)
    private CheckBox mPlayBox;
    @ViewIn(R.id.pay_audio)
    private CheckBox mPayAudioBox;
    @ViewIn(R.id.off_time)
    private TextView mOffTime;
    @ViewIn(R.id.clean_cache)
    private TextView mCleanCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = Preferences.getInstance(getApplicationContext());
        mPayAudioBox.setChecked(pref.getPreference("netPlay", false));
        mPlayBox.setChecked(pref.getPreference("ScreenFullPlay", false));
        if (Constants.OFFTIME == null || "".equals(Constants.OFFTIME)) {
            mOffTime.setText("");
        } else {
            mOffTime.setText(Constants.OFFTIME);
        }
        upCache();
        setCustomTitle(TITLE);
        setCustomTitleColor(Color.parseColor("#ffffff"));
    }

    @TrackClick(value = R.id.feek_back, location = TITLE, eventName = "进入意见反馈页面")
    private void clickFeekBack(View view) {
        Intent intent=new Intent();
        intent.setClass(this,FeekBackActivity.class);
        if (!UpdataUserInfo.isLogIn(this, true,intent)) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
    }

    @TrackClick(value = R.id.connect_us, location = TITLE, eventName = "进入关于我们页面")
    private void clickConnectUs(View view) {
        startActivity(new Intent(this, ContactUsActivity.class));
    }

    @TrackClick(value = R.id.pay_audio, location = TITLE, eventName = "2/3/4G播放")
    private void clickNet(View view) {
        pref.setPreference("netPlay", !pref.getPreference("netPlay", false));
        mPayAudioBox.setChecked(pref.getPreference("netPlay", false));
    }

    @TrackClick(value = R.id.play_box, location = TITLE, eventName = "自动全屏播放")
    private void clickScreen(View view) {
        pref.setPreference("ScreenFullPlay", !pref.getPreference("ScreenFullPlay", false));
        mPlayBox.setChecked(pref.getPreference("ScreenFullPlay", false));
    }

    @TrackClick(value = R.id.Reset_pass, location = TITLE, eventName = "修改密码")
    private void clickResetPass(View view) {
        if (!UpdataUserInfo.isLogIn(this, true,null)) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        RerestPassDialog dialog = new RerestPassDialog(this);
        dialog.show();
    }

    @TrackClick(value = R.id.set_off_time, location = TITLE, eventName = "设置定时关闭")
    private void clickSetOff(View view) {
        TimeWindow window = new TimeWindow();
        window.showPopWindow(this, mContentView, Gravity.BOTTOM, new TimeWindow.TimeCall() {
            void resultCall(String result, int hour, int min) {
                mOffTime.setText(result);
                Constants.OFFTIME = result;
                setOff(hour, min);
            }
        });
    }

    @TrackClick(value = R.id.clean_cache_layout, location = TITLE, eventName = "清除本地缓存")
    private void clickCleanCache(View view) {
        DataCacheManager.cleanInternalCache(getApplicationContext());
        DataCacheManager.cleanExternalCache(getApplicationContext());
        mHandler.sendEmptyMessage(0);
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                try{
//                    DataCacheManager.cleanInternalCache(getApplicationContext());
//                    DataCacheManager.cleanExternalCache(getApplicationContext());
//                    mHandler.sendEmptyMessage(0);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            upCache();
        }
    };

    private void setOff(int hour, int min) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("something");
        intent.setType("something");
        intent.setData(Uri.EMPTY);
        intent.addCategory("someting");
        intent.setClass(this, AlarmReceiver.class);
        // 以上给intent设置的四个属性是用来区分你发给系统的闹钟请求的，当你想取消掉之前发的闹钟请求，
        //这四个属性，必须严格相等，所以你需要一些比较独特的属性，比如服务器返回给你的json中某些特定字段。
        //当然intent中也可以放一些你要传递的消息。
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (hour == 0 && min == 0) {
            // Create the same intent, and thus a matching IntentSender, for
            // the one that was scheduled.
            // And cancel the alarm.
            am.cancel(pendingIntent);
        } else {
//发送闹钟请求
            //alarmCount是你需要记录的闹钟数量，必须保证你所发的alarmCount不能相同，最后一个参数填0就可以。

            long timeNow = System.currentTimeMillis();

            long time = timeNow + hour * 60 * 60 * 1000 + min * 60 * 1000;


            am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            //这样闹钟的请求就发送出去了。time是你要被提醒的时间，单位毫秒，注意不是时间差。
            // 第一个参数提醒的需求用我给出的就可以，感兴趣的朋友，可以去google一下，
            //这方面的资料非常多，一共有种，看一下就知道区别了。
        }


    }

    private void upCache() {
        mCleanCache.setText(FileUtils.FormetFileSize(DataCacheManager.countInternalCache(getApplicationContext())
                + DataCacheManager.countExternalCache(getApplicationContext())));
    }
}