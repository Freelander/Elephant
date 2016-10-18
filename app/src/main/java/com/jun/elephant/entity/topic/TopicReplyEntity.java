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
package com.jun.elephant.entity.topic;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jun on 2016/5/5.
 */
public class TopicReplyEntity {

    private ReplyEntity data;

    public ReplyEntity getData() {
        return data;
    }

    public void setData(ReplyEntity data) {
        this.data = data;
    }

    public static class ReplyEntity {
        @SerializedName("topic_id")
        int topicId;

        String body;

        @SerializedName("user_id")
        int userId;

        @SerializedName("body_original")
        String bodyOriginal;

        @SerializedName("updated_at")
        String updatedAt;

        @SerializedName("created_at")
        String createdAt;

        int id;

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getBodyOriginal() {
            return bodyOriginal;
        }

        public void setBodyOriginal(String bodyOriginal) {
            this.bodyOriginal = bodyOriginal;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
