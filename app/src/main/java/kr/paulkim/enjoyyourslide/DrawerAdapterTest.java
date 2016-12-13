package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 김새미루 on 2016-06-29.
 */
public class DrawerAdapterTest extends BaseAdapter {
    Context context;
    ArrayList<String> shortcutList;
    PackageManager packageManager;
    //GridView homeView;
    //LinearLayout deleteLinearLayout;
    //ImageView deleteImageView, selectImageView;

    public DrawerAdapterTest(Context context, ArrayList<String> shortcutList, PackageManager packageManager) {
        this.context = context;
        this.shortcutList = shortcutList;
        this.packageManager = packageManager;
    }


    @Override
    public int getCount() {
        return shortcutList.size();
    }

    @Override
    public Object getItem(int position) {
        return shortcutList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = li.inflate(R.layout.lockscreen_shortcut_simple, null);

            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.shortcutImageView);
            viewHolder.label = (TextView) convertView.findViewById(R.id.shortcutTextView);
            viewHolder.label.setTextColor(Color.WHITE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //String str = ListToString(shortcutList);
        //String [] test = str.split("!");

        //String[] test;
        //test = (String[]) convertView.getTag();

        String str = shortcutList.get(position);
        final String[] test = str.split("!!!");
        byte[] decodeByte = Base64.decode(test[1], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);

        viewHolder.icon.setImageBitmap(bitmap);
        viewHolder.label.setText(test[2]);

        /*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = packageManager.getLaunchIntentForPackage(test[0]);
                context.startActivity(intent);
            }
        });
        */

        //convertView.setOnLongClickListener(new AppLongClickListener());
        //convertView.setOnDragListener(new MyDragListener());
        //deleteLinearLayout.setOnDragListener(new MyDragListener());

        return convertView;
    }

    static class ViewHolder {
        TextView label;
        ImageView icon;
    }

    /*
    public class AppLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(clipData, dragShadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            selectImageView.setVisibility(View.INVISIBLE);
            deleteImageView.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public class MyDragListener implements View.OnDragListener {

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
    }
    */
}

