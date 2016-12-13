package kr.paulkim.enjoyyourslide.verticalfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kr.paulkim.enjoyyourslide.FragmentLifeCycle;
import kr.paulkim.enjoyyourslide.MyApplication;
import kr.paulkim.enjoyyourslide.R;

/**
 * Created by 김새미루 on 2016-07-16.
 */
public class VerticalFragment3 extends Fragment implements FragmentLifeCycle {
    private MyApplication application;
    private ImageView fragmentImage3;
    private long then = 0;

    public static VerticalFragment3 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        VerticalFragment3 fragment = new VerticalFragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment3, container, false);
        fragmentImage3 = (ImageView) view.findViewById(R.id.fragmentImage3);
        //fragmentImage3.setPadding(0, 110, 0, 0);
        application = (MyApplication) getActivity().getApplicationContext();
        String url = String.valueOf(application.getPhoneList().get(2));
        Glide.with(this).load("http://ksmr1102.vps.phps.kr/vertical_photo/v2_holister.jpg").into(fragmentImage3);
        fragmentImage3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    then = System.currentTimeMillis();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (System.currentTimeMillis() - then > 1200) {
                        return true;
                    }
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void OnPauseFragment() {

    }

    @Override
    public void OnResumeFragment() {

    }
}
