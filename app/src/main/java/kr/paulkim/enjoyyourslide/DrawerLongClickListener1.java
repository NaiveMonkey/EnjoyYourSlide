package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/**
 * Created by 김새미루 on 2016-06-17.
 */


public class DrawerLongClickListener1 implements AdapterView.OnItemLongClickListener {

    Context mContext;
    SlidingDrawer drawerForAdapter;
    LinearLayout deleteLinearLayout;
    ImageView deleteImageView;
    GridLayout homeViewForAdapter;
    Pac[] pacsForListener;
    PackageManager packageManager;

    public DrawerLongClickListener1(Context ctxt, SlidingDrawer slidingDrawer,
                                    LinearLayout deleteLinearLayout, ImageView deleteImageView, GridLayout homeView,
                                    Pac[] pacs, PackageManager packageManager) {
        mContext = ctxt;
        drawerForAdapter = slidingDrawer;
        this.deleteLinearLayout = deleteLinearLayout;
        this.deleteImageView = deleteImageView;
        homeViewForAdapter = homeView;
        pacsForListener = pacs;
        this.packageManager = packageManager;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                   long id) {
        LockScreenShortcutActivity.appLaunchable = false;
        //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(item.getWidth(), item.getHeight());
        //lp.leftMargin = (int) item.getX();
        //lp.topMargin = (int) item.getY();
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
        //lp.topMargin = 1;


        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll = (LinearLayout) li.inflate(R.layout.lockscreen_main_simple, null);


        ((ImageView) ll.findViewById(R.id.appIconImageView)).setImageDrawable(((ImageView) view.findViewById(R.id.appIconImageView)).getDrawable());

        ((TextView) ll.findViewById(R.id.appNameTextView)).setTextColor(Color.WHITE);
        ((TextView) ll.findViewById(R.id.appNameTextView)).setMaxLines(2);
        ((TextView) ll.findViewById(R.id.appNameTextView)).setHeight(10);
        ((TextView) ll.findViewById(R.id.appNameTextView)).setWidth(222);
        ((TextView) ll.findViewById(R.id.appNameTextView)).setText(((TextView) view.findViewById(R.id.appNameTextView)).getText());

        ll.setOnClickListener(new AppClickListener(pacsForListener, mContext, packageManager));
        String[] data = new String[2];
        data[0] = pacsForListener[position].packageName;
        data[1] = pacsForListener[position].label;

        ll.setTag(data);

        ll.setId(View.generateViewId());

        homeViewForAdapter.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        homeViewForAdapter.setUseDefaultMargins(true);
        homeViewForAdapter.addView(ll);

        drawerForAdapter.animateClose();
        drawerForAdapter.bringToFront();


        ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                /*
                ClipData clipData = ClipData.newPlainText("", "");
                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(clipData, dragShadowBuilder, view, 0);

                view.setVisibility(View.INVISIBLE);
                drawerForAdapter.setVisibility(View.INVISIBLE);
                deleteImageView.setVisibility(View.VISIBLE);
                */
                view.setOnTouchListener(new AppTouchListener());
                return false;
            }
        });


        deleteLinearLayout.setOnDragListener(new DragListener());
        homeViewForAdapter.setOnDragListener(new DragListener());


        /*
        ll.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final View view = (View) event.getLocalState();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_LOCATION:
                        // do nothing if hovering above own position
                        if (view == v) return true;
                        // get the new list index
                        final int index = calculateNewIndex(event.getX(), event.getY());
                        // remove the view from the old position
                        homeViewForAdapter.removeView(view);
                        // and push to the new
                        homeViewForAdapter.addView(view);

                        if (v == deleteLinearLayout) {
                            homeViewForAdapter.removeView(view);
                        }
                        break;
                    case DragEvent.ACTION_DROP:
                        view.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        if (!event.getResult()) {
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    view.setVisibility(View.VISIBLE);
                                    deleteImageView.setVisibility(View.INVISIBLE);
                                    drawerForAdapter.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        break;
                }
                return true;
            }
        });
        */

        //ll.setOnTouchListener(new AppTouchListener(item.getWidth()));

        return false;
    }

    private int calculateNewIndex(float x, float y) {
        // calculate which column to move to
        final float cellWidth = homeViewForAdapter.getWidth() / homeViewForAdapter.getColumnCount();
        final int column = (int) (x / cellWidth);

        // calculate which row to move to
        final float cellHeight = homeViewForAdapter.getHeight() / homeViewForAdapter.getRowCount();
        final int row = (int) Math.floor(y / cellHeight);

        // the items in the GridLayout is organized as a wrapping list
        // and not as an actual grid, so this is how to get the new index
        int index = row * homeViewForAdapter.getColumnCount() + column;
        if (index >= homeViewForAdapter.getChildCount()) {
            index = homeViewForAdapter.getChildCount() - 1;
        }

        return index;
    }

    //ll.setOnTouchListener(new AppTouchListener(item.getWidth()));

    class DragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final View view = (View) event.getLocalState();
            switch (event.getAction()) {

                case DragEvent.ACTION_DRAG_LOCATION:
                    // do nothing if hovering above own position
                    if (view == v) {
                        deleteImageView.setVisibility(View.INVISIBLE);
                        drawerForAdapter.setVisibility(View.VISIBLE);
                        return true;
                    }
                    // get the new list index
                    final int index = calculateNewIndex(event.getX(), event.getY());

                    // remove the view from the old position
                    homeViewForAdapter.removeView(view);

                    // and push to the new
                    homeViewForAdapter.addView(view, index);
                    view.setVisibility(View.VISIBLE);

                    if (v == deleteLinearLayout) {
                        homeViewForAdapter.removeView(view);
                        deleteImageView.setVisibility(View.INVISIBLE);
                        drawerForAdapter.setVisibility(View.VISIBLE);
                    }

                    break;
                case DragEvent.ACTION_DROP:

                    //view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    deleteImageView.setVisibility(View.INVISIBLE);
                    drawerForAdapter.setVisibility(View.VISIBLE);
                    /*
                    if (!event.getResult()) {
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                view.setVisibility(View.VISIBLE);
                                deleteImageView.setVisibility(View.INVISIBLE);
                                drawerForAdapter.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        deleteImageView.setVisibility(View.INVISIBLE);
                        drawerForAdapter.setVisibility(View.VISIBLE);
                    }
                    */
                    break;
            }
            return true;
        }

    }


}

