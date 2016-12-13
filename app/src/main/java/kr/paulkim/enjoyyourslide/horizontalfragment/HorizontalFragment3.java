package kr.paulkim.enjoyyourslide.horizontalfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kr.paulkim.enjoyyourslide.FragmentLifeCycle;
import kr.paulkim.enjoyyourslide.MyApplication;
import kr.paulkim.enjoyyourslide.R;

/**
 * Created by 김새미루 on 2016-07-23.
 */
public class HorizontalFragment3 extends Fragment implements FragmentLifeCycle {
    private MyApplication application;
    private ImageView fragmentImage3;
    private ImageView backBtn;

    public static HorizontalFragment3 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        HorizontalFragment3 fragment = new HorizontalFragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment2_3, container, false);
        fragmentImage3 = (ImageView) view.findViewById(R.id.fragmentImage2_3);
        //fragmentImage3.setPadding(0, 110, 0, 0);
        application = (MyApplication) getActivity().getApplicationContext();
        backBtn = (ImageView) view.findViewById(R.id.backBtn);
        backBtn.bringToFront();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                application.getViewPager().setCurrentItem(0, true);
            }
        });
        return view;
    }

    @Override
    public void OnPauseFragment() {

    }

    @Override
    public void OnResumeFragment() {
        String url = String.valueOf(application.getH_phoneList().get(1));
        Log.d("Fragment2_3", url);
        Glide.with(this).load(url).into(fragmentImage3);
        backBtn.setImageResource(R.drawable.home);
    }
}
