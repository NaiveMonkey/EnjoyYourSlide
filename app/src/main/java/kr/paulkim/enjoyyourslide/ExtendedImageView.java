package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 김새미루 on 2016-07-28.
 */
public class ExtendedImageView extends ImageView {
    public ExtendedImageView(Context context) {
        super(context);
    }

    public ExtendedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean canScrollVertical(int direction) {
        final int offset = computeVerticalScrollOffset();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        else return (direction < 0) ? (offset > 0) : (offset < range - 1);
    }
}
