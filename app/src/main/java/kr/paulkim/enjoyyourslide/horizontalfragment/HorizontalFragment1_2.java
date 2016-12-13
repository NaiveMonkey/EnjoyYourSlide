package kr.paulkim.enjoyyourslide.horizontalfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kr.paulkim.enjoyyourslide.FragmentLifeCycle;
import kr.paulkim.enjoyyourslide.HorizontalViewPager;
import kr.paulkim.enjoyyourslide.MyApplication;
import kr.paulkim.enjoyyourslide.R;
import kr.paulkim.enjoyyourslide.VerticalViewPager;

/**
 * Created by 김새미루 on 2016-08-05.
 */
public class HorizontalFragment1_2 extends Fragment implements FragmentLifeCycle {
    private MyApplication application;
    private ImageView fragmentImage1;
    private TextView countNum;
    private int downX, downY;

    public static HorizontalFragment1_2 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        HorizontalFragment1_2 fragment = new HorizontalFragment1_2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment2_1, container, false);
        application = (MyApplication) getActivity().getApplicationContext();
        final VerticalViewPager vp = application.getVp();
        final HorizontalViewPager viewPager = application.getViewPager();
        String url = String.valueOf(application.getPhoneList().get(6));
        Log.d("img_path", url);
        Log.d("currentITEM", String.valueOf(application.getVp().getCurrentItem()));

        /*
        countNum = (TextView) view.findViewById(R.id.countNum);
        countNum.bringToFront();
        countNum.setText(application.getCount().get(1));
        */

        //LockScreenMainFragment6 frag = ((LockScreenMainFragment6)this.getParentFragment());
        fragmentImage1 = (ImageView) view.findViewById(R.id.fragmentImage2_1);
        //fragmentImage1.setPadding(0, 110, 0, 0);
        //Glide.with(this).load(imgUrl).into(fragmentImage2_1);
        Glide.with(this).load(url).into(fragmentImage1);
        fragmentImage1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();
                        Log.d("coordinate", "X 좌표: " + String.valueOf(downX));
                        downY = (int) event.getY();
                        Log.d("coordinate", "Y좌표: " + String.valueOf(downY));

                        if (downY > 1030 || downY < 700) {
                            viewPager.setHorizontalDisabled();
                            vp.setVerticalEnabled();
                        } else if (downX > 700) {
                            viewPager.setHorizontalEnabled();
                            vp.setVerticalDisabled();
                        }
                        break;

                }
                return false;

            }
        });
        return view;
    }

    @Override
    public void OnPauseFragment() {
        application.getVp().setVerticalDisabled();
    }

    @Override
    public void OnResumeFragment() {
        application.getVp().setVerticalEnabled();
    }
}
