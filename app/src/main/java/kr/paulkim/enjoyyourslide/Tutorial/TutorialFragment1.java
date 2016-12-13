package kr.paulkim.enjoyyourslide.Tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kr.paulkim.enjoyyourslide.R;

/**
 * Created by 김새미루 on 2016-08-15.
 */
public class TutorialFragment1 extends Fragment {
    private ImageView tuto1;

    public static TutorialFragment1 newInstance() {
        Bundle args = new Bundle();
        TutorialFragment1 fragment = new TutorialFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro1_apptutorial, container, false);
        tuto1 = (ImageView) view.findViewById(R.id.tutorial1);
        Glide.with(this).load("http://ksmr1102.vps.phps.kr/tutorial/tuto1.png").into(tuto1);
        return view;
    }
}
