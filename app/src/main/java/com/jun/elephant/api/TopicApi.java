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
package com.jun.elephant.api;

import com.google.gson.JsonObject;
import com.jun.elephant.entity.topic.CategoryEntity;
import com.jun.elephant.entity.topic.TopicDetailEntity;
import com.jun.elephant.entity.topic.TopicListEntity;
import com.jun.elephant.entity.topic.TopicReplyEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Jun on 2016/4/9.
 */
public interface TopicApi {

    @GET("topics/{topicId}")
    Observable<TopicDetailEntity> getTopicDetail(@Path("topicId") int topicId,
                                           @QueryMap Map<String, String> options);

    @GET("topics")
    Observable<TopicListEntity> getTopics(@QueryMap Map<String, String> options);

    @POST("topics/{topicId}/vote-up")
    Observable<JsonObject> voteUp(@Path("topicId") int topicId);

    @POST("topics/{topicId}/vote-down")
    Observable<JsonObject> voteDown(@Path("topicId") int topicId);

    @POST("topics")
    Observable<TopicDetailEntity> publishTopic(@QueryMap Map<String, String> options);

    @GET("categories")
    Observable<CategoryEntity> getCategories();

    @POST("replies")
    Observable<TopicReplyEntity> publishReply(@QueryMap Map<String, String> options);
}
