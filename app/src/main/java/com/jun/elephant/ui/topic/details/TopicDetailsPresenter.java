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
import com.jun.elephant.global.Constants;

import rx.Observer;

/**
 * Created by Jun on 2016/10/17.
 */
public class TopicDetailsPresenter extends TopicDetailsContract.Presenter {

    private Observer<TopicDetailEntity> mTopicDetailObserver = new Observer<TopicDetailEntity>() {

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
        public void onNext(TopicDetailEntity topicDetailEntity) {
            mView.getDetailsInfo(topicDetailEntity);
        }
    };

    private Observer<JsonObject> mVoteUpObserver = new Observer<JsonObject>() {

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
        public void onNext(JsonObject jsonObject) {
            boolean status = jsonObject.get("vote-up").getAsBoolean();
            int voteCount = jsonObject.get("vote_count").getAsInt();
            if (status) mView.optVoteUpSuccess(voteCount);
        }
    };

    private Observer<JsonObject> mVoteDownObserver = new Observer<JsonObject>() {

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
        public void onNext(JsonObject jsonObject) {
            boolean status = jsonObject.get("vote-down").getAsBoolean();
            int voteCount = jsonObject.get("vote_count").getAsInt();
            if (status) mView.optVoteDownSuccess(voteCount);
        }
    };

    @Override
    public void getDetailsInfo(int topicId) {
        mRxManager.add(mModel.getDetailsInfo(topicId).subscribe(mTopicDetailObserver));
    }

    @Override
    public void optStatusType(int type, int topicId) {
        switch (type) {
            case Constants.TopicOpt.TOPIC_VOTE_DOWN:
                mRxManager.add(mModel.voteDown(topicId).subscribe(mVoteDownObserver));
                break;

            case Constants.TopicOpt.TOPIC_VOTE_UP:
                mRxManager.add(mModel.voteUp(topicId).subscribe(mVoteUpObserver));
                break;
        }
    }
}
