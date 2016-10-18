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

import java.util.List;

/**
 * Created by Jun on 2016/5/6.
 */
public class UserMessageEntity {
    private List<MessageEntity> data;

    public List<MessageEntity> getData() {
        return data;
    }

    public void setData(List<MessageEntity> data) {
        this.data = data;
    }
}
