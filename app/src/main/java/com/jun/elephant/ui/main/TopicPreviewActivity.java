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

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.jun.elephant.ui.widget.MultiStateView;
import com.jun.elephant.R;
import com.jun.elephant.common.BaseWebViewActivity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.util.MarkDownRenderer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jun on 2016/5/16.
 */
public class TopicPreviewActivity extends BaseWebViewActivity {
    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;

    private String title;

    private String content;

    private MarkDownRenderer markDownRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        title = getIntent().getExtras().getString(Constants.Key.PREVIEW_TOPIC_TITLE);
        content = getIntent().getExtras().getString(Constants.Key.PREVIEW_TOPIC_CONTENT);

        markDownRenderer = new MarkDownRenderer();
    }

    @Override
    public void initView() {
        super.initView();
        mToolBar.setTitle(title);
        mToolBar.setNavigationIcon(R.mipmap.ic_action_return);
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
        mWebView.setWebViewClient(new WebAppClient(this, mMultiStateView, mWebView));
    }

    @Override
    public void initLoad() {
        super.initLoad();
        String markdownHtml = markDownRenderer.renderMarkdown(content);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.loadData(markdownHtml, "text/html", "UTF-8");
    }
}
