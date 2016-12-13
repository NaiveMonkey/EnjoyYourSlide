package kr.paulkim.enjoyyourslide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 김새미루 on 2016-07-08.
 */
public class TestVerticalAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();


    public TestVerticalAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((TestContentFragment) fragments.get(position)).getTitle();
    }

    public static class Holder {
        private final List<Fragment> fragments = new ArrayList<>();
        private FragmentManager manager;

        public Holder(FragmentManager manager) {
            this.manager = manager;
        }

        public Holder add(Fragment f) {
            fragments.add(f);
            return this;
        }

        public TestVerticalAdapter set() {
            return new TestVerticalAdapter(manager, fragments);
        }
    }
}
