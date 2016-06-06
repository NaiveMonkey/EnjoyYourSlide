package kr.paulkim.enjoyyourslide;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;


public class IntroActivity extends AppCompatActivity {
    Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_intro);

        mHandler = new Handler();
        mHandler.postDelayed(mrun, 2000);
    }


    Runnable mrun = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(mrun);
    }


}
