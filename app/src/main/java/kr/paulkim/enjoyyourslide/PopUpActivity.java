package kr.paulkim.enjoyyourslide;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PopUpActivity extends AppCompatActivity {
    Pac[] pacs;
    Pac pac;
    private PackageManager packageManager;
    private List<ResolveInfo> appGroup;
    private PacReceiver pacReceiver;
    private ProgressDialog dialog;

    private GridView shortcutGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up);

        shortcutGridView = (GridView) findViewById(R.id.content);

        pacReceiver = new PacReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(pacReceiver, filter);

        LoadingTask loading = new LoadingTask();
        loading.execute();
    }

    private void loadShortCut() {
        packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        appGroup = packageManager.queryIntentActivities(intent, 0);

        pacs = new Pac[appGroup.size()];
        for (int i = 0; i < appGroup.size(); i++) {
            pacs[i] = new Pac(appGroup.get(i).loadIcon(packageManager), appGroup.get(i).activityInfo.packageName, appGroup.get(i).activityInfo.loadLabel(packageManager).toString());
            pacs[i].icon = appGroup.get(i).loadIcon(packageManager);
            pacs[i].packageName = appGroup.get(i).activityInfo.packageName;
            pacs[i].label = appGroup.get(i).loadLabel(packageManager).toString();
        }
        new SortApps().exchange_sort(pacs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pacReceiver != null) {
            unregisterReceiver(pacReceiver);
        }
    }

    public class PacReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            new LoadingTask().execute();
        }
    }

    private class LoadingTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                loadShortCut();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PopUpActivity.this);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            shortcutGridView.setAdapter(new DrawerAdapter(PopUpActivity.this, pacs));

            shortcutGridView.setOnItemClickListener(new DrawerClickListener1(PopUpActivity.this, pacs, packageManager));
            shortcutGridView.setOnItemLongClickListener(new LongClickListener());
            dialog.dismiss();
        }
    }

    public class LongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

            Drawable d = pacs[position].icon;
            BitmapDrawable drawable = (BitmapDrawable) d;
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageByte = baos.toByteArray();

            /*
            String packageName = pacs[position].packageName;
            String appLabel = pacs[position].label;
            pac = new Pac(d, packageName, appLabel);

            pac.setIcon(d);
            pac.setPackageName(packageName);
            pac.setLabel(appLabel);

            Toast.makeText(getApplicationContext(), packageName + " : " + appLabel, Toast.LENGTH_LONG).show();
            */


            //pac.setIcon(pacs[position].icon);
            //pac.setPackageName(pacs[position].packageName);
            //pac.setLabel(pacs[position].label);

            String[] test = new String[3];
            test[0] = pacs[position].packageName;
            test[1] = Base64.encodeToString(imageByte, Base64.DEFAULT);
            test[2] = pacs[position].label;
            v.setTag(test);

            //String str = Arrays.toString(test);
            //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

            //List<String> list = Arrays.asList(test);
            //ArrayList<String> al = new ArrayList<>(list);

            Intent intent = new Intent();
            //intent.putExtra("test", pac);
            intent.putExtra("test", test);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
    }
}
