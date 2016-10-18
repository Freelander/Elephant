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
package com.jun.elephant.ui.user.info;

import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.user.UserEntity;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.mvpframe.rx.RxSchedulers;

import rx.Observable;

/**
 * Created by Jun on 2016/10/14.
 */
public class UserInfoModel implements UserInfoContract.Model {
    /**
     * 根据用户 Id 获取用户信息
     * @param userId
     * @return
     */
    @Override
    public Observable<UserInfoEntity> getUserInfoById(int userId) {
        return Networks.getInstance().getUserApi().getUserInfoById(userId)
                .compose(RxSchedulers.<UserInfoEntity>io_main());
    }

    /**
     * 保存修改过后的用户信息
     * @param userId
     * @param userEntity
     * @return
     */
    @Override
    public Observable<UserInfoEntity> saveUserInfoById(int userId, UserEntity userEntity) {
        return Networks.getInstance().getUserApi().saveUserInfo(userId, userEntity)
                .compose(RxSchedulers.<UserInfoEntity>io_main());
    }
}
