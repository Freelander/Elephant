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

import android.content.Context;

import com.jun.elephant.global.Constants;

/**
 * 主题切换帮助类
 * Created by Jun on 2016/9/21.
 */

public class ThemeUtil {
    private SharePreferencesHelper mSPHelper;

    private static ThemeUtil mThemeUtil;

    public static ThemeUtil getInstance(Context context) {
        if (mThemeUtil == null) {
            mThemeUtil = new ThemeUtil(context);
        }

        return mThemeUtil;
    }

    public ThemeUtil(Context context) {
        super();
        mSPHelper = SharePreferencesHelper.getInstance(context);
    }

    /**
     * 保存主题设置
     * @param theme 主题
     */
    public void setTheme(String theme) {
        mSPHelper.putString(Constants.Key.THEME_MODE, theme);
    }

    /**
     * 取出当前主题
     * @return 主题
     */
    public String getTheme() {
        return mSPHelper.getString(Constants.Key.THEME_MODE, Constants.Theme.Blue);
    }

    /**
     * 判断是否是蓝色主题
     * @return
     */
    public boolean isBlueTheme() {
        return getTheme().equals(Constants.Theme.Blue);
    }

    /**
     * 判断是否是白色主题
     * @return
     */
    public boolean isWhiteTheme() {
        return getTheme().equals(Constants.Theme.White);
    }
}
