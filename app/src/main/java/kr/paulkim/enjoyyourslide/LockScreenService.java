package kr.paulkim.enjoyyourslide;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by 김새미루 on 2016-07-01.
 */
public class LockScreenService extends Service {
    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keyLock = null;
    private TelephonyManager telephonyManager = null;
    private boolean isPhonedle = true;
    private Context mContext;
    private int mServiceStartId = 0;
    private boolean lockScreen = false;
    private PhoneStateListener phoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    isPhonedle = true;
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    isPhonedle = false;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    isPhonedle = false;
                    break;
            }
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                if (telephonyManager == null) {
                    telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                    //Intent i = new Intent(mContext, ScreenServiceView.class);
                    //i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //stopService(i);
                }

                if (isPhonedle) {
                    keyLock.disableKeyguard();
                    Thread startDelay = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    Thread.sleep(3000);
                                    startLockScreenActivity();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    });
                    startDelay.start();
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        registerRestartAlarm(true);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Notification noti = new Notification.Builder(mContext)
                .setContentTitle("안녕")
                .setContentText("안녕")
                .build();

        startForeground(0, noti);

        mServiceStartId = startId;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);

        /*
        if (null != intent) {
            startLockScreenActivity();
        }
        */
        km = (KeyguardManager) this.getSystemService(Activity.KEYGUARD_SERVICE);
        if (km != null) {
            keyLock = km.newKeyguardLock(Context.KEYGUARD_SERVICE);
            keyLock.disableKeyguard();
        }

        return LockScreenService.START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (keyLock != null) {
            keyLock.reenableKeyguard();
        }
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        registerRestartAlarm(false);
    }

    private void startLockScreenActivity() {
        Intent intent = new Intent(mContext, LockScreenMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void registerRestartAlarm(boolean isOn) {
        Intent intent = new Intent(LockScreenService.this, RestartReceiver.class);
        intent.setAction(RestartReceiver.ACTION_RESTART_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (isOn) {
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000, 2000, sender);
        } else {
            am.cancel(sender);
        }
    }

}