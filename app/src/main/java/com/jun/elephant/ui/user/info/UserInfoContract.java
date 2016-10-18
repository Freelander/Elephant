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

import com.jun.elephant.entity.user.UserEntity;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.mvpframe.BaseModel;
import com.jun.elephant.mvpframe.BasePresenter;
import com.jun.elephant.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by Jun on 2016/10/14.
 */
public interface UserInfoContract {

    interface Model extends BaseModel {
        Observable<UserInfoEntity> getUserInfoById(int userId);

        Observable<UserInfoEntity> saveUserInfoById(int userId, UserEntity userEntity);
    }

    interface View extends BaseView {
        void getUserInfo(UserInfoEntity userInfoEntity);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void getUserInfoById(int userId);

        public abstract void saveUserInfoById(int userId, UserEntity userEntity);
    }
}
