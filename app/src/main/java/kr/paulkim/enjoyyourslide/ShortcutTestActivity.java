package kr.paulkim.enjoyyourslide;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by 김새미루 on 2016-06-29.
 */
public class ShortcutTestActivity extends AppCompatActivity {
    Pac[] pacs;

    private LinearLayout deleteLinearLayout;
    private DrawerAdapterTest testAdapter;

    private GridView homeView;
    private ImageView deleteImageView, selectImageView;

    private ProgressDialog dialog;
    private PackageManager packageManager;
    private ArrayList<String> shortcutList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_shortcut);

        SharedPreferences mPref = getSharedPreferences("shortcut", Context.MODE_PRIVATE);
        String str = mPref.getString("save", null);
        packageManager = getPackageManager();
        homeView = (GridView) findViewById(R.id.home_view);
        deleteLinearLayout = (LinearLayout) findViewById(R.id.deleteLinearLayout);
        deleteImageView = (ImageView) findViewById(R.id.deleteImageView);
        selectImageView = (ImageView) findViewById(R.id.selectImageView);
        selectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShortcutTestActivity.this, PopUpActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        shortcutList = new ArrayList<>();
        if (str != null) {
            shortcutList = StringToList(str);
        }
        //String [] test = shortcutList.toArray(new String[shortcutList.size()]);
        //List list = Arrays.asList(test);
        testAdapter = new DrawerAdapterTest(this, shortcutList, packageManager);
        //testAdapter = new DrawerAdapterTest(this, shortcutList, packageManager, homeView,
        //        deleteLinearLayout, deleteImageView, selectImageView);

        homeView.setAdapter(testAdapter);
        homeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String str = shortcutList.get(position);
                String[] test = str.split("!!!");
                Intent intent = packageManager.getLaunchIntentForPackage(test[0]);
                startActivity(intent);
            }
        });

        homeView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                ClipData clipData = ClipData.newPlainText("", "");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(clipData, dragShadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                selectImageView.setVisibility(View.INVISIBLE);
                deleteImageView.setVisibility(View.VISIBLE);
                return true;
            }
        });

        homeView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final View view = (View) event.getLocalState();
                switch (event.getAction()) {

                    case DragEvent.ACTION_DRAG_LOCATION:
                        // do nothing if hovering above own position
                        if (view == v) {
                            deleteImageView.setVisibility(View.INVISIBLE);
                            selectImageView.setVisibility(View.VISIBLE);
                            return true;
                        }
                        // get the new list index
                        //final int index = calculateNewIndex(event.getX(), event.getY());

                        // remove the view from the old position
                        homeView.removeView(view);
                        // and push to the new

                        homeView.addView(view);
                        view.setVisibility(View.VISIBLE);

                        if (v == deleteLinearLayout) {
                            homeView.removeView(view);

                            deleteImageView.setVisibility(View.INVISIBLE);
                            selectImageView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case DragEvent.ACTION_DROP:

                        //view.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        deleteImageView.setVisibility(View.INVISIBLE);
                        selectImageView.setVisibility(View.VISIBLE);

                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //ArrayList<String> test = (ArrayList<String>)getIntent().getSerializableExtra("test");
                String[] test = data.getExtras().getStringArray("test");
                /*
                Bundle bundle = getIntent().getExtras();
                Pac pac = data.getParcelableExtra("test");
                Pac pac1 = data.getParcelableExtra("icon");
                */
                final String packageName = test[0];
                String appIcon = test[1];
                String appName = test[2];
                String sum = packageName + "!!!" + appIcon + "!!!" + appName;
                shortcutList.add(sum);
                testAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences("shortcut", Context.MODE_PRIVATE).edit();
        editor.putString("save", ListToString(shortcutList));
        editor.apply();
    }

    public String ListToString(ArrayList<String> list) {
        String string = "";
        for (int i = 0; i < list.size() - 1; i++) {
            string = string + list.get(i) + "^";
        }
        string = string + list.get(list.size() - 1);
        return string;
    }

    public ArrayList<String> StringToList(String str) {
        String[] strlist = str.split("\\^");
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < strlist.length; i++) {
            list.add(strlist[i]);
        }
        return list;
    }

}