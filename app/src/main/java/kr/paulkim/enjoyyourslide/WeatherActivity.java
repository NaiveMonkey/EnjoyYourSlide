package kr.paulkim.enjoyyourslide;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kr.paulkim.enjoyyourslide.yahooweather.data.Channel;
import kr.paulkim.enjoyyourslide.yahooweather.data.Item;
import kr.paulkim.enjoyyourslide.yahooweather.service.WeatherServiceCallback;
import kr.paulkim.enjoyyourslide.yahooweather.service.YahooWeatherService;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {
    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private ImageView forecastWeatherIconImageView;
    private TextView forecastDateTextView;
    private TextView forecastLowTempTextView;
    private TextView forecastHighTempTextView;
    private TextView forecastConditionTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

        forecastWeatherIconImageView = (ImageView) findViewById(R.id.forecastWeatherIconImageView);
        forecastDateTextView = (TextView) findViewById(R.id.forecastDateTextView);
        forecastLowTempTextView = (TextView) findViewById(R.id.forecastLowTempTextView);
        forecastHighTempTextView = (TextView) findViewById(R.id.forecastHighTempTextView);
        forecastConditionTextView = (TextView) findViewById(R.id.forecastConditionTextView);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("Seoul, Korea");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();

        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());

        /*int forecastResourceId = getResources().getIdentifier("drawable/icon_" + item.getForecast().getCode(), null, getPackageName());
        @SuppressWarnings("deprecation")
        Drawable forecastWeatherIconDrawable = getResources().getDrawable(forecastResourceId);

        forecastWeatherIconImageView.setImageDrawable(forecastWeatherIconDrawable);

        forecastDateTextView.setText(item.getForecast().getDay() + " " + item.getForecast().getDate());
        forecastLowTempTextView.setText(item.getForecast().getLow() + "\u00B0" + channel.getUnits().getTemperature());
        forecastHighTempTextView.setText(item.getForecast().getHigh() + "\u00B0" + channel.getUnits().getTemperature());
        forecastConditionTextView.setText(item.getForecast().getDescription()); */
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
