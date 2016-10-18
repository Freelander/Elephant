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
package com.jun.elephant.util;

import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * 日志管理工具
 * Created by Jun on 2016/2/25.
 */
public class JLog {

    public static boolean DEBUG ;

    public static void initialize(boolean isDebug) {
        JLog.DEBUG = isDebug;
    }

    public static void v(String tag, String message) {
        if(DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if(DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if(DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if(DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if(DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Exception e) {
        if(DEBUG) {
            Log.e(tag, message, e);
        }
    }


    /**
     * 使用 Logger 工具
     */

    public static void logv(String tag, String message) {
        if (DEBUG) {
            Logger.init(tag);
            Logger.v(message);
        }
    }

    public static void logd(String tag, String message) {
        if(DEBUG) {
            Logger.init(tag);
            Logger.d(message);
        }
    }

    public static void logi(String tag, String message) {
        if(DEBUG) {
            Logger.init(tag);
            Logger.i(message);
        }
    }

    public static void logw(String tag, String message) {
        if(DEBUG) {
            Logger.init(tag);
            Logger.w(message);
        }
    }

    public static void loge(String tag, String message) {
        if(DEBUG) {
            Logger.init(tag);
            Logger.e(message);

            
        }
    }

    public static void loge(String tag, String message, Exception e) {
        if(DEBUG) {
            Logger.init(tag);
            Logger.e(message, e);
        }
    }

    public static void logJ(String tag, String message) {
        if (DEBUG) {
            Logger.init(tag);
            Logger.json(message);
        }
    }
}
