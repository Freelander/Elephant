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
package com.jun.elephant.ui.topic.reply;

import com.jun.elephant.entity.topic.TopicReplyEntity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by Jun on 2016/10/15.
 */
public class TopicReplyPresenter extends TopicReplyContract.Presenter {

    private Observer<TopicReplyEntity> mReplyObserver = new Observer<TopicReplyEntity>() {

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
        public void onNext(TopicReplyEntity topicReplyEntity) {
            mView.replySuccess(topicReplyEntity);
        }
    };

    @Override
    public void reply(int topicId, String body) {
        mRxManager.add(mModel.reply(topicId, body)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.onRequestStart();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(mReplyObserver));
    }
}
