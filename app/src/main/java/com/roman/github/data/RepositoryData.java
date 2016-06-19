package com.roman.github.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by roman on 16. 6. 1.
 */
public class RepositoryData implements Parcelable {

    private String name;
    private String description;
    private String homePage;
    private int size;
    private String language;
    private int openIssues;
    private int forksCount;

    public RepositoryData(String name, String desciption, String hPage, int s, String lang, int oIssues, int fCount) {
        this.name = name;
        this.description = desciption;
        this.homePage = hPage;
        this.size = s;
        this.language = lang;
        this.openIssues = oIssues;
        this.forksCount = fCount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHomePage() {
        return homePage;
    }

    public int getSize() {
        return size;
    }

    public String getLanguage() {
        return language;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public int getForksCount() {
        return forksCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.homePage);
        dest.writeInt(this.size);
        dest.writeString(this.language);
        dest.writeInt(this.openIssues);
        dest.writeInt(this.forksCount);
    }

    protected RepositoryData(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.homePage = in.readString();
        this.size = in.readInt();
        this.language = in.readString();
        this.openIssues = in.readInt();
        this.forksCount = in.readInt();
    }

    public static final Creator<RepositoryData> CREATOR = new Creator<RepositoryData>() {
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
