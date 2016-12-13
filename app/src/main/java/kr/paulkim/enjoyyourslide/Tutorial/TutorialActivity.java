package kr.paulkim.enjoyyourslide.Tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import kr.paulkim.enjoyyourslide.R;
import kr.paulkim.enjoyyourslide.SettingsActivity;
import kr.paulkim.enjoyyourslide.TestHorizontalAdapter;
import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Fragment> numberList;
    private ImageView skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        numberList = new ArrayList<>();
        numberList.add(TutorialFragment1.newInstance());
        numberList.add(TutorialFragment2.newInstance());
        numberList.add(TutorialFragment3.newInstance());
        numberList.add(TutorialFragment4.newInstance());

        viewPager = (ViewPager) findViewById(R.id.tutorialViewPager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        TestHorizontalAdapter viewPagerAdapter = new TestHorizontalAdapter(getSupportFragmentManager(), numberList);
        viewPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(viewPager);

        skipBtn = (ImageView) findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSettingActivity();
            }
        });
    }

    private void loadSettingActivity() {
        startActivity(new Intent(TutorialActivity.this, SettingsActivity.class));
        finish();
    }
}
