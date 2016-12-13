package kr.paulkim.enjoyyourslide.verticalfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
public class VerticalFragment2 extends Fragment implements FragmentLifeCycle {
    private MyApplication application;
    private ImageView fragmentImage2;

    public static VerticalFragment2 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        VerticalFragment2 fragment = new VerticalFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment3, container, false);

        /*
        fragment = (RelativeLayout) view.findViewById(R.id.fragment);
        fragment.setBackgroundResource(R.drawable.shakebox);
        fragment.bringToFront();
        */
        fragmentImage2 = (ImageView) view.findViewById(R.id.fragmentImage3);
        //fragmentImage4.setPadding(0, 110, 0, 0);
        application = (MyApplication) getActivity().getApplicationContext();
        String url = String.valueOf(application.getPhoneList().get(1));


        //Glide.with(this).load(url).override(1200,1550).into(fragmentImage4);

        Glide.with(this).load("http://ksmr1102.vps.phps.kr/vertical_photo/v1_bourne.jpg").into(fragmentImage2);

        return view;
    }

    @Override
    public void OnPauseFragment() {

    }

    @Override
    public void OnResumeFragment() {

    }
}
