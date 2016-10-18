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
package com.jun.elephant.ui.user.message;

import com.jun.elephant.entity.user.UserMessageEntity;

import rx.Observer;

/**
 * Created by Jun on 2016/10/15.
 */
public class MessagePresenter extends MessageListContract.Presenter {

    private Observer<UserMessageEntity> getMessageObserver(final int pageIndex) {
        return new Observer<UserMessageEntity>() {
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
            public void onNext(UserMessageEntity userMessageEntity) {
                if (pageIndex == 1) {
                    mView.refreshMessageList(userMessageEntity);
                } else {
                    mView.loadMoreMessageList(userMessageEntity);
                }
            }
        };
    }


    @Override
    public void getMessageList(int pageIndex) {
        mRxManager.add(mModel.getUserMessage(pageIndex).subscribe(getMessageObserver(pageIndex)));
    }
}
