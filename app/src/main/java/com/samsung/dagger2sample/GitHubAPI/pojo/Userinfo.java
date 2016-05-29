package com.samsung.dagger2sample.GitHubAPI.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anna on 29.05.2016.
 */
public class Userinfo implements Parcelable {

    public String login;
    public int id;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public boolean site_admin;
    public String name;
    public String company;
    public String blog;
    public String location;
    public String email;
    public String hireable;
    public String bio;
    public int public_repos;
    public int public_gists;
    public int followers;
    public int following;
    public String created_at;
    public String updated_at;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeInt(this.id);
        dest.writeString(this.avatar_url);
        dest.writeString(this.gravatar_id);
        dest.writeString(this.url);
        dest.writeString(this.html_url);
        dest.writeString(this.followers_url);
        dest.writeString(this.following_url);
        dest.writeString(this.gists_url);
        dest.writeString(this.starred_url);
        dest.writeString(this.subscriptions_url);
        dest.writeString(this.organizations_url);
        dest.writeString(this.repos_url);
        dest.writeString(this.events_url);
        dest.writeString(this.received_events_url);
        dest.writeString(this.type);
        dest.writeByte(this.site_admin ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeString(this.company);
        dest.writeString(this.blog);
        dest.writeString(this.location);
        dest.writeString(this.email);
        dest.writeString(this.hireable);
        dest.writeString(this.bio);
        dest.writeInt(this.public_repos);
        dest.writeInt(this.public_gists);
        dest.writeInt(this.followers);
        dest.writeInt(this.following);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
    }

    public Userinfo() {
    }

    protected Userinfo(Parcel in) {
        this.login = in.readString();
        this.id = in.readInt();
        this.avatar_url = in.readString();
        this.gravatar_id = in.readString();
        this.url = in.readString();
        this.html_url = in.readString();
        this.followers_url = in.readString();
        this.following_url = in.readString();
        this.gists_url = in.readString();
        this.starred_url = in.readString();
        this.subscriptions_url = in.readString();
        this.organizations_url = in.readString();
        this.repos_url = in.readString();
        this.events_url = in.readString();
        this.received_events_url = in.readString();
        this.type = in.readString();
        this.site_admin = in.readByte() != 0;
        this.name = in.readString();
        this.company = in.readString();
        this.blog = in.readString();
        this.location = in.readString();
        this.email = in.readString();
        this.hireable = in.readString();
        this.bio = in.readString();
        this.public_repos = in.readInt();
        this.public_gists = in.readInt();
        this.followers = in.readInt();
        this.following = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
    }

    public static final Parcelable.Creator<Userinfo> CREATOR = new Parcelable.Creator<Userinfo>() {
        @Override
        public Userinfo createFromParcel(Parcel source) {
            return new Userinfo(source);
        }

        @Override
        public Userinfo[] newArray(int size) {
            return new Userinfo[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("login: ");
        builder.append(this.login);
        builder.append("\n");
        builder.append("id: ");
        builder.append(this.id);
        builder.append("\n");
        return builder.toString();
    }
}
