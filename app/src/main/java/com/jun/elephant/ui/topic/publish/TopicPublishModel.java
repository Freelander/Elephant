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

import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.topic.CategoryEntity;
import com.jun.elephant.entity.topic.TopicDetailEntity;
import com.jun.elephant.mvpframe.rx.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Jun on 2016/10/15.
 */
public class TopicPublishModel implements TopicPublishContract.Model {

    @Override
    public Observable<CategoryEntity> getCategories() {
        return Networks.getInstance().getTopicApi()
                .getCategories()
                .compose(RxSchedulers.<CategoryEntity>io_main());
    }

    @Override
    public Observable<TopicDetailEntity> publishTopic(String title, String body, String categoryId) {
        Map<String, String> options = new HashMap<>();
        options.put("title", title);
        options.put("body", body);
        options.put("node_id", categoryId);

        return Networks.getInstance().getTopicApi()
                .publishTopic(options)
                .compose(RxSchedulers.<TopicDetailEntity>io_main());
    }
}
