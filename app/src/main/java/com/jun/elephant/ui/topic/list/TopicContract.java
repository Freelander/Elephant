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
package com.jun.elephant.ui.topic.list;

import com.jun.elephant.entity.TokenEntity;
import com.jun.elephant.entity.topic.TopicListEntity;
import com.jun.elephant.mvpframe.BaseModel;
import com.jun.elephant.mvpframe.BasePresenter;
import com.jun.elephant.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by Jun on 2016/10/14.
 */

public interface TopicContract {

    interface Model extends BaseModel {

        Observable<TokenEntity> getTokenByForum();

        Observable<TopicListEntity> getTopicByForum(String type, int pageIndex);

        Observable<TopicListEntity> getUserTopicPraise(int userId, int pageIndex);

        Observable<TopicListEntity> getUserTopicShare(int userId, int pageIndex);

        Observable<TopicListEntity> getUserFollowUser(int userId, int pageIndex);
    }

    interface View extends BaseView {
        void refreshTopicList(TopicListEntity topicListEntity);

        void loadMoreTopicList(TopicListEntity topicListEntity);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getTopicListByUser(int type, int userId, int pageIndex);

        abstract void getTopicListByForum(String type, int pageIndex);

    }
}
