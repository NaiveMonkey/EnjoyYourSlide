package kr.paulkim.enjoyyourslide.verticalfragment;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import kr.paulkim.enjoyyourslide.FragmentLifeCycle;
import kr.paulkim.enjoyyourslide.MyApplication;
import kr.paulkim.enjoyyourslide.R;

/**
 * Created by 김새미루 on 2016-07-12.
 */
public class VerticalFragment1 extends Fragment implements FragmentLifeCycle {
    private LinearLayout weatherBackground;
    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private ImageView yahooLogo;
    private MyApplication app;


    public static VerticalFragment1 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        VerticalFragment1 fragment = new VerticalFragment1();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_weather, container, false);
        app = (MyApplication) getActivity().getApplicationContext();
        String url = String.valueOf(app.getPhoneList().get(0));
        Log.d("WHITE", url);

        weatherBackground = (LinearLayout) view.findViewById(R.id.weatherBackground);
        //Glide.with(this).load("http://ksmr1102.vps.phps.kr/weather/" + app.getCondition() + ".png").into(weatherBackground);
        Glide.with(this).load("http://ksmr1102.vps.phps.kr/weather/" + app.getCondition() + ".png").asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            weatherBackground.setBackground(drawable);
                        }
                    }
                });

        Log.d("weatherCODE", String.valueOf(app.getCondition()));
        //Glide.with(this).load(url).override(1200, 1550).into(background);
        weatherIconImageView = (ImageView) view.findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) view.findViewById(R.id.temperatureTextView);
        temperatureTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Lato-Light.ttf"));
        //conditionTextView = (TextView) view.findViewById(R.id.conditionTextView);
        locationTextView = (TextView) view.findViewById(R.id.locationTextView);
        locationTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Spoqa Han Sans Regular.ttf"));
        /*
        weatherIconImageView.bringToFront();
        temperatureTextView.bringToFront();
        locationTextView.bringToFront();
        */
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherIconImageView.setImageDrawable(app.getWeatherIconImage());
        temperatureTextView.setText(app.getTemperature());
        //conditionTextView.setText(app.getCondition());
        locationTextView.setText(app.getLocation());
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public int getPosition() {
        return getArguments().getInt("position");
    }


    @Override
    public void OnPauseFragment() {

    }

    @Override
    public void OnResumeFragment() {

    }
}
