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
package com.jun.elephant.ui.login;

import android.content.Context;

import com.jun.elephant.BuildConfig;
import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.TokenEntity;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.global.Constants;

import rx.Observable;

/**
 * Created by Jun on 2016/10/14.
 */
public class LoginModel implements LoginContract.Model {

    /**
     * 获取登录状态 Token，扫描后得到的 userName， loginToken
     * @param context
     * @param userName
     * @param loginToken
     * @return
     */
    @Override
    public Observable<TokenEntity> getLoginToken(Context context, String userName, String loginToken) {
        return Networks.getInstance().getTokenApi().getToken(
                Constants.Token.AUTH_TYPE_USER,
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                userName, loginToken);
    }

    /**
     * 登录
     * @return
     */
    @Override
    public Observable<UserInfoEntity> login() {
        return Networks.getInstance().getUserApi().getUserInfo();
    }

}
