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

import android.text.TextUtils;

import com.jun.elephant.Elephant;
import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.TokenEntity;
import com.jun.elephant.entity.topic.TopicListEntity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.util.JLog;
import com.jun.elephant.util.SharePreferencesHelper;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jun on 2016/10/15.
 */
public class TopicPresenter extends TopicContract.Presenter {

    public Observer<TopicListEntity> getTopicListObserver(final int pageIndex) {
        return new Observer<TopicListEntity>() {
            @Override
            public void onCompleted() {
                mView.onRequestEnd();
            }

            @Override
            public void onError(Throwable e) {
                mView.onRequestError(e.toString());
                mView.onInternetError();
            }

            @Override
            public void onNext(TopicListEntity topicListEntity) {
                if (pageIndex == 1) {
                    mView.refreshTopicList(topicListEntity);
                } else {
                    mView.loadMoreTopicList(topicListEntity);
                }
            }
        };
    }

    @Override
    void getTopicListByUser(int type, int userId, int pageIndex) {
        switch (type) {
            case Constants.User.USER_TOPIC_VOTES:
                mRxManager.add(mModel.getUserTopicPraise(userId, pageIndex)
                        .subscribe(getTopicListObserver(pageIndex)));
                break;
            case Constants.User.USER_TOPIC_FOLLOW:
                mRxManager.add(mModel.getUserFollowUser(userId, pageIndex)
                        .subscribe(getTopicListObserver(pageIndex)));
                break;
            case Constants.User.USER_TOPIC_MY:
                mRxManager.add(mModel.getUserTopicShare(userId, pageIndex)
                        .subscribe(getTopicListObserver(pageIndex)));
                break;
        }
    }

    @Override
    void getTopicListByForum(final String type, final int pageIndex) {
        mRxManager.add(Observable.just(null)
                .flatMap(new Func1<Object, Observable<TopicListEntity>>() {
                    @Override
                    public Observable<TopicListEntity> call(Object o) {
                        return TextUtils.isEmpty(Networks.getToken())
                                ? Observable.<TopicListEntity>error(new NullPointerException("Token is null"))
                                : mModel.getTopicByForum(type, pageIndex);
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if (throwable instanceof NullPointerException ||
                                        throwable instanceof IllegalArgumentException) {
                                    return mModel.getTokenByForum()
                                            .doOnNext(new Action1<TokenEntity>() {
                                                @Override
                                                public void call(TokenEntity tokenEntity) {
                                                    Networks.setToken(tokenEntity.getAccess_token());
                                                    JLog.logd("Token ==== ", tokenEntity.getAccess_token());
                                                    SharePreferencesHelper.getInstance(Elephant.applicationContext)
                                                            .putString(Constants.Key.TOKEN, tokenEntity.getAccess_token());
                                                }

                                            });
                                }

                                return Observable.just(throwable);

                            }
                        });

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getTopicListObserver(pageIndex)));
    }
}
