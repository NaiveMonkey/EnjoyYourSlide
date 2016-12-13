package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

/**
 * Created by 김새미루 on 2016-06-24.
 */
public class RelativeLayoutAbsListView extends RelativeLayout {
    AbsListView absListView;

    public RelativeLayoutAbsListView(Context context) {
        super(context);
    }

    public RelativeLayoutAbsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public RelativeLayoutAbsListView(Context context, AttributeSet attrs,
                                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public void setAbsListView(AbsListView alv) {
        absListView = alv;
    }
}
