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
package com.jun.elephant;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.jun.elephant.util.JLog;

import java.io.File;

/**
 * Created by Jun on 2016/4/10.
 */
public class Elephant extends Application {

    public static Context applicationContext;

    public static String APP_CACHE_PATH = "Elephant/cache";

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        /**
         * 设置 Fresco 图片缓存的路径
         */
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
                .setBaseDirectoryPath(getOwnCacheDirectory(this, APP_CACHE_PATH))
                .build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setMainDiskCacheConfig(diskCacheConfig)
                .setSmallImageDiskCacheConfig(diskCacheConfig)
                .build();

        //初始化 Fresco 图片缓存库
        Fresco.initialize(this, config);
        //初始化日志输出工具
        JLog.initialize(BuildConfig.LOG_DEBUG);
    }

    public static File getOwnCacheDirectory(Context context, String cachePath) {
        File appCacheDir = null;

        if ("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cachePath);
        }

        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }

        return appCacheDir;
    }

    public static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }

}
