package com.wodm.android.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.utils.ScreenSwitchUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * qiangyu on 1/26/16 15:33
 * csdn博客:http://blog.csdn.net/yissan
 */
public class CommonVideoView extends FrameLayout implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, View.OnTouchListener, View.OnClickListener, Animator.AnimatorListener, SeekBar.OnSeekBarChangeListener {

    private final int UPDATE_VIDEO_SEEKBAR = 1000;
    private final int UPDATE_VIDEO_SEEKBAR_TIME = 1001;
    private Boolean isScreenLock = false;
    private ScreenSwitchUtils screenSwitchUtils;
    private Context context;
    private FrameLayout viewBox;
    private MyVideoView videoView;
    private LinearLayout videoPauseBtn;
    //    private LinearLayout screenSwitchBtn;
    private LinearLayout touchStatusView;
    private LinearLayout videoControllerLayout;
    //    private ImageView screenLock;
    private RelativeLayout videoTitleLayout;
    private ImageView touchStatusImg;
    private ImageView videoPlayImg;
    private ImageView videoPauseImg;
    private TextView touchStatusTime;
    private TextView videoCurTimeText;
    private TextView videoTotalTimeText;
    private SeekBar videoSeekBar;
    private TextView mVideoName;
    private ProgressBar progressBar;
    private ImageView tan_anim;
    private ImageView anim_share;
    public CheckBox more;
    private ImageView anim_dowm;
    private TextView mListAnim;

    private int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    private int duration;
    private String formatTotalTime;

    private Timer timer = new Timer();

    private float touchLastX;
    //定义用seekBar当前的位置，触摸快进的时候显示时间
    private int position;
    private int touchStep = 1000;//快进的时间，1秒
    private int touchPosition = -1;

    private boolean videoControllerShow = true;//底部状态栏的显示状态
    private boolean animation = false;
    private LinearLayout ll_bottom;
    private SendBulletListener sendBulletListener;
    private setTimeDBListener settimeListener;
    private String videoUrl;

    public interface SendBulletListener {
        public void sendBullet();
    }

    public void setSendBulletListener(SendBulletListener listener) {
        this.sendBulletListener = listener;
    }


    /**
     * 是否微横屏
     *
     * @return
     */
    public Boolean isLandscape() {
        return orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            videoHandler.sendEmptyMessage(UPDATE_VIDEO_SEEKBAR);
        }
    };

    private Handler videoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VIDEO_SEEKBAR:
                    if (videoView.isPlaying()) {
                        videoSeekBar.setProgress(videoView.getCurrentPosition());
                    }
                    break;
                case UPDATE_VIDEO_SEEKBAR_TIME:
                    videoViewOnclick();
                    break;
            }
        }
    };

    public CommonVideoView(Context context) {
        this(context, null);
    }

    public CommonVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    public void start(String url){
        start(url,0);
    };
    public void start(String url,int lookTime) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        videoView.stopPlayback();
        videoPauseBtn.setEnabled(false);
        videoSeekBar.setEnabled(false);
        videoPlayImg.setVisibility(View.INVISIBLE);
        videoView.setVideoURI(Uri.parse(url));
        videoPauseImg.setImageResource(R.mipmap.play_stop);
        videoUrl=url;
        if (lookTime!=0){
            videoView.seekTo(lookTime);
        }
        videoView.start();
    }

    public interface setTimeDBListener{
        public void setTime(String playUrl,int time,int totalTime);
    }
    public void setTimeListener(setTimeDBListener listener){
        this.settimeListener=listener;

    }
    public void setFullScreen() {
        orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        touchStatusImg.setImageResource(R.mipmap.anim_fangda);
        this.setLayoutParams(new
                RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setlandLayout(View.VISIBLE);
        videoTitleLayout.setBackgroundColor(Color.argb(0xcc, 0x28, 0x28, 0x28));
    }

    public void setNormalScreen() {

        orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        touchStatusImg.setImageResource(R.mipmap.anim_fangda);
        this.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.px_450)));
        setlandLayout(GONE);
        videoTitleLayout.setBackgroundColor(Color.argb(0x0, 0x0, 0x00, 0x00));
    }


    private void setlandLayout(int v) {

        tan_anim.setVisibility(v);
        anim_share.setVisibility(v);
        more.setVisibility(v);
        /**
         * 将缓存图标隐藏
       anim_dowm.setVisibility(v);*/
//        mListAnim.setVisibility(v);
        videoView.requestLayout();
//        screenLock.setVisibility(v);
        if (this.videoControllerShow) {
            videoHandler.removeMessages(UPDATE_VIDEO_SEEKBAR_TIME);
            videoHandler.sendEmptyMessageDelayed(UPDATE_VIDEO_SEEKBAR_TIME, 10 * 1000);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        View view = inflate(context, R.layout.layout_common_video_view, this);
        view.findViewById(R.id.send_bullet).setOnClickListener(this);
        mListAnim = (TextView) view.findViewById(R.id.list_anim);
        mListAnim.setOnClickListener(this);
        viewBox = (FrameLayout) view.findViewById(R.id.viewBox);
        videoView = (MyVideoView) view.findViewById(R.id.videoView);
        videoPauseBtn = (LinearLayout) view.findViewById(R.id.videoPauseBtn);
//        screenSwitchBtn = (LinearLayout) view.findViewById(R.id.screen_status_btn);
        videoControllerLayout = (LinearLayout) view.findViewById(R.id.videoControllerLayout);
        videoTitleLayout = (RelativeLayout) view.findViewById(R.id.videoTopLayout);
        touchStatusView = (LinearLayout) view.findViewById(R.id.touch_view);
        touchStatusImg = (ImageView) view.findViewById(R.id.touchStatusImg);
        touchStatusTime = (TextView) view.findViewById(R.id.touch_time);
        videoCurTimeText = (TextView) view.findViewById(R.id.videoCurTime);
        videoTotalTimeText = (TextView) view.findViewById(R.id.videoTotalTime);
        videoSeekBar = (SeekBar) view.findViewById(R.id.videoSeekBar);
        videoPlayImg = (ImageView) view.findViewById(R.id.videoPlayImg);
        mVideoName = (TextView) view.findViewById(R.id.video_name);
        videoPlayImg.setVisibility(GONE);
        videoPauseImg = (ImageView) view.findViewById(R.id.videoPauseImg);
        progressBar = (ProgressBar) view.findViewById(R.id.view_bar);
//        screenLock = (ImageView) view.findViewById(R.id.screen_lock);

        tan_anim = (ImageView) view.findViewById(R.id.tan_anim);
        anim_share = (ImageView) view.findViewById(R.id.anim_share);
        more = (CheckBox) view.findViewById(R.id.anim_collect);
        anim_dowm = (ImageView) view.findViewById(R.id.anim_dowm);

        tan_anim.setOnClickListener(this);
        anim_share.setOnClickListener(this);
        more.setOnClickListener(this);
        anim_dowm.setOnClickListener(this);
        view.findViewById(R.id.exit_screen).setOnClickListener(this);

        videoPauseBtn.setOnClickListener(this);
        view.findViewById(R.id.videoNext).setOnClickListener(this);
        videoSeekBar.setOnSeekBarChangeListener(this);
        videoPauseBtn.setOnClickListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnCompletionListener(this);
        view.findViewById(R.id.screen_status_img).setOnClickListener(this);
        videoPlayImg.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });
        }
        //注册在设置或播放过程中发生错误时调用的回调函数。如果未指定回调函数，或回调函数返回false，VideoView 会通知用户发生了错误。
        videoView.setOnErrorListener(this);
        viewBox.setOnTouchListener(this);
        viewBox.setOnClickListener(this);
//        addView(view);
        setNormalScreen();
        screenSwitchUtils =ScreenSwitchUtils.init(context);
        screenSwitchUtils.isPortrait();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        duration = videoView.getDuration();
        int[] time = getMinuteAndSecond(duration);
        videoTotalTimeText.setText(String.format("%02d:%02d", time[0], time[1]));
        formatTotalTime = String.format("%02d:%02d", time[0], time[1]);
        videoSeekBar.setMax(duration);
        progressBar.setVisibility(View.GONE);
        mp.start();
        videoPauseBtn.setEnabled(true);
        videoSeekBar.setEnabled(true);
//        videoPauseImg.setImageResource(R.mipmap.play_start);
        try {
            timer.schedule(timerTask, 0, 1000);
        } catch (Exception e) {

        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        videoView.seekTo(0);
        videoSeekBar.setProgress(0);
        videoPauseImg.setImageResource(R.mipmap.play_start);
        videoPlayImg.setVisibility(View.VISIBLE);
        playEnd();
    }

    /**
     * 一集播放完，自动跳转到下一集
     */
    void playEnd() {
        if (this.call != null)
            this.call.doVideoPlayNext();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!videoView.isPlaying()) {
                    return false;
                }
                float downX = event.getRawX();
                touchLastX = downX;
                Log.d("FilmDetailActivity", "downX" + downX);
                this.position = videoView.getCurrentPosition();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!videoView.isPlaying()) {
                    return false;
                }
                float currentX = event.getRawX();
                float deltaX = currentX - touchLastX;
                float deltaXAbs = Math.abs(deltaX);
                if (deltaXAbs > 1) {
                    if (touchStatusView.getVisibility() != View.VISIBLE) {
                        touchStatusView.setVisibility(View.VISIBLE);
                    }
                    touchLastX = currentX;
                    Log.d("FilmDetailActivity", "deltaX" + deltaX);
                    if (deltaX > 1) {
                        position += touchStep;
                        if (position > duration) {
                            position = duration;
                        }
                        touchPosition = position;
                        touchStatusImg.setImageResource(R.mipmap.ic_fast_forward_white_24dp);
                        int[] time = getMinuteAndSecond(position);
                        touchStatusTime.setText(String.format("%02d:%02d/%s", time[0], time[1], formatTotalTime));
                    } else if (deltaX < -1) {
                        position -= touchStep;
                        if (position < 0) {
                            position = 0;
                        }
                        touchPosition = position;
                        touchStatusImg.setImageResource(R.mipmap.ic_fast_rewind_white_24dp);
                        int[] time = getMinuteAndSecond(position);
                        touchStatusTime.setText(String.format("%02d:%02d/%s", time[0], time[1], formatTotalTime));
                        //mVideoView.seekTo(position);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touchPosition != -1) {
                    videoView.seekTo(touchPosition);
                    touchStatusView.setVisibility(View.GONE);
                    touchPosition = -1;
                    if (videoControllerShow) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private int[] getMinuteAndSecond(int mils) {
        mils /= 1000;
        int[] time = new int[2];
        time[0] = mils / 60;
        time[1] = mils % 60;
        return time;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.videoNext: //下一集
                if (this.call != null)
                    this.call.doVideoPlayNext();
                break;

            case R.id.list_anim://选集
                if (this.call != null)
                    this.call.doChapterList();
                break;
            case R.id.exit_screen:
                //退出全屏
                if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    ((AnimDetailActivity) context).setLandPort();
                } else {
                    ((Activity) context).finish();
                }
                break;
            case R.id.anim_share:
                // 分享
                if (this.call != null)
                    this.call.doVideoShare();

                break;

            case R.id.anim_dowm:
                // 下载
                if (this.call != null)
                    this.call.doVideoDowm();
                break;


            case R.id.anim_collect:
                // 收藏
                if (this.call != null)
                    this.call.doVideoCollection(more);
                break;
            case R.id.tan_anim:
                // 弹窗

                break;
            case R.id.send_bullet:
                if (sendBulletListener != null)
                    sendBulletListener.sendBullet();
                break;

//            case R.id.screen_lock:
//                //锁屏
//                isScreenLock = !isScreenLock;
//                break;
            case R.id.videoPlayImg:
                videoView.start();
                videoPlayImg.setVisibility(View.INVISIBLE);
                videoPauseImg.setImageResource(R.mipmap.play_stop);
                break;
            case R.id.videoPauseBtn:
                touchPlayOrPause();
                break;
            case R.id.viewBox:
                if (!isScreenLock)
                    videoViewOnclick();
                else {
//                    screenLock.setVisibility(VISIBLE);
                }

                break;
            case R.id.screen_status_img:
                screenSwitchUtils.toggleScreen();
                screenSwitchUtils.setIsSennor();
                int i = getResources().getConfiguration().orientation;
                if (i == Configuration.ORIENTATION_PORTRAIT) {
//                    ((AnimDetailActivity) context).setIsLandscape(false);
                    setFullScreen();

                } else if (i == Configuration.ORIENTATION_LANDSCAPE) {
//                    ((AnimDetailActivity) context).setIsLandscape(true);
                    setNormalScreen();
                }

                ((AnimDetailActivity) context).setLandPort();
//                ((AnimDetailActivity) context).setIsSennor();
//                ((AnimDetailActivity) context).setButtonFullScreenClicked();

                break;

        }
    }

    public void touchPlayOrPause(){
        if (videoView.isPlaying()) {
            videoView.pause();
            videoPauseImg.setImageResource(R.mipmap.play_start);
            videoPlayImg.setVisibility(View.VISIBLE);
        } else {
            videoView.start();
            videoPauseImg.setImageResource(R.mipmap.play_stop);
            videoPlayImg.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isPlaying(){
        return videoView.isPlaying();
    }

    float y;

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }


    public void orientation(AnimDetailActivity animDetailActivity) {
        videoTitleLayout.setY(0);
        videoTitleLayout.setX(0);
        videoControllerLayout.setX(0);
        if(!videoControllerShow)
        {videoControllerLayout.setY(videoControllerLayout.getY()- videoControllerLayout.getHeight());videoControllerShow=true;}
    }

    private void videoViewOnclick() {
        float curY = videoControllerLayout.getY();
        float cursY = videoTitleLayout.getY();
                 if (!animation && videoControllerShow) {
                    animation = true;
                     if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                     { startAnimation(videoTitleLayout, cursY, cursY - videoTitleLayout.getHeight(), null);}
                    startAnimation(videoControllerLayout, curY, curY + videoControllerLayout.getHeight(), this);
                } else if (!animation) {
                    animation = true;
                     if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                     {startAnimation(videoTitleLayout, cursY, cursY + videoTitleLayout.getHeight(), null);}
                    startAnimation(videoControllerLayout, curY, curY - videoControllerLayout.getHeight(), this);
                     videoHandler.removeMessages(UPDATE_VIDEO_SEEKBAR_TIME);
                     videoHandler.sendEmptyMessageDelayed(UPDATE_VIDEO_SEEKBAR_TIME, 10 * 1000);
                }
    }

    private void startAnimation(View vi, float fromy, float toy, Animator.AnimatorListener l) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(vi, "y",
                fromy, toy);
        animator.setDuration(200);
        animator.start();
        if (l != null)
            animator.addListener(l);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        this.animation = false;
        this.videoControllerShow = !this.videoControllerShow;

        if (!this.videoControllerShow) {
//            screenLock.setVisibility(INVISIBLE);
        } else {
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                screenLock.setVisibility(VISIBLE);
            } else {
//                screenLock.setVisibility(INVISIBLE);
            }
        }

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int[] time = getMinuteAndSecond(progress);
        videoCurTimeText.setText(String.format("%02d:%02d", time[0], time[1]));
        Log.e("SCY"," - - --progress -  - --  "+progress);
        Log.e("SCY"," - - - - progress  - - - --  "+String.format("%02d:%02d", time[0], time[1]));
        int[] times = getMinuteAndSecond(duration);
        Log.e("SCY"," - - -- -  -duration --  "+duration);
        Log.e("SCY"," - - -- -  duration- --  "+String.format("%02d:%02d", times[0], times[1]));
        if (videoUrl==null||videoUrl.equals("")){
            return;
        }
        settimeListener.setTime(videoUrl,progress,duration);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        videoView.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        videoView.seekTo(videoSeekBar.getProgress());
        videoView.start();
        videoPlayImg.setVisibility(View.INVISIBLE);
        videoPauseImg.setImageResource(R.mipmap.play_stop);
    }


    public void setmVideoName(String title) {
        this.mVideoName.setText(title);
    }


    VideoViewCall call;

    public void setVideoCall(VideoViewCall call) {
        this.call = call;
    }

    public static class VideoViewCall {
        public void doVideoDowm() {

        }

        public void doChapterList() {

        }

        public void doVideoShare() {

        }

        public void doVideoCollection(CheckBox more) {

        }


        public void doVideoPlayNext() {
        }
    }



}
