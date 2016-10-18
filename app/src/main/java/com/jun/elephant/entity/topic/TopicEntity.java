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
package com.jun.elephant.entity.topic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jun on 2016/5/3.
 */
public class TopicEntity implements Parcelable {

    private int id;
    private String title;
    @SerializedName("is_excellent")
    private boolean isExcellent;
    @SerializedName("reply_count")
    private int replyCount;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("vote_count")
    private int voteCount;

    private boolean favorite;

    private boolean attention;

    @SerializedName("vote_down")
    private boolean voteDown;

    @SerializedName("vote_up")
    private boolean voteUp;

    private LinksBean links;

    private UserBean user;

    @SerializedName("last_reply_user")
    private LastReplyUser lastReplyUser;

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LastReplyUser getLastReplyUser() {
        return lastReplyUser;
    }

    public void setLastReplyUser(LastReplyUser lastReplyUser) {
        this.lastReplyUser = lastReplyUser;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIsExcellent() {
        return isExcellent;
    }

    public void setIsExcellent(boolean isExcellent) {
        this.isExcellent = isExcellent;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public boolean isVoteDown() {
        return voteDown;
    }

    public void setVoteDown(boolean voteDown) {
        this.voteDown = voteDown;
    }

    public boolean isVoteUp() {
        return voteUp;
    }

    public void setVoteUp(boolean voteUp) {
        this.voteUp = voteUp;
    }

    public static class LinksBean implements Parcelable {
        @SerializedName("details_web_view")
        private String detailsWebView;
        @SerializedName("replies_web_view")
        private String repliesWebView;
        @SerializedName("web_url")
        private String webUrl;

        public String getDetailsWebView() {
            return detailsWebView;
        }

        public void setDetailsWebView(String detailsWebView) {
            this.detailsWebView = detailsWebView;
        }

        public String getRepliesWebView() {
            return repliesWebView;
        }

        public void setRepliesWebView(String repliesWebView) {
            this.repliesWebView = repliesWebView;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.detailsWebView);
            dest.writeString(this.repliesWebView);
            dest.writeString(this.webUrl);
        }

        public LinksBean() {
        }

        protected LinksBean(Parcel in) {
            this.detailsWebView = in.readString();
            this.repliesWebView = in.readString();
            this.webUrl = in.readString();
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

    public static class UserBean implements Parcelable {

        public DataEntity data;

        public DataEntity getData() {
            return data;
        }

        public void setData(DataEntity data) {
            this.data = data;
        }

        public static class DataEntity implements Parcelable {
            private String id;
            private String name;
            private String avatar;
            private String introduction;

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeString(this.avatar);
                dest.writeString(this.introduction);
            }

            public DataEntity() {
            }

            protected DataEntity(Parcel in) {
                this.id = in.readString();
                this.name = in.readString();
                this.avatar = in.readString();
                this.introduction = in.readString();
            }

            public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
                @Override
                public DataEntity createFromParcel(Parcel source) {
                    return new DataEntity(source);
                }

                @Override
                public DataEntity[] newArray(int size) {
                    return new DataEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.data, flags);
        }

        public UserBean() {
        }

        protected UserBean(Parcel in) {
            this.data = in.readParcelable(DataEntity.class.getClassLoader());
        }

        public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
            @Override
            public UserBean createFromParcel(Parcel source) {
                return new UserBean(source);
            }

            @Override
            public UserBean[] newArray(int size) {
                return new UserBean[size];
            }
        };
    }

    public static class LastReplyUser implements Parcelable {

        private DataEntity data;

        public DataEntity getData() {
            return data;
        }

        public void setData(DataEntity data) {
            this.data = data;
        }

        public static class DataEntity implements Parcelable {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.name);
            }

            public DataEntity() {
            }

            protected DataEntity(Parcel in) {
                this.id = in.readString();
                this.name = in.readString();
            }

            public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
                @Override
                public DataEntity createFromParcel(Parcel source) {
                    return new DataEntity(source);
                }

                @Override
                public DataEntity[] newArray(int size) {
                    return new DataEntity[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.data, flags);
        }

        public LastReplyUser() {
        }

        protected LastReplyUser(Parcel in) {
            this.data = in.readParcelable(DataEntity.class.getClassLoader());
        }

        public static final Parcelable.Creator<LastReplyUser> CREATOR = new Parcelable.Creator<LastReplyUser>() {
            @Override
            public LastReplyUser createFromParcel(Parcel source) {
                return new LastReplyUser(source);
            }

            @Override
            public LastReplyUser[] newArray(int size) {
                return new LastReplyUser[size];
            }
        };
    }

    public static class Category implements Parcelable {

        private CategoryEntity.Category data;

        public CategoryEntity.Category getData() {
            return data;
        }

        public void setData(CategoryEntity.Category data) {
            this.data = data;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.data, flags);
        }

        public Category() {
        }

        protected Category(Parcel in) {
            this.data = in.readParcelable(CategoryEntity.Category.class.getClassLoader());
        }

        public static final Creator<Category> CREATOR = new Creator<Category>() {
            @Override
            public Category createFromParcel(Parcel source) {
                return new Category(source);
            }

            @Override
            public Category[] newArray(int size) {
                return new Category[size];
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
        dest.writeString(this.title);
        dest.writeByte(isExcellent ? (byte) 1 : (byte) 0);
        dest.writeInt(this.replyCount);
        dest.writeString(this.updatedAt);
        dest.writeString(this.createdAt);
        dest.writeInt(this.voteCount);
        dest.writeParcelable(this.links, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.lastReplyUser, flags);
        dest.writeParcelable(this.category, flags);
    }

    public TopicEntity() {
    }

    protected TopicEntity(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.isExcellent = in.readByte() != 0;
        this.replyCount = in.readInt();
        this.updatedAt = in.readString();
        this.createdAt = in.readString();
        this.voteCount = in.readInt();
        this.links = in.readParcelable(LinksBean.class.getClassLoader());
        this.user = in.readParcelable(UserBean.class.getClassLoader());
        this.lastReplyUser = in.readParcelable(LastReplyUser.class.getClassLoader());
        this.category = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Parcelable.Creator<TopicEntity> CREATOR = new Parcelable.Creator<TopicEntity>() {
        @Override
        public TopicEntity createFromParcel(Parcel source) {
            return new TopicEntity(source);
        }

        @Override
        public TopicEntity[] newArray(int size) {
            return new TopicEntity[size];
        }
    };
}
