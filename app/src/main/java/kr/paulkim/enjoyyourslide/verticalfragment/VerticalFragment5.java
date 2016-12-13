package kr.paulkim.enjoyyourslide.verticalfragment;

import android.content.Intent;
import android.net.Uri;
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
public class VerticalFragment5 extends Fragment implements FragmentLifeCycle {
    private MyApplication application;
    private ImageView fragmentImage5;

    public static VerticalFragment5 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        VerticalFragment5 fragment = new VerticalFragment5();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment3, container, false);
        fragmentImage5 = (ImageView) view.findViewById(R.id.fragmentImage3);
        //fragmentImage5.setPadding(0, 110, 0 ,0);
        application = (MyApplication) getActivity().getApplicationContext();
        String url = String.valueOf(application.getPhoneList().get(4));
        Glide.with(this).load(url).into(fragmentImage5);
        fragmentImage5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.fox.com/gotham"));
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                startActivity(intent);
                getActivity().finish();
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
