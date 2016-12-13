package kr.paulkim.enjoyyourslide;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 김새미루 on 2016-06-02.
 */
public class LockScreenServiceTime extends Service {

    private Handler mHandler;
    private Calendar c;
    private TextView mTime, mAM_PM, mMonth_Day;

    private String formattedDate;
    private String formattedTime;
    private String formattedDay;

    private Context mContext;
    private LayoutInflater mInflater;
    private View mLockscreenView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        initState();
        initView();
        if (null != mWindowManager && null != mLockscreenView && null != mParams) {
            mWindowManager.addView(mLockscreenView, mParams);
            mTime = (TextView) mLockscreenView.findViewById(R.id.testTime);
            mAM_PM = (TextView) mLockscreenView.findViewById(R.id.testAM_PM);
            mMonth_Day = (TextView) mLockscreenView.findViewById(R.id.testMonth_Day);

            mTime.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Light.ttf"));
            mAM_PM.setTypeface(Typeface.createFromAsset(getAssets(), "Spoqa Han Sans Regular.ttf"));
            mMonth_Day.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));

            mHandler = new Handler(getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    formattedDate = new SimpleDateFormat("MM.dd", Locale.KOREA).format(new Date());
                    formattedTime = new SimpleDateFormat("HH:mm", Locale.KOREA).format(new Date());
                    formattedDay = new SimpleDateFormat("E요일", Locale.KOREA).format(new Date());
                    mTime.setText(formattedTime);
                    mAM_PM.setText(formattedDay);
                    mMonth_Day.setText(formattedDate);
                    mHandler.postDelayed(this, 10000);
                }
            }, 10);
        }
        return LockScreenServiceTime.START_NOT_STICKY;
    }

    private void initState() {
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mParams.gravity = Gravity.TOP;
        mWindowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
    }

    private void initView() {
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLockscreenView = mInflater.inflate(R.layout.time_lockmain, null);
    }

    public void dettachView() {
        if (null != mWindowManager && null != mLockscreenView) {
            AlphaAnimation alpha = new AlphaAnimation(1, 0);
            alpha.setDuration(500);
            alpha.setInterpolator(new LinearInterpolator());
            mLockscreenView.setAnimation(alpha);
            mWindowManager.removeView(mLockscreenView);
            mLockscreenView = null;
            mWindowManager = null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dettachView();
    }
}