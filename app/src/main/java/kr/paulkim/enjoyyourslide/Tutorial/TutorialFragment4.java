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
public class TutorialFragment4 extends Fragment {
    private ImageView tuto4;

    public static TutorialFragment4 newInstance() {
        Bundle args = new Bundle();
        TutorialFragment4 fragment = new TutorialFragment4();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro4_apptutorial, container, false);
        tuto4 = (ImageView) view.findViewById(R.id.tutorial4);
        Glide.with(this).load("http://ksmr1102.vps.phps.kr/tutorial/tuto4.png").into(tuto4);
        return view;
    }
}
