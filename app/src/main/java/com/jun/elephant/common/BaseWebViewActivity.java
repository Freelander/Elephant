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
package com.jun.elephant.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.polok.localify.LocalifyClient;
import com.github.polok.localify.module.LocalifyModule;
import com.jun.elephant.api.Networks;
import com.jun.elephant.global.Constants;
import com.jun.elephant.ui.main.PhotoShowActivity;
import com.jun.elephant.ui.widget.MultiStateView;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public abstract class BaseWebViewActivity extends BaseActivity {

    protected final static String PLATFORM = "Android";

    protected static class WebAppClient extends WebViewClient {
        private Context context;

        private MultiStateView multiStateView;

        private WebView contentView;

        private WebSettings settings;

        public WebAppClient(Context context,
                            MultiStateView multiStateView,
                            WebView contentView) {
            this.context = context;
            this.multiStateView = multiStateView;
            this.contentView = contentView;

            initSetting();
        }

        private void initSetting() {
            settings = contentView.getSettings();

            //注释掉缓存，缓存导致某些界面没法实时更新
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            settings.setJavaScriptEnabled(true);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri.getHost().contains(Constants.PHPHUB_HOST)) {
                HashMap<String, String> segments = new HashMap<>();
                String key = null;
                for (String segment : uri.getPathSegments()) {
                    if (key == null) {
                        key = segment;
                    } else {
                        segments.put(key, segment);
                        key = null;
                    }
                }
                if (segments.size() > 0) {
                    if (segments.containsKey(Constants.PHPHUB_TOPIC_PATH) && !TextUtils.isEmpty(segments.get(Constants.PHPHUB_TOPIC_PATH))) {
                        url = String.format("%s%s?id=%s", Constants.DEEP_LINK_PREFIX, Constants.PHPHUB_TOPIC_PATH, segments.get(Constants.PHPHUB_TOPIC_PATH));
                    } else if (segments.containsKey(Constants.PHPHUB_USER_PATH) && !TextUtils.isEmpty(segments.get(Constants.PHPHUB_USER_PATH))) {
                        url = String.format("%s%s?id=%s", Constants.DEEP_LINK_PREFIX, Constants.PHPHUB_USER_PATH, segments.get(Constants.PHPHUB_USER_PATH));
                    }
                }
            }

            if (url.contains(Constants.DEEP_LINK_PREFIX)) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
                }
            } else {
//                Intent intentToLaunch = WebViewPageActivity.getCallingIntent(context, webUrl);
//                context.startActivity(intentToLaunch);
//                navigator.navigateToWebView(context, url);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (multiStateView != null) {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (multiStateView != null) {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }
            addImageClickEvent();
        }

        private void addImageClickEvent() {
            LocalifyModule localify = new LocalifyClient.Builder()
                    .withAssetManager(context.getAssets())
                    .build()
                    .localify();

            localify.rx()
                    .loadAssetsFile("js/ImageClickEvent.js")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<String, Boolean>() {
                        @Override
                        public Boolean call(String javascript) {
                            return !TextUtils.isEmpty(javascript);
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String javascript) {
                            contentView.loadUrl(javascript.replace("{platform}", PLATFORM));
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.toString());
                        }
                    });
        }
    }

    protected static class WebAppInterface {
        private Context context;


        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(String url) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.Key.TOPIC_IMAGE_URL, url);
            Intent intent = new Intent(context, PhotoShowActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);

        }
    }

    public Map<String, String> getAuth() {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + Networks.getToken());
        return header;
    }

}
