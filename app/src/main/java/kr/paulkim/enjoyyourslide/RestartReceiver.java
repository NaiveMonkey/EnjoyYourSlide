package kr.paulkim.enjoyyourslide;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 김새미루 on 2016-06-02.
 */
public class RestartReceiver extends BroadcastReceiver {
    static public final String ACTION_RESTART_SERVICE = "RestartReceiver.restart";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent photo = new Intent(context, PhotoService.class);
        context.startService(photo);

        Intent count = new Intent(context, CountService.class);
        context.startService(count);

        Intent i = new Intent(context, LockScreenService.class);
        context.startService(i);
    }
}
