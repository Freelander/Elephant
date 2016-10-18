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
import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.topic.TopicDetailEntity;
import com.jun.elephant.mvpframe.rx.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Jun on 2016/10/17.
 */
public class TopicDetailsModel implements TopicDetailsContract.Model {

    private Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("include", "user,node");
        options.put("columns", "root(excerpt),user(signature)");
        return options;
    }

    @Override
    public Observable<TopicDetailEntity> getDetailsInfo(int topicId) {
        return Networks.getInstance().getTopicApi()
                .getTopicDetail(topicId, getOptions())
                .compose(RxSchedulers.<TopicDetailEntity>io_main());
    }

    @Override
    public Observable<JsonObject> voteUp(int topicId) {
        return Networks.getInstance().getTopicApi()
                .voteUp(topicId)
                .compose(RxSchedulers.<JsonObject>io_main());
    }

    @Override
    public Observable<JsonObject> voteDown(int topicId) {
        return Networks.getInstance().getTopicApi()
                .voteDown(topicId)
                .compose(RxSchedulers.<JsonObject>io_main());
    }
}
