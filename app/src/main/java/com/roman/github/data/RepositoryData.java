package com.roman.github.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by roman on 16. 6. 1.
 */
public class RepositoryData implements Parcelable {

    private String name;
    private String description;

    public RepositoryData(String name, String desciption) {
        this.name = name;
        this.description = desciption;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    protected RepositoryData(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<RepositoryData> CREATOR = new Parcelable.Creator<RepositoryData>() {
        @Override
        public RepositoryData createFromParcel(Parcel source) {
            return new RepositoryData(source);
        }

        @Override
        public RepositoryData[] newArray(int size) {
            return new RepositoryData[size];
        }
    };
}
