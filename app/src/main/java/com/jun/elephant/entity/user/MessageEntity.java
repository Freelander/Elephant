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
package com.jun.elephant.entity.user;

import com.google.gson.annotations.SerializedName;
import com.jun.elephant.entity.topic.TopicDetailEntity;

/**
 * Created by Jun on 2016/5/6.
 */
public class MessageEntity {
    private int id;

    private String type;

    private String body;

    @SerializedName("topic_id")
    private int topicId;

    @SerializedName("reply_id")
    private int replyId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("from_user_id")
    private int fromUserId;

    @SerializedName("type_msg")
    private String typeMsg;

    private String message;

    @SerializedName("from_user")
    private UserInfoEntity fromUserEntity;

    private TopicDetailEntity topic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getTypeMsg() {
        return typeMsg;
    }

    public void setTypeMsg(String typeMsg) {
        this.typeMsg = typeMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInfoEntity getFromUserEntity() {
        return fromUserEntity;
    }

    public void setFromUserEntity(UserInfoEntity fromUserEntity) {
        this.fromUserEntity = fromUserEntity;
    }

    public TopicDetailEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicDetailEntity topic) {
        this.topic = topic;
    }
}
