package com.roman.github.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by roman on 16. 6. 1.
 */
public class UserInfoData implements Parcelable {

    private String login;
    private String name;
    private String avatar_url;

    public UserInfoData(String login, String name, String avatar_url) {
        this.login = login;
        this.name = name;
        this.avatar_url = avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.name);
        dest.writeString(this.avatar_url);
    }

    protected UserInfoData(Parcel in) {
        this.login = in.readString();
        this.name = in.readString();
        this.avatar_url = in.readString();
    }

    public static final Creator<UserInfoData> CREATOR = new Creator<UserInfoData>() {
        @Override
        public UserInfoData createFromParcel(Parcel source) {
            return new UserInfoData(source);
        }

        @Override
        public UserInfoData[] newArray(int size) {
            return new UserInfoData[size];
        }
    };
}
