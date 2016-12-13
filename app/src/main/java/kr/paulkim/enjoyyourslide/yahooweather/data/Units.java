package kr.paulkim.enjoyyourslide.yahooweather.data;

import org.json.JSONObject;

/**
 * Created by 김새미루 on 2016-06-06.
 */
public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }


}
