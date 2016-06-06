package kr.paulkim.enjoyyourslide;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
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
 * Created by 김새미루 on 2016-06-02.
 */
public class ScreenService extends Service {
    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keyLock = null;
    private TelephonyManager telephonyManager = null;
    private boolean isPhonedle = true;
    private PackageReceiver pReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                if (telephonyManager == null) {
                    telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                }

                if (isPhonedle) {
                    keyLock.disableKeyguard();
                    Intent i = new Intent(context, LockScreenMainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        }
    };


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


    @Override
    public void onCreate() {
        super.onCreate();
        km = (KeyguardManager) this.getSystemService(Activity.KEYGUARD_SERVICE);
        if (km != null) {
            keyLock = km.newKeyguardLock(Context.KEYGUARD_SERVICE);
            keyLock.disableKeyguard();
        }
        registerRestartAlarm(true);

        pReceiver = new PackageReceiver();
        IntentFilter pFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        pFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        pFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        pFilter.addDataScheme("package");
        registerReceiver(pReceiver, pFilter);


        Notification noti = new Notification.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Enjoy Your Slide")
                .setContentText("실행 중")
                .build();
        startForeground(1234, noti);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
        return Service.START_NOT_STICKY;
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
        if (pReceiver != null) {
            unregisterReceiver(pReceiver);
        }
        registerRestartAlarm(false);
    }

    public void registerRestartAlarm(boolean isOn) {
        Intent intent = new Intent(ScreenService.this, RestartReceiver.class);
        intent.setAction(RestartReceiver.ACTION_RESTART_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (isOn) {
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000, 10000, sender);
        } else {
            am.cancel(sender);
        }
    }
}