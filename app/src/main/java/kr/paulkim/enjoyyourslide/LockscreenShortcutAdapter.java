package kr.paulkim.enjoyyourslide;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by 김새미루 on 2016-06-09.
 */
public class LockScreenShortcutAdapter extends BaseAdapter {
    Context context;
    List<ResolveInfo> appGroup;
    PackageManager packageManager;
    //LockScreenShortcutActivity.Pac[] pacsForAdapter;
    private SparseBooleanArray mSelectedItemsIds;
    /*
    public LockScreenShortcutAdapter(Context context, LockScreenShortcutActivity.Pac pacs[]) {
        this.context = context;
        pacsForAdapter = pacs;
        mSelectedItemsIds = new SparseBooleanArray();

        //this.appGroup = appGroup;
        //this.packageManager = packageManager;
    }
    */

    public LockScreenShortcutAdapter(Context context, List<ResolveInfo> appGroup, PackageManager packageManager) {
        this.context = context;
        this.appGroup = appGroup;
        this.packageManager = packageManager;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        //return pacsForAdapter.length;
        return appGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return appGroup.get(position);
        //return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
        //return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Holder holder;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lockscreen_shortcut_simple, null, true);
            holder = new Holder();
            holder.appIcon = (ImageView) convertView.findViewById(R.id.shortcutImageView);
            /*
            holder.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ViewGroup.LayoutParams lp = holder.icon.getLayoutParams();
            lp.width = 100;
            lp.height = 100;
            holder.icon.requestLayout();
            */
            holder.appName = (TextView) convertView.findViewById(R.id.shortcutTextView);
            //holder.appCheck = (CheckBox) convertView.findViewById(R.id.shortcutCheckBox);

            //holder.appName.setTextSize(8);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //holder.appIcon.setId(position);
        //holder.appName.setId(position);
        //holder.appCheck.setId(position);


        //holder.appIcon.setImageDrawable(pacsForAdapter[position].icon);
        //holder.appName.setText(pacsForAdapter[position].label);

        ResolveInfo info = appGroup.get(position);
        holder.appIcon.setImageDrawable(info.activityInfo.loadIcon(packageManager));
        holder.appName.setText(info.activityInfo.loadLabel(packageManager));

        return convertView;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    static class Holder {
        public TextView appName;
        public ImageView appIcon;
        //public CheckBox appCheck;
    }


}
