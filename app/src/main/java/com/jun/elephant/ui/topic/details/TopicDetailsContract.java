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
package com.jun.elephant.ui.topic.details;

import com.google.gson.JsonObject;
import com.jun.elephant.entity.topic.TopicDetailEntity;
import com.jun.elephant.mvpframe.BaseModel;
import com.jun.elephant.mvpframe.BasePresenter;
import com.jun.elephant.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by Jun on 2016/10/17.
 */
public interface TopicDetailsContract {
    interface Model extends BaseModel {

        Observable<TopicDetailEntity> getDetailsInfo(int topicId);

        Observable<JsonObject> voteUp(int topicId);

        Observable<JsonObject> voteDown(int topicId);
    }

    interface View extends BaseView {
        void getDetailsInfo(TopicDetailEntity topicDetailEntity);

        void optVoteUpSuccess(int voteCount);

        void optVoteDownSuccess(int voteCount);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getDetailsInfo(int topicId);

        public abstract void optStatusType(int type, int topicId);
    }
}
