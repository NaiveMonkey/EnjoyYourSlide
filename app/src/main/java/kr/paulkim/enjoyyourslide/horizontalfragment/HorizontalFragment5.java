package kr.paulkim.enjoyyourslide.horizontalfragment;

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
 * Created by 김새미루 on 2016-07-29.
 */
public class HorizontalFragment5 extends Fragment implements FragmentLifeCycle {
    private MyApplication application;
    private ImageView fragment5;
    private ImageView backBtn;

    public static HorizontalFragment5 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        HorizontalFragment5 fragment = new HorizontalFragment5();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment2_3, container, false);
        fragment5 = (ImageView) view.findViewById(R.id.fragmentImage2_3);
        backBtn = (ImageView) view.findViewById(R.id.backBtn);
        backBtn.bringToFront();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                application.getViewPager().setCurrentItem(0, true);
            }
        });
        //fragment5.setPadding(0, 110, 0, 0);
        application = (MyApplication) getActivity().getApplicationContext();
        String url = String.valueOf(application.getH_phoneList().get(3));
        Glide.with(this).load(url).into(fragment5);
        backBtn.setImageResource(R.drawable.home);
        return view;
    }

    @Override
    public void OnPauseFragment() {

    }

    @Override
    public void OnResumeFragment() {

    }
}
