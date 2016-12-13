package kr.paulkim.enjoyyourslide.verticalfragment;

import android.graphics.Bitmap;
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
import kr.paulkim.enjoyyourslide.VerticalViewPager;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment1_1;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment2_1;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment3;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment4;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment5;
import kr.paulkim.enjoyyourslide.horizontalfragment.HorizontalFragment6;

/**
 * Created by 김새미루 on 2016-07-13.
 */
public class VerticalFragment4 extends Fragment implements FragmentLifeCycle {

    static final int MIN_DISTANCE = 100;
    static public VerticalViewPager vp;
    private TestHorizontalAdapter pageAdapter;
    private MyApplication application;
    private Bitmap bitmap;


    //private Back task;
    private HorizontalViewPager viewPager;
    private int downX, downY;
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

    public static VerticalFragment4 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        VerticalFragment4 fragment = new VerticalFragment4();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment2, container, false);
        //fragmentImage2 = (ImageView) view.findViewById(R.id.fragmentImage2);
        //Glide.with(this).load(imgUrl).override(600, 700).into(fragmentImage2);

        /*
        Fragment child2_1 = new LockScreenMainFragment2_1();
        Fragment child2_2 = new LockScreenMainFragment2_2();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.child1, child2_1);
        transaction.add(R.id.child1, child2_2).commit();
        */

        /*
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.horizontal_viewpager1);
        viewPager.setAdapter(new TestVerticalAdapter.Holder(this.getChildFragmentManager())
                .add(LockScreenMainFragment2_1.newInstance(1))
                .add(LockScreenMainFragment2_2.newInstance(2))
                .set());
                */

        //vp = application.getVp();
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
        /*
        viewPager.setOnTouchListener(this);
        application = (MyApplication) getActivity().getApplicationContext();
        application.setViewPager(viewPager);
        Log.d("RECT", "넓이: " + String.valueOf(viewPager.getWidth()) + "   " + "높이: " + String.valueOf(viewPager.getHeight()));
        */
        return view;
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<>();
        /*
        fList.add(HorizontalFragment1_1.newInstance(1));
        fList.add(HorizontalFragment2_1.newInstance(2));
        fList.add(HorizontalFragment3.newInstance(3));
        fList.add(HorizontalFragment4.newInstance(4));
        fList.add(HorizontalFragment5.newInstance(5));
        fList.add(HorizontalFragment6.newInstance(6));
        */

        HorizontalFragment1_1 ho1 = new HorizontalFragment1_1();
        HorizontalFragment2_1 ho2 = new HorizontalFragment2_1();
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

        for (int i = 0; i < Integer.parseInt(application.getCount().get(0)) + 1; i++) {
            fList.add(fragment.get(i));
        }

        return fList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //task = new Back();
        //task.execute(imgUrl);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        List<Fragment> fragments = getFragments();
        pageAdapter = new TestHorizontalAdapter(this.getChildFragmentManager(), fragments);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setOnTouchListener(this);
        application = (MyApplication) getActivity().getApplicationContext();
        application.setViewPager(viewPager);
        */

    }

    @Override
    public void OnPauseFragment() {
    }

    @Override
    public void OnResumeFragment() {
        viewPager.setHorizontalEnabled();
    }


    /*
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getRawX();
                Log.d("RECT", "1 X좌표: " + String.valueOf(downX));
                downY = (int) event.getY();
                Log.d("RECT", "1 Y좌표: " + String.valueOf(downY));
                break;

            case MotionEvent.ACTION_MOVE:
                int distanceX = Math.abs((int) event.getRawX() - downX);
                Log.d("RECT", "2 X좌표: " + String.valueOf((int) event.getRawX()));
                int distanceY = Math.abs((int) event.getRawY() - downY);
                Log.d("RECT", "2 Y좌표: " + String.valueOf((int) event.getRawY()));

                if (distanceY > distanceX && distanceY > MIN_DISTANCE) {
                    //viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                    viewPager.setHorizontalDisabled();
                    //vp.getParent().requestDisallowInterceptTouchEvent(true);
                    vp.setVerticalEnabled();
                    //Toast.makeText(getActivity().getApplicationContext(), "수직 슬라이드", Toast.LENGTH_LONG).show();
                } else {
                    //viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                    viewPager.setHorizontalEnabled();
                    //vp.getParent().requestDisallowInterceptTouchEvent(false);
                    vp.setVerticalDisabled();
                    //Toast.makeText(getActivity().getApplicationContext(), "수평 슬라이드", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return false;
    }
    */

   /*
    private class Back extends AsyncTask<String, Integer, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);

                InputStream is = conn.getInputStream();

                bitmap = BitmapFactory.decodeStream(is);

            } catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap img) {
            super.onPostExecute(bitmap);
            fragmentImage2.setImageBitmap(bitmap);
        }
    }
    */
}
