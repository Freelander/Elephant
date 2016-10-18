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
package com.jun.elephant.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.jun.elephant.ui.widget.MultiStateView;
import com.jun.elephant.R;
import com.jun.elephant.common.BaseWebViewActivity;
import com.jun.elephant.global.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jun on 2016/4/18.
 */
public class WebViewActivity extends BaseWebViewActivity {

    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;

    private String mWebUrl, mTitle;

    public static Intent newIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.Key.WEB_TITLE, title);
        intent.putExtra(Constants.Key.WEB_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mWebUrl = getIntent().getStringExtra(Constants.Key.WEB_URL);
        mTitle = getIntent().getStringExtra(Constants.Key.WEB_TITLE);
    }

    @Override
    public void initView() {
        super.initView();
        mToolBar.setTitle(mTitle);
    }

    @Override
    public void initLoad() {
        super.initLoad();

        mWebView.setWebViewClient(new WebAppClient(this, mMultiStateView, mWebView));
        mWebView.loadUrl(mWebUrl, getAuth());
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.finish();
        }
    }
}
