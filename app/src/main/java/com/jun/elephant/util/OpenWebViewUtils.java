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
import android.content.Intent;

import com.jun.elephant.R;
import com.jun.elephant.global.Constants;
import com.jun.elephant.ui.main.WebViewActivity;

/**
 * Created by Jun on 2016/9/29.
 */

public class OpenWebViewUtils {

    /**
     * 跳转到关于 PHPHub WebView 界面
     * @param context 上下文
     */
    public static void aboutPHPHub(Context context) {
        String title = context.getString(R.string.about_phphub);
        String url = Constants.ABOUT_PHPHUB;
        Intent intent = WebViewActivity.newIntent(context, title, url);
        context.startActivity(intent);
    }

    /**
     * 跳转到关于我 WebView 界面
     * @param context 上下文
     */
    public static void aboutMe(Context context) {
        String title = context.getString(R.string.about_me);
        String url = Constants.ABOUT_ME;
        Intent intent = WebViewActivity.newIntent(context, title, url);
        context.startActivity(intent);
    }

    /**
     * 跳转到获取源代码 WebView 界面
     * @param context 上下文
     */
    public static void getOpenSource(Context context) {
        String title = context.getString(R.string.open_source);
        String url = Constants.OPEN_SOURCE;
        Intent intent = WebViewActivity.newIntent(context, title, url);
        context.startActivity(intent);
    }

    /**
     * 跳转到登录帮助 WebView 界面
     * @param context 上下文
     */
    public static void loginHelp(Context context) {
        String title = context.getString(R.string.menu_login_help);
        String url = Constants.LOGIN_HELP;
        Intent intent = WebViewActivity.newIntent(context, title, url);
        context.startActivity(intent);
    }

    /**
     * 跳转到我回复 WebView 界面
     * @param context 上下文
     * @param url 我回复的链接地址
     */
    public static void myReply(Context context, String url) {
        String title = context.getString(R.string.menu_revert);
        Intent intent = WebViewActivity.newIntent(context, title, url);
        context.startActivity(intent);
    }

    /**
     * 跳转到个人博客
     * @param context 上下文
     * @param title 博客名
     * @param url 博客地址
     */
    public static void blog(Context context, String title, String url) {
        Intent intent = WebViewActivity.newIntent(context, title, url);
        context.startActivity(intent);
    }
}
