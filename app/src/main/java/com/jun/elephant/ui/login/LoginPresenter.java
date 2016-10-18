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

import com.jun.elephant.Elephant;
import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.TokenEntity;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.util.JLog;
import com.jun.elephant.util.SharePreferencesHelper;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jun on 2016/10/14.
 */
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void login(Context context, String userName, String loginToken) {
        mRxManager.add(mModel.getLoginToken(context, userName, loginToken)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onRequestStart();
                    }
                })
                .flatMap(new Func1<TokenEntity, Observable<UserInfoEntity>>() {
                    @Override
                    public Observable<UserInfoEntity> call(TokenEntity tokenEntity) {
                        JLog.logd("login_token: ", tokenEntity.getAccess_token());
                        Networks.setToken(tokenEntity.getAccess_token());
                        SharePreferencesHelper.getInstance(Elephant.applicationContext)
                                .putString(Constants.Key.TOKEN, tokenEntity.getAccess_token());
                        return mModel.login();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<UserInfoEntity>() {
                    @Override
                    public void call(UserInfoEntity userInfoEntity) {
                        mView.onLoginSuccess(userInfoEntity);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onInternetError();
                        mView.onRequestEnd();
                    }
                })

        );
    }
}
