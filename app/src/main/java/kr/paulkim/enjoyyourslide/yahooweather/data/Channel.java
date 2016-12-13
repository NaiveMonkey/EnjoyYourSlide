package kr.paulkim.enjoyyourslide.yahooweather.data;


import org.json.JSONObject;

/**
 * Created by 김새미루 on 2016-06-06.
 */
public class Channel implements JSONPopulator {
    private Units units;
    private Item item;


    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }


    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

    }


}
