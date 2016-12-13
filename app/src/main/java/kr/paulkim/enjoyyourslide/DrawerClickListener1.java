package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by 김새미루 on 2016-06-17.
 */

public class DrawerClickListener1 implements AdapterView.OnItemClickListener {

    Context mContext;
    Pac[] pacsForAdapter;
    PackageManager pmForListener;

    public DrawerClickListener1(Context c, Pac[] pacs, PackageManager pm) {
        mContext = c;
        pacsForAdapter = pacs;
        pmForListener = pm;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
        if (LockScreenShortcutActivity.appLaunchable) {
            /*Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(pacsForAdapter[pos].packageName, pacsForAdapter[pos].name);
            launchIntent.setComponent(cp);

            mContext.startActivity(launchIntent);*/
            Intent intent = pmForListener.getLaunchIntentForPackage(pacsForAdapter[pos].packageName);
            mContext.startActivity(intent);
        }
    }

}
