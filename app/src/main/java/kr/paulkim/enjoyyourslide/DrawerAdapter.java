package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 김새미루 on 2016-06-17.
 */
public class DrawerAdapter extends BaseAdapter {
    Context mContext;
    Pac[] pacsForAdapter;

    public DrawerAdapter(Context mContext, Pac[] pacsForAdapter) {
        this.mContext = mContext;
        this.pacsForAdapter = pacsForAdapter;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return pacsForAdapter.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = li.inflate(R.layout.lockscreen_main_simple, null);

            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.appNameTextView);
            viewHolder.text.setTextColor(Color.WHITE);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.appIconImageView);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        System.out.println("DSSSSSSSSSSSSSSSSSSSSSSS");
        viewHolder.text.setText(pacsForAdapter[pos].label);
        viewHolder.icon.setImageDrawable(pacsForAdapter[pos].icon);


        return convertView;
    }

    static class ViewHolder {
        TextView text;
        ImageView icon;
    }

}