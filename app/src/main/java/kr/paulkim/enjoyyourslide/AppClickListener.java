package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

/**
 * Created by 김새미루 on 2016-06-17.
 */
public class AppClickListener implements View.OnClickListener {
    Pac[] pacsForListener;
    Context mContext;
    PackageManager packageManager;

    public AppClickListener(Pac[] pacs, Context ctxt, PackageManager packageManager) {
        pacsForListener = pacs;
        mContext = ctxt;
        this.packageManager = packageManager;

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String[] data;
        data = (String[]) v.getTag();


        String positionX = "" + v.getX();
        String positionY = "" + v.getY();
        Toast.makeText(mContext, positionX + " : " + positionY, Toast.LENGTH_LONG).show();

        /*
        Intent launchIntent = new Intent(Intent.ACTION_MAIN);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cp = new ComponentName(data[0], data[1]);
        launchIntent.setComponent(cp);
        mContext.startActivity(launchIntent);
        */

        Intent intent = packageManager.getLaunchIntentForPackage(data[0]);
        mContext.startActivity(intent);

    }

}