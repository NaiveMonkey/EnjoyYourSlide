package kr.paulkim.enjoyyourslide;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 김새미루 on 2016-07-27.
 */
public class PhotoService extends Service {
    private Context mContext;
    private String myJson;
    private JSONArray photo_path;
    private ArrayList<String> photoList;
    private MyApplication application;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        photoList = new ArrayList<>();
        getData("http://ksmr1102.vps.phps.kr/send_vertical.php");
        application = (MyApplication) getApplicationContext();

        return super.onStartCommand(intent, flags, startId);
    }

    protected void showPhoto() {
        try {
            JSONObject obj = new JSONObject(myJson);
            photo_path = obj.getJSONArray("result");
            for (int i = 0; i < photo_path.length(); i++) {
                JSONObject c = photo_path.getJSONObject(i);
                String img_path = c.getString("img_path");
                Log.d("img_path", img_path);

                photoList.add(img_path);
            }
            application.setPhoneList(photoList);
            Log.d("img_path", String.valueOf(photoList));


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
}
