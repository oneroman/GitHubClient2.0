package com.samsung.dagger2sample.GitHubAPI.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by roman on 16. 5. 27.
 */
public class Repository implements Parcelable {

    public int id;
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public Repository() {
    }

    protected Repository(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Repository> CREATOR = new Parcelable.Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel source) {
            return new Repository(source);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("name: ");
        builder.append(this.name);
        builder.append("\n");

        builder.append("id: ");
        builder.append(this.id);
        builder.append("\n");
        return builder.toString();
    }
}
