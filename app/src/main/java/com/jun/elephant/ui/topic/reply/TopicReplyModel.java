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

import com.jun.elephant.api.Networks;
import com.jun.elephant.entity.topic.TopicReplyEntity;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Jun on 2016/10/15.
 */
public class TopicReplyModel implements TopicReplyContract.Model {

    private Map<String, String> getOptions(int topicId, String body) {
        Map<String, String> options = new HashMap<String, String>();
        options.put("topic_id", String.valueOf(topicId));
        options.put("body", body);

        return options;
    }

    @Override
    public Observable<TopicReplyEntity> reply(int topicId, String body) {
            return Networks.getInstance().getTopicApi()
                    .publishReply(getOptions(topicId, body));
    }
}
