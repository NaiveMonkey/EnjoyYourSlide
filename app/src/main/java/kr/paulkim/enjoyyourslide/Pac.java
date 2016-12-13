package kr.paulkim.enjoyyourslide;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by 김새미루 on 2016-06-29.
 */
public class Pac implements Serializable {
    Drawable icon;
    String packageName;
    String label;

/*
    public Pac(Parcel source) {
        this.icon = new BitmapDrawable((Bitmap)source.readValue(Bitmap.class.getClassLoader()));
        this.packageName = source.readString();
        this.label = source.readString();
    }
    */


    public Pac(Drawable icon, String packageName, String label) {
        this.icon = icon;
        this.packageName = packageName;
        this.label = label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
        dest.writeParcelable(bitmap, flags);
        dest.writeString(packageName);
        dest.writeString(label);
    }

    public void readFromParcel(Parcel in) {
        Bitmap bitmap = in.readParcelable(getClass().getClassLoader());
        icon = new BitmapDrawable(bitmap);
        packageName = in.readString();
        label = in.readString();
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Pac createFromParcel(Parcel in) {
            return new Pac(in);
        }

        @Override
        public Pac[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Pac[size];
        }

    };
    */
}

