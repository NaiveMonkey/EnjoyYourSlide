package kr.paulkim.testlockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 김새미루 on 2016-06-02.
 */
public class PackageReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        /*

        if(action.equals(Intent.ACTION_PACKAGE_ADDED)){
            //앱이 설치 되었을 때
        } else if (action.equals(Intent.ACTION_PACKAGE_REMOVED)){
            //앱이 삭제 되었을 때
        } else if (action.equals(Intent.ACTION_PACKAGE_REPLACED)){
            Intent i = new Intent(context, ScreenService.class);
            context.startService(i);
        }
        */
    }
}
