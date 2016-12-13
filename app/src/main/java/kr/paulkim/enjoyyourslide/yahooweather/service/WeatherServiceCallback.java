package kr.paulkim.enjoyyourslide.yahooweather.service;

import kr.paulkim.enjoyyourslide.yahooweather.data.Channel;

/**
 * Created by 김새미루 on 2016-06-06.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
