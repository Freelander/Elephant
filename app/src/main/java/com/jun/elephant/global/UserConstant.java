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
package com.jun.elephant.global;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jun.elephant.entity.user.UserInfoEntity;

/**
 * Created by Jun on 2016/4/16.
 */
@SuppressLint("CommitPrefEdits")
public class UserConstant {

    private static final String TAG = "UserConstant";

    private static UserConstant mUserConstant;

    private SharedPreferences mPreferences;

    private SharedPreferences.Editor mEditor;

    private Gson mGson;

    public static UserConstant getInstance(Context context) {
        if (mUserConstant == null)
            mUserConstant = new UserConstant(context);

        return mUserConstant;
    }

    private UserConstant(Context context) {
        mPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        mGson = new Gson();
    }


    public boolean saveUserData(String value) {
        mEditor = mPreferences.edit();
        mEditor.putString(Constants.Key.USER_DATA, value);

        return mEditor.commit() && setIsLogin(true);
    }

    public UserInfoEntity getUserData() {
        String temp = mPreferences.getString(Constants.Key.USER_DATA, "");
        return mGson.fromJson(temp, UserInfoEntity.class);
    }

    public boolean logout() {
        mEditor = mPreferences.edit();
        mEditor.remove(Constants.Key.USER_DATA);
        mEditor.putBoolean(Constants.Key.IS_LOGIN, false);
        return mEditor.commit() && setIsLogin(false);
    }

    public boolean isLogin() {
        return mPreferences.getBoolean(Constants.Key.IS_LOGIN, false);
    }

    private boolean setIsLogin(boolean login) {
        mEditor = mPreferences.edit();
        mEditor.putBoolean(Constants.Key.IS_LOGIN, login);
        return mEditor.commit();
    }
}
