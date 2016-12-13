package kr.paulkim.enjoyyourslide;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by 김새미루 on 2016-07-01.
 */
public class LockScreenUtil {
    private static LockScreenUtil mLockScreenUtilInstance;
    private Context mContext;

    private LockScreenUtil() {
        mContext = null;
    }

    private LockScreenUtil(Context context) {
        mContext = context;
    }

    public static LockScreenUtil getInstance(Context context) {
        if (mLockScreenUtilInstance == null) {
            if (null != context) {
                mLockScreenUtilInstance = new LockScreenUtil(context);
            } else {
                mLockScreenUtilInstance = new LockScreenUtil();
            }
        }
        return mLockScreenUtilInstance;
    }

    public boolean isStandardKeyguardState() {
        boolean isStandardKeyguard = false;
        KeyguardManager keyManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        if (null != keyManager) {
            isStandardKeyguard = keyManager.isKeyguardSecure();
        }
        return isStandardKeyguard;
    }

    public boolean isSoftKeyAvail(Context context) {
        final boolean[] isSoftkey = {false};
        final View activityRootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int rootViewHeigt = activityRootView.getRootView().getHeight();
                int viewHeight = activityRootView.getHeight();
                int heightDiff = rootViewHeigt - viewHeight;
                if (heightDiff > 100) {
                    isSoftkey[0] = true;
                }
            }
        });
        return isSoftkey[0];
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = mContext.getResources().getDimensionPixelSize(resourceId);

        return result;
    }
}
