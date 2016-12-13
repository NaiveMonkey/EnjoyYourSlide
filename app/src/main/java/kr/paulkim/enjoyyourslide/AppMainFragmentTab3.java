package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

public class AppMainFragmentTab3 extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks {
    private TextView appSettingTextView;
    private Switch lockScreenExeBtn, VibrationBtn;

    private TextView accountSettingTextView, personIdTextView;
    private Button logoutBtn, tutorialBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_fragment_tab3, container, false);
        appSettingTextView = (TextView) mView.findViewById(R.id.appSettingTextView);
        lockScreenExeBtn = (Switch) mView.findViewById(R.id.lockScreenExeBtn);
        VibrationBtn = (Switch) mView.findViewById(R.id.VibrationBtn);

        accountSettingTextView = (TextView) mView.findViewById(R.id.accountSettingTextView);
        personIdTextView = (TextView) mView.findViewById(R.id.personIdTextView);
        logoutBtn = (Button) mView.findViewById(R.id.logoutBtn);
        tutorialBtn = (Button) mView.findViewById(R.id.tutorialBtn);

        lockScreenExeBtn.setOnClickListener(this);
        VibrationBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        tutorialBtn.setOnClickListener(this);

        SharedPreferences mPref = this.getActivity().getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE);
        lockScreenExeBtn.setChecked(mPref.getBoolean("lockScreenStatus", true));


        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }


    @Override
    public void onClick(View view) {
        if (lockScreenExeBtn.isChecked()) {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE).edit();
            editor.putBoolean("lockScreenStatus", true);
            editor.apply();
            Intent intent = new Intent(AppMainFragmentTab3.this.getActivity(), LockScreenServiceTime.class);
            getActivity().startService(intent);
        } else if (!lockScreenExeBtn.isChecked()) {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE).edit();
            editor.putBoolean("lockScreenStatus", false);
            editor.apply();
            Intent intent = new Intent(AppMainFragmentTab3.this.getActivity(), LockScreenServiceTime.class);
            getActivity().stopService(intent);
        }

        switch (view.getId()) {
            case R.id.logoutBtn:

                break;

            case R.id.tutorialBtn:
                startActivity(new Intent(getActivity(), AppTutorialActivity.class));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}