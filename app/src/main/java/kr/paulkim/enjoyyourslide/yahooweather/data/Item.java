package kr.paulkim.enjoyyourslide.yahooweather.data;


import org.json.JSONObject;

/**
 * Created by 김새미루 on 2016-06-06.
 */
public class Item implements JSONPopulator {
    private Condition condition;


    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
