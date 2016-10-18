/*
 * Copyright 2016 Freelander
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jun.elephant.entity.user;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jun on 2016/5/6.
 */
public class UserEntity implements Parcelable {
    private int id;
    private String name;
    private String avatar;
    private int topic_count;
    private int reply_count;
    private int notification_count;
    private boolean is_banned;
    private String twitter_account;
    private String company;
    private String city;
    private String email;
    private String signature;
    private String introduction;
    private String github_name;
    private String github_url;
    private String real_name;
    private String personal_website;
    private String created_at;
    private String updated_at;
    /**
     * replies_web_view : https://staging_api.phphub.org/v1/users/1/replies/web_view
     */

    private LinksBean links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getTopic_count() {
        return topic_count;
    }

    public void setTopic_count(int topic_count) {
        this.topic_count = topic_count;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getNotification_count() {
        return notification_count;
    }

    public void setNotification_count(int notification_count) {
        this.notification_count = notification_count;
    }

    public boolean isIs_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public String getTwitter_account() {
        return twitter_account;
    }

    public void setTwitter_account(String twitter_account) {
        this.twitter_account = twitter_account;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGithub_name() {
        return github_name;
    }

    public void setGithub_name(String github_name) {
        this.github_name = github_name;
    }

    public String getGithub_url() {
        return github_url;
    }

    public void setGithub_url(String github_url) {
        this.github_url = github_url;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPersonal_website() {
        return personal_website;
    }

    public void setPersonal_website(String personal_website) {
        this.personal_website = personal_website;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public static class LinksBean implements Parcelable {
        private String replies_web_view;

        public String getReplies_web_view() {
            return replies_web_view;
        }

        public void setReplies_web_view(String replies_web_view) {
            this.replies_web_view = replies_web_view;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.replies_web_view);
        }

        public LinksBean() {
        }

        protected LinksBean(Parcel in) {
            this.replies_web_view = in.readString();
        }

        public static final Parcelable.Creator<LinksBean> CREATOR = new Parcelable.Creator<LinksBean>() {
            @Override
            public LinksBean createFromParcel(Parcel source) {
                return new LinksBean(source);
            }

            @Override
            public LinksBean[] newArray(int size) {
                return new LinksBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeInt(this.topic_count);
        dest.writeInt(this.reply_count);
        dest.writeInt(this.notification_count);
        dest.writeByte(is_banned ? (byte) 1 : (byte) 0);
        dest.writeString(this.twitter_account);
        dest.writeString(this.company);
        dest.writeString(this.city);
        dest.writeString(this.email);
        dest.writeString(this.signature);
        dest.writeString(this.introduction);
        dest.writeString(this.github_name);
        dest.writeString(this.github_url);
        dest.writeString(this.real_name);
        dest.writeString(this.personal_website);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeParcelable(this.links, flags);
    }

    public UserEntity() {
    }

    protected UserEntity(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.avatar = in.readString();
        this.topic_count = in.readInt();
        this.reply_count = in.readInt();
        this.notification_count = in.readInt();
        this.is_banned = in.readByte() != 0;
        this.twitter_account = in.readString();
        this.company = in.readString();
        this.city = in.readString();
        this.email = in.readString();
        this.signature = in.readString();
        this.introduction = in.readString();
        this.github_name = in.readString();
        this.github_url = in.readString();
        this.real_name = in.readString();
        this.personal_website = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.links = in.readParcelable(LinksBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserEntity> CREATOR = new Parcelable.Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
