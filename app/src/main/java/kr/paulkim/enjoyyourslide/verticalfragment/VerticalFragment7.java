package kr.paulkim.enjoyyourslide.verticalfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.paulkim.enjoyyourslide.FragmentLifeCycle;
import kr.paulkim.enjoyyourslide.HorizontalViewPager;
import kr.paulkim.enjoyyourslide.MyApplication;
import kr.paulkim.enjoyyourslide.R;
import kr.paulkim.enjoyyourslide.TestHorizontalAdapter;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment1_2;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment2_2;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment3;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment4;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment5;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment6;

/**
 * Created by 김새미루 on 2016-08-04.
 */
public class VerticalFragment7 extends Fragment implements FragmentLifeCycle {
    private TestHorizontalAdapter pageAdapter;
    private MyApplication application;
    private HorizontalViewPager viewPager;
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        int currentPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int newPosition) {
            FragmentLifeCycle fragmentToHide = (FragmentLifeCycle) pageAdapter.instantiateItem(viewPager, currentPosition);
            fragmentToHide.OnPauseFragment();

            FragmentLifeCycle fragmentToShow = (FragmentLifeCycle) pageAdapter.instantiateItem(viewPager, newPosition);
            fragmentToShow.OnResumeFragment();

            currentPosition = newPosition;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public static VerticalFragment7 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        VerticalFragment7 fragment = new VerticalFragment7();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment2, container, false);
        application = (MyApplication) getActivity().getApplicationContext();

        viewPager = (HorizontalViewPager) view.findViewById(R.id.horizontal_viewpager1);
        List<Fragment> fragments = getFragments();
        pageAdapter = new TestHorizontalAdapter(this.getChildFragmentManager(), fragments);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedposition / 2 + 0.5f);
                page.setScaleY(normalizedposition / 2 + 0.5f);
            }
        });
        application.setViewPager(viewPager);
        return view;
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<>();

        HorizontalFragment1_2 ho1 = new HorizontalFragment1_2();
        HorizontalFragment2_2 ho2 = new HorizontalFragment2_2();
        HorizontalFragment3 ho3 = new HorizontalFragment3();
        HorizontalFragment4 ho4 = new HorizontalFragment4();
        HorizontalFragment5 ho5 = new HorizontalFragment5();
        HorizontalFragment6 ho6 = new HorizontalFragment6();

        ArrayList<Fragment> fragment = new ArrayList<>();
        fragment.add(ho1);
        fragment.add(ho2);
        fragment.add(ho3);
        fragment.add(ho4);
        fragment.add(ho5);
        fragment.add(ho6);

        for (int i = 0; i < Integer.parseInt(application.getCount().get(1)) + 1; i++) {
            fList.add(fragment.get(i));
        }

        return fList;
    }

    @Override
    public void OnPauseFragment() {

    }

    @Override
    public void OnResumeFragment() {
        viewPager.setHorizontalEnabled();
    }
}
