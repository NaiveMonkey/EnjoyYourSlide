package kr.paulkim.enjoyyourslide;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro2;

public class AppTutorialActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.intro1_apptutorial));
        addSlide(SampleSlide.newInstance(R.layout.intro2_apptutorial));
        addSlide(SampleSlide.newInstance(R.layout.intro3_apptutorial));
        addSlide(SampleSlide.newInstance(R.layout.intro4_apptutorial));
    }

    private void loadAppMainActivity() {
        startActivity(new Intent(AppTutorialActivity.this, SettingsActivity.class));
        finish();
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadAppMainActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadAppMainActivity();
    }

    public void getStarted(View v) {
        loadAppMainActivity();
    }

}
