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
 * Created by 김새미루 on 2016-08-04.
 */
public class VerticalFragment9 extends Fragment implements FragmentLifeCycle {
    private MyApplication application;
    private ImageView fragmentImage9;

    public static VerticalFragment9 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        VerticalFragment9 fragment = new VerticalFragment9();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment3, container, false);
        fragmentImage9 = (ImageView) view.findViewById(R.id.fragmentImage3);
        //fragmentImage9.setPadding(0, 110, 0 ,0);
        application = (MyApplication) getActivity().getApplicationContext();
        String url = String.valueOf(application.getPhoneList().get(8));
        Glide.with(this).load(url).into(fragmentImage9);
        return view;
    }

    @Override
    public void OnPauseFragment() {

    }

    @Override
    public void OnResumeFragment() {

    }
}
