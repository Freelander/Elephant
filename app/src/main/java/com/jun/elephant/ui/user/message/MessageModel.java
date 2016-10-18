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

import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.user.UserMessageEntity;
import com.jun.elephant.mvpframe.rx.RxSchedulers;
import com.jun.elephant.global.Constants;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Jun on 2016/10/15.
 */
public class MessageModel implements MessageListContract.Model {

    @Override
    public Observable<UserMessageEntity> getUserMessage(int pageIndex) {
        return Networks.getInstance().getUserApi()
                .getMyMessage(getOptions(pageIndex))
                .compose(RxSchedulers.<UserMessageEntity>io_main());
    }


    private Map<String, String> getOptions(int pageIndex) {
        Map<String, String> options = new HashMap<>();
        options.put("per_page", String.valueOf(Constants.PER_PAGE));
        options.put("include", "from_user,topic");
        options.put("page", String.valueOf(pageIndex));

        return options;
    }
}
