package kr.paulkim.enjoyyourslide;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment1;
import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment2;
import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment3;
import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment4;
import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment5;
import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment6;
import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment7;
import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment8;
import kr.paulkim.enjoyyourslide.verticalfragment.VerticalFragment9;
import kr.paulkim.enjoyyourslide.yahooweather.data.Channel;
import kr.paulkim.enjoyyourslide.yahooweather.data.Item;
import kr.paulkim.enjoyyourslide.yahooweather.service.WeatherServiceCallback;
import kr.paulkim.enjoyyourslide.yahooweather.service.YahooWeatherService;


public class LockScreenMainActivity extends AppCompatActivity implements SensorEventListener,
        WeatherServiceCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    static final int MIN_DISTANCE = 100;
    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;
    private GestureDetector gestureScanner;
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;
    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;
    private YahooWeatherService service;
    private ProgressDialog dialog;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private MyApplication app;
    //private Beer_VerticalViewPager viewPager;
    private VerticalViewPager viewPager;
    private TestVerticalAdapter pagerAdapter;
    private boolean weatherSuccess = false;
    //private float downX, downY, upX, upY;
    private int downX, downY;
    private boolean vibration;
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        int currentPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int newPosition) {
            FragmentLifeCycle fragmentToHide = (FragmentLifeCycle) pagerAdapter.instantiateItem(viewPager, currentPosition);
            fragmentToHide.OnPauseFragment();

            FragmentLifeCycle fragmentToShow = (FragmentLifeCycle) pagerAdapter.instantiateItem(viewPager, newPosition);
            fragmentToShow.OnResumeFragment();

            currentPosition = newPosition;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setType(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.lockscreen_main);
        app = (MyApplication) getApplicationContext();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SharedPreferences mPref = getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE);
        vibration = mPref.getBoolean("vibration", false);
        Log.d("vibration", String.valueOf(vibration));

        //날씨 api

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(true);

        buildGoogleApiClient();
        createLocationRequest();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(600000);
        mLocationRequest.setFastestInterval(600000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<>();
        fList.add(VerticalFragment1.newInstance(1));
        fList.add(VerticalFragment2.newInstance(2));
        fList.add(VerticalFragment3.newInstance(3));
        fList.add(VerticalFragment4.newInstance(4));
        fList.add(VerticalFragment5.newInstance(5));
        fList.add(VerticalFragment6.newInstance(6));
        fList.add(VerticalFragment7.newInstance(7));
        fList.add(VerticalFragment8.newInstance(8));
        fList.add(VerticalFragment9.newInstance(9));
        return fList;
    }

    private void initViewPager() {
        viewPager = (VerticalViewPager) findViewById(R.id.VerticalViewPager);
        List<Fragment> fragments = getFragments();
        pagerAdapter = new TestVerticalAdapter(getSupportFragmentManager(), fragments);
        if (weatherSuccess) {
            viewPager.setAdapter(pagerAdapter);
            /*
            viewPager.setAdapter(new TestVerticalAdapter.Holder(getSupportFragmentManager())
                    .add(LockScreenMainFragment1.newInstance(1))
                    .add(LockScreenMainFragment2.newInstance(2))
                    .add(LockScreenMainFragment3.newInstance(3))
                    .add(LockScreenMainFragment4.newInstance(4))
                    .add(LockScreenMainFragment5.newInstance(5))
                    .add(LockScreenMainFragment6.newInstance(6))
                    .set());
                    */
            viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
            viewPager.addOnPageChangeListener(pageChangeListener);
            //viewPager.setOnPageChangeListener(pageChangeListener);
            app.setVp(viewPager);
            viewPager.setOnTouchListener(new OnSwipeTouchListener(this) {

                @Override
                public void onDoubleClick() {
                    super.onDoubleClick();
                    Toast.makeText(getApplicationContext(), "Double Click", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LockScreenMainActivity.this, LockScreenShortcutActivity.class));
                    finish();
                }
            });
        }
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.dismiss();

        Item item = channel.getItem();

        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());
        Log.d("weatherID", String.valueOf(item.getCondition().getCode()));

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        app.setWeatherIconImage(weatherIconDrawable);
        app.setTemperature(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        app.setCondition(String.valueOf(item.getCondition().getCode()));
        app.setLocation(service.getLocation());
        weatherSuccess = true;
        initViewPager();
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.dismiss();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        GlobalVars.taskID = getTaskId();
        GlobalVars.keepInApp = new Intent(LockScreenMainActivity.this, LockScreenServiceTime.class);
        startService(GlobalVars.keepInApp);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        stopService(new Intent(LockScreenMainActivity.this, LockScreenServiceTime.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //updateUI();
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        stopLocationUpdates();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    protected void startLocationUpdates() {
        if (mLastLocation == null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            updateUI();
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, LockScreenMainActivity.this);
    }

    private void updateUI() {
        if (mLastLocation != null) {
            String cityName;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());

            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getAdminArea() + " " + addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName();
                    service.refreshWeather(cityName);
                    dialog.show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //updateUI();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 250) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Toast.makeText(getApplicationContext(), "흔들흔들!", Toast.LENGTH_SHORT).show();
                    if (vibration) {
                        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(300);
                    }

                    /*
                    Intent goHome = new Intent();
                    goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    goHome.setAction("android.intent.action.MAIN");
                    goHome.addCategory("android.intent.category.HOME");
                    startActivity(goHome);
                    */
                    finish();
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                return true;

            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_refresh:
                viewPager.setCurrentItem(1);
                startLocationUpdates();


                break;

            case R.id.menu_changeCity:
                Toast.makeText(getApplicationContext(), "도시 바꾸기", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Change City");
                final EditText input = new EditText(this);
                input.setTextColor(Color.BLACK);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewPager.setCurrentItem(1);
                        String str = input.getText().toString();
                        service.refreshWeather(str);
                        dialog.show();

                    }
                });
                builder.show();
                return true;
            case R.id.menu_setting:
                Toast.makeText(getApplicationContext(), "세팅으로 가기", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LockScreenMainActivity.this, SettingsActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    public static class GlobalVars {
        public static int taskID;
        public static Intent keepInApp;
    }
}
