/*
 * Copyright 2016 Freelander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jun.elephant.ui.user.info;

import com.jun.elephant.entity.user.UserEntity;
import com.jun.elephant.entity.user.UserInfoEntity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by Jun on 2016/10/14.
 */
public class UserInfoPresenter extends UserInfoContract.Presenter {

    public Observer<UserInfoEntity> getUserInfoObserver() {
        return new Observer<UserInfoEntity>() {
            @Override
            public void onCompleted() {
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.onRequestError(e.getMessage());
            }

            @Override
            public void onNext(UserInfoEntity userInfoEntity) {
                mView.getUserInfo(userInfoEntity);
            }
        };
    }

    @Override
    public void getUserInfoById(int userId) {
        mRxManager.add(mModel.getUserInfoById(userId).subscribe(getUserInfoObserver()));
    }

    @Override
    public void saveUserInfoById(int userId, UserEntity userEntity) {
        mRxManager.add(mModel.saveUserInfoById(userId, userEntity)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserInfoObserver())

        );
    }
}
