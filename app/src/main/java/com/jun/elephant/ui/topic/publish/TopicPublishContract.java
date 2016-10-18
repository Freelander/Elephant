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
package com.jun.elephant.ui.topic.publish;

import com.jun.elephant.entity.topic.CategoryEntity;
import com.jun.elephant.entity.topic.TopicDetailEntity;
import com.jun.elephant.entity.topic.TopicEntity;
import com.jun.elephant.mvpframe.BaseModel;
import com.jun.elephant.mvpframe.BasePresenter;
import com.jun.elephant.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by Jun on 2016/10/15.
 */
public interface TopicPublishContract {

    interface Model extends BaseModel {
        Observable<CategoryEntity> getCategories();

        Observable<TopicDetailEntity> publishTopic(String title, String body, String categoryId);
    }

    interface View extends BaseView {

        void getCategory(CategoryEntity categoryEntity);

        void publishTopicSuccess(TopicEntity topicEntity);

    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void getCategory();

        public abstract void publishTopic(String title, String body, String categoryId);

    }
}
