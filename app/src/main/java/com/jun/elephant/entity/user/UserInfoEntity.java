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
 * Created by Jun on 2016/6/15.
 */
public class UserInfoEntity implements Parcelable {

    /**
     * id : 1
     * name : Summer
     * avatar : http://phphub.org/uploads/avatars/1_1443413935.jpeg
     * topic_count : 88
     * reply_count : 645
     * notification_count : 0
     * is_banned : false
     * twitter_account : Summe
     * company :
     * city : 南京
     * email : summer.alex07@gmail.com
     * signature : Little knowledge is dangerous
     * introduction : A little knowledge is a dangerous thing
     * github_name : summerblue
     * github_url : https://github.com/summerblue
     * real_name : I love it
     * personal_website : summerblue.me
     * created_at : 2014-08-18 01:00:03
     * updated_at : 2016-04-15 12:53:08
     * links : {"replies_web_view":"https://staging_api.phphub.org/v1/users/1/replies/web_view"}
     */

    private UserEntity data;

    public UserEntity getData() {
        return data;
    }

    public void setData(UserEntity data) {
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

    public UserInfoEntity() {
    }

    protected UserInfoEntity(Parcel in) {
        this.data = in.readParcelable(UserEntity.class.getClassLoader());
    }

    public static final Creator<UserInfoEntity> CREATOR = new Creator<UserInfoEntity>() {
        @Override
        public UserInfoEntity createFromParcel(Parcel source) {
            return new UserInfoEntity(source);
        }

        @Override
        public UserInfoEntity[] newArray(int size) {
            return new UserInfoEntity[size];
        }
    };
}
