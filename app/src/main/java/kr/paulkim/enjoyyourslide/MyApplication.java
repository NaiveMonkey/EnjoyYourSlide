package kr.paulkim.enjoyyourslide;

import android.graphics.drawable.Drawable;
import android.support.multidex.MultiDexApplication;

import java.util.ArrayList;

/**
 * Created by 김새미루 on 2016-07-05.
 */
public class MyApplication extends MultiDexApplication {
    private String email;
    private String username;
    private String separator;

    private Drawable weatherIconImage;
    private String temperature;
    private String condition;
    private String location;
    private ArrayList<String> phoneList;
    private ArrayList<String> h_phoneList;
    private ArrayList<String> count;
    private HorizontalViewPager viewPager;
    private VerticalViewPager vp;
    //private Beer_VerticalViewPager vp;


    public ArrayList<String> getCount() {
        return count;
    }

    public void setCount(ArrayList<String> count) {
        this.count = count;
    }

    public ArrayList<String> getH_phoneList() {
        return h_phoneList;
    }

    public void setH_phoneList(ArrayList<String> h_phoneList) {
        this.h_phoneList = h_phoneList;
    }

    public VerticalViewPager getVp() {
        return vp;
    }

    public void setVp(VerticalViewPager vp) {
        this.vp = vp;
    }

    /*
    public void setVp(Beer_VerticalViewPager vp) {
        this.vp = vp;
    }

    public Beer_VerticalViewPager getVp() {
        return vp;
    }
    */

    public HorizontalViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(HorizontalViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public ArrayList<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(ArrayList<String> phoneList) {
        this.phoneList = phoneList;
    }

    public Drawable getWeatherIconImage() {
        return weatherIconImage;
    }

    public void setWeatherIconImage(Drawable weatherIconImage) {
        this.weatherIconImage = weatherIconImage;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
