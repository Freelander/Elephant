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

import com.jun.elephant.entity.topic.TopicListEntity;
import com.jun.elephant.entity.user.UserEntity;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.entity.user.UserMessageEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Jun on 2016/4/16.
 */
public interface UserApi {

    @GET("me")
    Observable<UserInfoEntity> getUserInfo();

    @GET("users/{userId}")
    Observable<UserInfoEntity> getUserInfoById(@Path("userId") int userId);

    @GET("users/{userId}/following")
    Observable<TopicListEntity> getAttentions(@Path("userId") int userId);

    @GET("user/{userId}/votes")
    Observable<TopicListEntity> getVotes(@Path("userId") int userId,
                                         @QueryMap Map<String, String> options);

    @GET("user/{userId}/topics")
    Observable<TopicListEntity> getTopics(@Path("userId") int userId,
                                      @QueryMap Map<String, String> options);

    @GET("me/notifications")
    Observable<UserMessageEntity> getMyMessage(@QueryMap Map<String, String> options);

    @PUT("users/{userId}")
    Observable<UserInfoEntity> saveUserInfo(@Path("userId") int userId,
                                                 @Body UserEntity userInfo);

}
