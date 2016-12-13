package kr.paulkim.enjoyyourslide.horizontalfragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kr.paulkim.enjoyyourslide.FragmentLifeCycle;
import kr.paulkim.enjoyyourslide.MyApplication;
import kr.paulkim.enjoyyourslide.R;

/**
 * Created by 김새미루 on 2016-08-05.
 */
public class HorizontalFragment2_2 extends Fragment implements FragmentLifeCycle {
    private MyApplication application;
    private ImageView fragmentImage2;
    private ImageView backBtn;
    private String myJson;
    private JSONArray photo_path;
    private ArrayList<String> h_photoList;

    public static HorizontalFragment2_2 newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        HorizontalFragment2_2 fragment = new HorizontalFragment2_2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lockscreen_main_fragment2_3, container, false);
        h_photoList = new ArrayList<>();
        application = (MyApplication) getActivity().getApplicationContext();
        fragmentImage2 = (ImageView) view.findViewById(R.id.fragmentImage2_3);
        //fragmentImage2.setPadding(0, 110, 0, 0);
        backBtn = (ImageView) view.findViewById(R.id.backBtn);
        backBtn.bringToFront();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                application.getViewPager().setCurrentItem(0, true);
            }
        });

        return view;
    }

    protected void showPhoto() {
        try {
            JSONObject obj = new JSONObject(myJson);
            photo_path = obj.getJSONArray("result");
            for (int i = 0; i < photo_path.length(); i++) {
                JSONObject c = photo_path.getJSONObject(i);
                String img_path = c.getString("img_path");
                Log.d("img_path", img_path);

                h_photoList.add(img_path);
            }
            application.setH_phoneList(h_photoList);
            Log.d("img_path", String.valueOf(h_photoList));
            Log.d("horizontal_path", String.valueOf(h_photoList.get(0)));
            Glide.with(this).load(String.valueOf(h_photoList.get(0))).into(fragmentImage2);
            backBtn.setImageResource(R.drawable.home);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];

                BufferedReader bufferedReader;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String result) {
                myJson = result;
                showPhoto();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    @Override
    public void OnPauseFragment() {

    }

    @Override
    public void OnResumeFragment() {
        getData("http://ksmr1102.vps.phps.kr/send_horizontal2.php");
    }
}
