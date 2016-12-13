package kr.paulkim.enjoyyourslide;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class LockScreenShortcutActivity extends AppCompatActivity implements SensorEventListener {
    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;
    static boolean appLaunchable = true;
    Pac[] pacs;
    RelativeLayoutAbsListView area_shorcut;
    private GridView shortcutGridView;
    private LinearLayout deleteLinearLayout;
    /*
    private LinearLayout cellNum1, cellNum2, cellNum3, cellNum4, cellNum5, cellNum6, cellNum7, cellNum8,
            cellNum9, cellNum10, cellNum11, cellNum12, cellNum13, cellNum14, cellNum15, cellNum16;
            */
    private DrawerAdapter drawerAdapter;
    private SlidingDrawer slidingDrawer;
    private GridLayout homeView;
    private ImageView deleteImageView;
    private ProgressDialog dialog;
    private List<ResolveInfo> appGroup;
    private PackageManager packageManager;
    private PacReceiver pacReceiver;
    private MyDBManager myDBManager = null;
    private SQLiteDatabase db = null;
    private LayoutInflater li;
    private LinearLayout ll, ll2;
    private ImageView icon, transImage;
    private TextView name, transText;
    private GridLayout.Spec row;
    private GridLayout.Spec col;
    private float initialX;
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;
    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreen_shortcut);

        shortcutGridView = (GridView) findViewById(R.id.content);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.drawer);
        deleteLinearLayout = (LinearLayout) findViewById(R.id.deleteLinearLayout);
        deleteImageView = (ImageView) findViewById(R.id.deleteImageView);
        homeView = (GridLayout) findViewById(R.id.home_view);

        /*
        cellNum1 = (LinearLayout) findViewById(R.id.cellNum1);
        cellNum2 = (LinearLayout) findViewById(R.id.cellNum2);
        cellNum3 = (LinearLayout) findViewById(R.id.cellNum3);
        cellNum4 = (LinearLayout) findViewById(R.id.cellNum4);
        cellNum5 = (LinearLayout) findViewById(R.id.cellNum5);
        cellNum6 = (LinearLayout) findViewById(R.id.cellNum6);
        cellNum7 = (LinearLayout) findViewById(R.id.cellNum7);
        cellNum8 = (LinearLayout) findViewById(R.id.cellNum8);
        cellNum9 = (LinearLayout) findViewById(R.id.cellNum9);
        cellNum10 = (LinearLayout) findViewById(R.id.cellNum10);
        cellNum11 = (LinearLayout) findViewById(R.id.cellNum11);
        cellNum12 = (LinearLayout) findViewById(R.id.cellNum12);
        cellNum13 = (LinearLayout) findViewById(R.id.cellNum13);
        cellNum14 = (LinearLayout) findViewById(R.id.cellNum14);
        cellNum15 = (LinearLayout) findViewById(R.id.cellNum15);
        cellNum16 = (LinearLayout) findViewById(R.id.cellNum16);
        */
        //loadShortCut();

        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {

            @Override
            public void onDrawerOpened() {
                appLaunchable = true;
            }
        });

        pacReceiver = new PacReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(pacReceiver, filter);

        //shortcutGridView.setAdapter(shortcutAdapter);
        /*
        shortcutGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        shortcutGridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
        */
        LoadingTask loading = new LoadingTask();
        loading.execute();

        myDBManager = new MyDBManager(this, "lock_screen.db", null, 1);
        db = myDBManager.getReadableDatabase();
        /*
        Cursor count = db.rawQuery("select count (*) from SHORTCUT", null);
        count.moveToFirst();
        if (count.getInt(0) == 0) {
            for (int i = 1; i < 17; i++) {
                li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ll2 = (LinearLayout) li.inflate(R.layout.lockscreen_main_trans, null);
                transImage = ((ImageView) ll2.findViewById(R.id.transImage));
                transText = ((TextView) ll2.findViewById(R.id.transText));

                homeView.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
                homeView.setUseDefaultMargins(true);

                ll2.setId((int) System.currentTimeMillis());
                //int count = homeView.getChildCount();
                Log.e("SHORTCUT_COUNT", String.valueOf(i));

                int rowNum, colNum;

                if (0 < i && i < 5) {
                    rowNum = 0;
                    colNum = i - 1;
                } else if (4 < i && i < 9) {
                    rowNum = 1;
                    colNum = i - 5;
                } else if (8 < i && i < 13) {
                    rowNum = 2;
                    colNum = i - 9;
                } else {
                    rowNum = 3;
                    colNum = i - 13;
                }

                row = GridLayout.spec(rowNum);
                col = GridLayout.spec(colNum);
                Log.e("rowcol", String.valueOf(rowNum) + " : " + String.valueOf(colNum));
                GridLayout.LayoutParams rowcol = new GridLayout.LayoutParams(row, col);

                homeView.addView(ll2, rowcol);

                ContentValues addRowValue = new ContentValues();
                addRowValue.put("_id", ll2.getId());
                addRowValue.put("row", rowNum);
                addRowValue.put("col", colNum);
                db.insert("TRANSPARENT", null, addRowValue);
            }
            count.close();
        } else {
            Cursor trans = db.rawQuery("select * from TRANSPARENT order by row asc, col asc;", null);
            while (trans.moveToNext()){
                li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ll2 = new LinearLayout(LockScreenShortcutActivity.this);
                ll2 = (LinearLayout) li.inflate(R.layout.lockscreen_main_trans, null);

                transImage = new ImageView(LockScreenShortcutActivity.this);
                transText = new TextView(LockScreenShortcutActivity.this);
                transImage = ((ImageView) ll2.findViewById(R.id.transImage));
                transText = ((TextView) ll2.findViewById(R.id.transText));

                homeView.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
                homeView.setUseDefaultMargins(true);

                int rowNum = trans.getInt(trans.getColumnIndex("row"));
                int colNum = trans.getInt(trans.getColumnIndex("col"));
                row = GridLayout.spec(rowNum);
                col = GridLayout.spec(colNum);
                GridLayout.LayoutParams rowcol = new GridLayout.LayoutParams(row, col);

                homeView.addView(ll2, rowcol);

            }
            trans.close();
            */

        Cursor cursor = db.rawQuery("select * from SHORTCUT order by row asc, col asc;", null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            final String id = Integer.toString(_id);
            final String packageName = cursor.getString(cursor.getColumnIndex("packageName"));
            byte[] bb = cursor.getBlob(cursor.getColumnIndex("appIcon"));
            String appName = cursor.getString(cursor.getColumnIndex("appName"));

            final int rowNum = cursor.getInt(cursor.getColumnIndex("row"));
            final int colNum = cursor.getInt(cursor.getColumnIndex("col"));
            row = GridLayout.spec(rowNum);
            col = GridLayout.spec(colNum);
            GridLayout.LayoutParams rowcol = new GridLayout.LayoutParams(row, col);

            li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ll = (LinearLayout) li.inflate(R.layout.lockscreen_main_simple, null);

            icon = ((ImageView) ll.findViewById(R.id.appIconImageView));
            name = ((TextView) ll.findViewById(R.id.appNameTextView));


                /*
                String[] data = new String[3];
                data[0] = packageName;
                data[1] = Base64.encodeToString(bb, Base64.DEFAULT);
                data[2] = appName;
                ll.setTag(data);
                */

            //homeView.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
            //homeView.setUseDefaultMargins(true);

            name.setTextColor(Color.WHITE);
            name.setMaxLines(2);
            name.setHeight(10);
            name.setWidth(222);
            name.setText(appName);
            icon.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));

            ll.setId(_id);

            homeView.addView(ll);

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                    startActivity(intent);
                    Log.d("aaaa", String.valueOf(rowNum));
                    Log.d("aaaa", String.valueOf(colNum));
                }
            });
            ll.setOnLongClickListener(new AppLongClickListener());
            deleteLinearLayout.setOnDragListener(new DragListener());
            homeView.setOnDragListener(new DragListener());
        }
        cursor.close();
        //}
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
            //pacs[i].name = appGroup.get(i).activityInfo.name;
            pacs[i].label = appGroup.get(i).loadLabel(packageManager).toString();
        }
        new SortApps().exchange_sort(pacs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 250) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Toast.makeText(getApplicationContext(), "흔들흔들!", Toast.LENGTH_SHORT).show();
                    /*
                    Intent goHome = new Intent();
                    goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    goHome.setAction("android.intent.action.MAIN");
                    goHome.addCategory("android.intent.category.HOME");
                    startActivity(goHome);
                    */
                    finish();
                }
                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pacReceiver != null) {
            unregisterReceiver(pacReceiver);
        }

        db.close();
    }

    private int calculateNewIndex(float x, float y) {
        // calculate which column to move to
        final float cellWidth = homeView.getWidth() / homeView.getColumnCount();
        Log.d("INDEX", String.valueOf(cellWidth));
        final int column = (int) (x / cellWidth);
        Log.d("INDEX", String.valueOf(column));

        // calculate which row to move to
        final float cellHeight = homeView.getHeight() / homeView.getRowCount();
        Log.d("INDEX", String.valueOf(cellHeight));
        final int row = (int) Math.floor(y / cellHeight);
        Log.d("INDEX", String.valueOf(row));

        // the items in the GridLayout is organized as a wrapping list
        // and not as an actual grid, so this is how to get the new index
        int index;
        if (row * homeView.getColumnCount() + column >= homeView.getChildCount()) {
            index = homeView.getChildCount() - 1;
            Log.d("INDEX", String.valueOf(row * homeView.getColumnCount() + column));
        } else {
            index = row * homeView.getColumnCount() + column;
        }
        return index;
    }

    private void Drag(DragEvent event, View view) {
        // get the new list index
        final int index = calculateNewIndex(event.getX(), event.getY());
        //String[] data;
        //data = (String[]) view.getTag();

        homeView.removeView(view);
        // remove the view from the old position

        Cursor cursor = db.rawQuery("select * from SHORTCUT where _id = " + "'" + Integer.toString(view.getId()) + "'", null);
        cursor.moveToFirst();
        int _id = cursor.getInt(cursor.getColumnIndex("_id"));
        final String id = Integer.toString(_id);
        Log.d("TAG", id);
        final String packageName = cursor.getString(cursor.getColumnIndex("packageName"));
        //Log.d("TAG", packageName);
        byte[] bb = cursor.getBlob(cursor.getColumnIndex("appIcon"));
        String appName = cursor.getString(cursor.getColumnIndex("appName"));
        //Log.d("TAG", appName);
        int rowNumTest = cursor.getInt(cursor.getColumnIndex("row"));
        int colNumTest = cursor.getInt(cursor.getColumnIndex("col"));
        row = GridLayout.spec(rowNumTest);
        col = GridLayout.spec(colNumTest);
        //GridLayout.LayoutParams rowcol = new GridLayout.LayoutParams(row, col);
        db.delete("SHORTCUT", "_id" + "=" + id, null);

        int i, j;
        int position = -1;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (rowNumTest == i && colNumTest == j) {
                    position += 1;
                }
            }
        }
        Log.i("position", String.valueOf(position));


        //view.setId((int)System.currentTimeMillis());
        // and push to the new
        homeView.addView(view, index);
        Log.d("childIndex", String.valueOf(index));
        view.setVisibility(View.VISIBLE);

        int rowNum, colNum;
        if (0 <= index && index < 4) {
            rowNum = 0;
            colNum = index;
        } else if (3 < index && index < 8) {
            rowNum = 1;
            colNum = index - 4;
        } else if (7 < index && index < 12) {
            rowNum = 2;
            colNum = index - 8;
        } else {
            rowNum = 3;
            colNum = index - 12;
        }


        //index에 있었던 위치의 아이콘을 remove된 view의 위치로 변화시켜줌
        ContentValues update = new ContentValues();
        update.put("row", rowNumTest);
        update.put("col", colNumTest);
        db.update("SHORTCUT", update, "row=" + String.valueOf(rowNum) + " and col=" + String.valueOf(colNum), null);

        // index가 2가지로 나뉘어지니까 이걸 잘 배분해보자

        byte[] decodeByte = Base64.decode(Base64.encodeToString(bb, Base64.DEFAULT), Base64.DEFAULT);
        ContentValues values = new ContentValues();
        values.put("_id", id);
        Log.d("idid", id);
        values.put("packageName", packageName);
        Log.d("TAG", packageName);
        values.put("appIcon", decodeByte);
        values.put("appName", appName);
        Log.d("TAG", appName);
        values.put("row", rowNum);
        Log.d("asdfasdf", String.valueOf(rowNum));
        values.put("col", colNum);
        Log.d("asdfasdf", String.valueOf(colNum));
        db.insert("SHORTCUT", null, values);

        cursor.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                float finalX = event.getX();

                if (initialX > finalX) {
                    startActivity(new Intent(LockScreenShortcutActivity.this, LockScreenMainActivity.class));
                    finish();
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public class PacReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            new LoadingTask().execute();
            //loadShortCut();
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
            dialog = new ProgressDialog(LockScreenShortcutActivity.this);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //shortcutGridView.setAdapter(new LockScreenShortcutAdapter(LockScreenShortcutActivity.this, pacs));
            // shortcutGridView.setAdapter(new LockScreenShortcutAdapter(LockScreenShortcutActivity.this, appGroup, packageManager));

            shortcutGridView.setAdapter(new DrawerAdapter(LockScreenShortcutActivity.this, pacs));

            shortcutGridView.setOnItemClickListener(new DrawerClickListener1(LockScreenShortcutActivity.this, pacs, packageManager));
            shortcutGridView.setOnItemLongClickListener(new DrawerLongClickListener());
            //shortcutGridView.setOnItemLongClickListener(new DrawerLongClickListener1(LockScreenShortcutActivity.this,
            //        slidingDrawer, deleteLinearLayout, deleteImageView, homeView, pacs, packageManager));
            dialog.dismiss();
        }
    }

    public class DrawerLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            appLaunchable = false;

            Drawable d = pacs[position].icon;
            BitmapDrawable drawable = (BitmapDrawable) d;
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageByte = baos.toByteArray();

            li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ll = new LinearLayout(LockScreenShortcutActivity.this);
            ll = (LinearLayout) li.inflate(R.layout.lockscreen_main_simple, null);

            ll2 = new LinearLayout(LockScreenShortcutActivity.this);
            ll2 = (LinearLayout) li.inflate(R.layout.lockscreen_main_trans, null);
            transImage = new ImageView(LockScreenShortcutActivity.this);
            transText = new TextView(LockScreenShortcutActivity.this);
            transImage = ((ImageView) ll2.findViewById(R.id.transImage));
            transText = ((TextView) ll2.findViewById(R.id.transText));

            //homeView.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
            //homeView.setUseDefaultMargins(true);

            icon = new ImageView(LockScreenShortcutActivity.this);
            name = new TextView(LockScreenShortcutActivity.this);
            icon = ((ImageView) ll.findViewById(R.id.appIconImageView));
            name = ((TextView) ll.findViewById(R.id.appNameTextView));

            name.setTextColor(Color.WHITE);
            name.setMaxLines(2);
            name.setHeight(10);
            name.setWidth(222);
            icon.setImageDrawable(pacs[position].icon);
            name.setText(pacs[position].label);

            String[] data = new String[3];
            data[0] = pacs[position].packageName;
            data[1] = Base64.encodeToString(imageByte, Base64.DEFAULT);
            data[2] = pacs[position].label;
            ll.setTag(data);
            ll.setId((int) System.currentTimeMillis());

            Cursor shortcut = db.rawQuery("select count (*) from SHORTCUT", null);
            shortcut.moveToFirst();
            int count = shortcut.getInt(0) + 1;
            shortcut.close();
            //int count = homeView.getChildCount();
            //Log.d("child", String.valueOf(count));
            int rowNum, colNum;

            if (0 < count && count < 5) {
                rowNum = 0;
                colNum = count - 1;
            } else if (4 < count && count < 9) {
                rowNum = 1;
                colNum = count - 5;
            } else if (8 < count && count < 13) {
                rowNum = 2;
                colNum = count - 9;
            } else {
                rowNum = 3;
                colNum = count - 13;
            }

            Log.e("rowcol", String.valueOf(rowNum) + " : " + String.valueOf(colNum));

            ContentValues addRowValue = new ContentValues();
            addRowValue.put("_id", ll.getId());
            addRowValue.put("packageName", pacs[position].packageName);
            addRowValue.put("appIcon", imageByte);
            addRowValue.put("appName", pacs[position].label);
            addRowValue.put("row", rowNum);
            addRowValue.put("col", colNum);
            db.insert("SHORTCUT", null, addRowValue);

            /*
            Cursor trans = db.rawQuery("select * from TRANSPARENT where row = " + String.valueOf(rowNum) + " and col = " + String.valueOf(colNum), null);
            trans.moveToFirst();
            int _transId = trans.getInt(trans.getColumnIndex("_id"));
            String transId = String.valueOf(_transId);
            Log.e("transId", transId);
            db.delete("TRANSPARENT", "_id" + "=" + transId, null);
            trans.close();

            db.delete("TRANSPARENT", "row = " + String.valueOf(rowNum) + " and col = " + String.valueOf(colNum), null);
            int remove;

            if (rowNum == 0 && 0 <= colNum && colNum <= 3) {
                remove = colNum;
            } else if (rowNum == 1 && 0 <= colNum && colNum <= 3) {
                remove = 4 + colNum;
            } else if (rowNum == 2 && 0 <= colNum && colNum <= 3) {
                remove = 8 + colNum;
            } else {
                remove = 12 + colNum;
            }
            homeView.removeViewsInLayout(rowNum, colNum);
            */

            row = GridLayout.spec(rowNum);
            col = GridLayout.spec(colNum);
            GridLayout.LayoutParams rowcol = new GridLayout.LayoutParams(row, col);

            /*
            if(count ==1){
                cellNum1.addView(ll);
            } else if (count == 2){
                cellNum2.addView(ll);
            } else if (count == 3){
                cellNum3.addView(ll);
            } else if (count == 4){
                cellNum4.addView(ll);
            }
            */
            homeView.addView(ll);

            ll.setOnClickListener(new AppClickListener(pacs, LockScreenShortcutActivity.this, packageManager));

            slidingDrawer.animateClose();
            slidingDrawer.bringToFront();

            Toast.makeText(getApplicationContext(), Integer.toString(ll.getId()), Toast.LENGTH_LONG).show();
            ll.setOnLongClickListener(new AppLongClickListener());
            deleteLinearLayout.setOnDragListener(new DragListener());
            /*
            cellNum1.setOnDragListener(new DragListener());
            cellNum2.setOnDragListener(new DragListener());
            cellNum3.setOnDragListener(new DragListener());
            cellNum4.setOnDragListener(new DragListener());
            */
            homeView.setOnDragListener(new DragListener());
            return false;
        }
    }

    public class AppLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(clipData, dragShadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            slidingDrawer.setVisibility(View.INVISIBLE);
            deleteImageView.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public class DragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            //final ViewGroup viewGroup = (ViewGroup) view.getParent();
            //final LinearLayout containView = (LinearLayout) v;

            switch (event.getAction()) {

                case DragEvent.ACTION_DRAG_LOCATION:
                    // do nothing if hovering above own position
                    if (view == v) {
                        deleteImageView.setVisibility(View.INVISIBLE);
                        slidingDrawer.setVisibility(View.VISIBLE);
                        return true;
                    }
                    /*
                    if (v == cellNum1) {
                        Drag(event, view);
                        //viewGroup.removeView(view);
                        //containView.addView(view);
                        //view.setVisibility(View.VISIBLE);
                    } else if (v == cellNum2){
                        Drag(event, view);
                        //viewGroup.removeView(view);
                        //containView.addView(view);
                        //view.setVisibility(View.VISIBLE);
                    } else if (v == cellNum3){
                        Drag(event, view);
                        //viewGroup.removeView(view);
                        //containView.addView(view);
                        //view.setVisibility(View.VISIBLE);
                    } else if (v == cellNum4){
                        Drag(event, view);
                        //viewGroup.removeView(view);
                        //containView.addView(view);
                        //view.setVisibility(View.VISIBLE);
                    }
                    */
                    Drag(event, view);
                    break;

                case DragEvent.ACTION_DROP:
                    if (v == deleteLinearLayout) {
                        db.delete("SHORTCUT", "_id" + "=" + Integer.toString(view.getId()), null);
                        //viewGroup.removeView(view);
                        homeView.removeView(view);
                        deleteImageView.setVisibility(View.INVISIBLE);
                        slidingDrawer.setVisibility(View.VISIBLE);
                    }
                    //view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    deleteImageView.setVisibility(View.INVISIBLE);
                    slidingDrawer.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        }
    }
}
