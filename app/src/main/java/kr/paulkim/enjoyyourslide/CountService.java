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
 * Created by 김새미루 on 2016-08-06.
 */
public class CountService extends Service {

    private Context mContext;
    private String myJson;
    private JSONArray countNum;
    private ArrayList<String> count;
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
        count = new ArrayList<>();
        getData("http://ksmr1102.vps.phps.kr/send_count.php");
        application = (MyApplication) getApplicationContext();

        return super.onStartCommand(intent, flags, startId);
    }

    protected void showCount() {
        try {
            JSONObject obj = new JSONObject(myJson);
            countNum = obj.getJSONArray("count");
            for (int i = 0; i < countNum.length(); i++) {
                JSONObject c = countNum.getJSONObject(i);
                String num = c.getString("num");
                Log.d("COUNT12", num);

                count.add(num);
            }
            application.setCount(count);

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
                showCount();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
