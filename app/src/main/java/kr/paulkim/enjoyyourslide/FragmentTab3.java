package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class FragmentTab3 extends Fragment implements View.OnClickListener{
    private Switch lockScreenExeBtn;
    private TextView lockScreenText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_fragment_tab3, container, false);
        lockScreenExeBtn = (Switch)mView.findViewById(R.id.lockScreenExeBtn);
        lockScreenText = (TextView)mView.findViewById(R.id.lockScreenExeTextView);
        lockScreenExeBtn.setOnClickListener(this);

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
        if(lockScreenExeBtn.isChecked()){
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE).edit();
            editor.putBoolean("lockScreenStatus", true);
            editor.apply();
            Intent intent = new Intent(FragmentTab3.this.getActivity(), ScreenService.class);
            getActivity().startService(intent);
        } else{
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("lockScreenEXE", Context.MODE_PRIVATE).edit();
            editor.putBoolean("lockScreenStatus", false);
            editor.apply();
            Intent intent = new Intent(FragmentTab3.this.getActivity(), ScreenService.class);
            getActivity().stopService(intent);
        }
    }
}