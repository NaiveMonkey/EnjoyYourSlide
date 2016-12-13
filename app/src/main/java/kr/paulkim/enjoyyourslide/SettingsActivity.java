package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import kr.paulkim.enjoyyourslide.Tutorial.TutorialActivity;


/**
 * Created by 김새미루 on 2016-06-13.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private SwitchCompat lockScreenExeBtn, vibrationBtn;
    private TextView personIdTextView;
    private ImageView logoutBtn, tutorialBtn;

    private GoogleApiClient mGoogleApiClient;
    private MyApplication app;

    private int on_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();

        lockScreenExeBtn = (SwitchCompat) findViewById(R.id.lockScreenExeBtn);
        vibrationBtn = (SwitchCompat) findViewById(R.id.VibrationBtn);

        personIdTextView = (TextView) findViewById(R.id.personIdTextView);
        logoutBtn = (ImageView) findViewById(R.id.logoutBtn);
        tutorialBtn = (ImageView) findViewById(R.id.tutorialBtn);

        app = (MyApplication) getApplicationContext();
        personIdTextView = (TextView) findViewById(R.id.personIdTextView);
        personIdTextView.setText(app.getEmail());

        lockScreenExeBtn.setOnClickListener(this);
        vibrationBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        tutorialBtn.setOnClickListener(this);

        SharedPreferences mPref = getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE);
        lockScreenExeBtn.setChecked(mPref.getBoolean("lockScreenStatus", false));
        vibrationBtn.setChecked(mPref.getBoolean("vibration", false));

    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }
    }
    */

    @Override
    public void onClick(View view) {
        if (lockScreenExeBtn.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE).edit();
            editor.putBoolean("lockScreenStatus", true);
            editor.apply();
            Intent intent = new Intent(SettingsActivity.this, LockScreenService.class);
            startService(intent);
            Intent photo = new Intent(SettingsActivity.this, PhotoService.class);
            startService(photo);
            Intent count = new Intent(SettingsActivity.this, CountService.class);
            startService(count);

        } else if (!lockScreenExeBtn.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE).edit();
            editor.putBoolean("lockScreenStatus", false);
            editor.apply();
            Intent intent = new Intent(SettingsActivity.this, LockScreenService.class);
            stopService(intent);
            Intent i = new Intent(SettingsActivity.this, LockScreenServiceTime.class);
            stopService(i);
            Intent photo = new Intent(SettingsActivity.this, PhotoService.class);
            stopService(photo);
            Intent count = new Intent(SettingsActivity.this, CountService.class);
            stopService(count);
        }

        if (vibrationBtn.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE).edit();
            editor.putBoolean("vibration", true);
            editor.apply();
        } else if (!vibrationBtn.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE).edit();
            editor.putBoolean("vibration", false);
            editor.apply();
        }

        switch (view.getId()) {
            case R.id.logoutBtn:
                if (app.getSeparator() == "1") {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    if (lockScreenExeBtn.isChecked()) {
                                        lockScreenExeBtn.performClick();
                                    }
                                    if (mGoogleApiClient.isConnected())
                                        mGoogleApiClient.disconnect();
                                    // [START_EXCLUDE]
                                    // [END_EXCLUDE]
                                }
                            });
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                    finish();
                    break;
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                    editor.putString(Config.EMAIL_SHARED_PREF, "");
                    editor.apply();

                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                    finish();
                    break;
                }

            case R.id.tutorialBtn:
                startActivity(new Intent(SettingsActivity.this, TutorialActivity.class));
        }
    }

    /*
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personEmail = acct.getEmail();
            personIdTextView.setText(personEmail);
        }
    }
    */

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
