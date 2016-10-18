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

import com.jun.elephant.BuildConfig;
import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.TokenEntity;
import com.jun.elephant.entity.topic.TopicListEntity;
import com.jun.elephant.mvpframe.rx.RxSchedulers;
import com.jun.elephant.global.Constants;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Jun on 2016/10/14.
 */
public class TopicModel implements TopicContract.Model {

    @Override
    public Observable<TokenEntity> getTokenByForum() {
        return Networks.getInstance().getTokenApi().getToken(
                Constants.Token.AUTH_TYPE_GUEST,
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET)
                .compose(RxSchedulers.<TokenEntity>io_main());
    }

    @Override
    public Observable<TopicListEntity> getTopicByForum(String type, int pageIndex) {
        return Networks.getInstance().getTopicApi()
                .getTopics(getOptionsByForum(type, pageIndex))
                .compose(RxSchedulers.<TopicListEntity>io_main());
    }

    /**
     * 获取用户点赞话题列表
     * @param userId
     * @param pageIndex
     * @return
     */
    @Override
    public Observable<TopicListEntity> getUserTopicPraise(int userId, int pageIndex) {
        return Networks.getInstance().getUserApi()
                .getVotes(userId, getOptionsByUser(pageIndex))
                .compose(RxSchedulers.<TopicListEntity>io_main());
    }

    /**
     * 获取用户分享话题列表
     * @param userId
     * @param pageIndex
     * @return
     */
    @Override
    public Observable<TopicListEntity> getUserTopicShare(int userId, int pageIndex) {
        return Networks.getInstance().getUserApi()
                .getTopics(userId, getOptionsByUser(pageIndex))
                .compose(RxSchedulers.<TopicListEntity>io_main());
    }

    /**
     * 获取用户关注的人列表，备注：这个是预留接口，后台未提供相关接口
     * @param userId
     * @param pageIndex
     * @return
     */
    @Override
    public Observable<TopicListEntity> getUserFollowUser(int userId, int pageIndex) {
        return Networks.getInstance().getUserApi()
                .getAttentions(userId)
                .compose(RxSchedulers.<TopicListEntity>io_main());
    }


    /**
     * 获取用户话题列表相关配置
     * @param pageIndex
     * @return
     */
    private Map<String, String> getOptionsByUser(int pageIndex) {
        Map<String, String> options = new HashMap<>();
        options.put("include", "category,user,node,last_reply_user");
        options.put("per_page", String.valueOf(Constants.PER_PAGE));
        options.put("page", String.valueOf(pageIndex));

        return options;
    }

    /**
     * 论坛话题列表相关设置
     * @param filters
     * @param pageIndex
     * @return
     */
    private Map<String, String> getOptionsByForum(String filters, int pageIndex) {
        Map<String, String> options = new HashMap<String, String>();
        options.put("include", "category,user,node,last_reply_user");
        options.put("per_page", String.valueOf(Constants.PER_PAGE));
        options.put("filters", filters);
        options.put("page", String.valueOf(pageIndex));
        options.put("columns", "user(signature)");

        return options;
    }
}
