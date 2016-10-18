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

import java.util.List;

/**
 * Created by Jun on 2016/3/27.
 */
public class TopicListEntity {

    /**
     * id : 1300
     * title : 督促唱几句
     * is_excellent : false
     * reply_count : 0
     * updated_at : 2016-03-24 18:29:29
     * created_at : 2016-03-24 18:29:21
     * vote_count : 1
     * links : {"details_web_view":"https://staging_api.phphub.org/v1/topics/1300/web_view","replies_web_view":"https://staging_api.phphub.org/v1/topics/1300/replies/web_view","web_url":"http://phphub.org/topics/1300"}
     */

    private List<TopicEntity> data;

    public List<TopicEntity> getData() {
        return data;
    }

    public void setData(List<TopicEntity> data) {
        this.data = data;
    }

}
