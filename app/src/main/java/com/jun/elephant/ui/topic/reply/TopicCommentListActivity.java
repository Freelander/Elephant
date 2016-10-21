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
package com.jun.elephant.ui.topic.reply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.jun.elephant.R;
import com.jun.elephant.entity.topic.TopicReplyEntity;
import com.jun.elephant.mvpframe.base.BaseFrameWebViewActivity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.ui.widget.MultiStateView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jun on 2016/3/14.
 */
public class TopicCommentListActivity extends BaseFrameWebViewActivity<TopicReplyPresenter, TopicReplyModel>
        implements TopicReplyContract.View {
    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.bottom_rl)
    LinearLayout mBottomRl;
    @Bind(R.id.comment_edt)
    AppCompatEditText mCommentEdt;

    private String mCommentUrl;

    private int mTopicId;

    public static Intent newIntent(Context context, String commentUrl, int topicId) {
        Intent intent = new Intent(context, TopicCommentListActivity.class);
        intent.putExtra(Constants.Key.COMMENT_URL, commentUrl);
        intent.putExtra(Constants.Key.TOPIC_ID, topicId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mCommentUrl = getIntent().getStringExtra(Constants.Key.COMMENT_URL);
        mTopicId = getIntent().getIntExtra(Constants.Key.TOPIC_ID, 1);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.app_comment_list));
    }

    @Override
    public void initLoad() {
        super.initLoad();

        mWebView.setWebViewClient(new WebAppClient(this, mMultiStateView, mWebView));
        mWebView.loadUrl(mCommentUrl, getAuth());
        mWebView.addJavascriptInterface(new WebAppInterface(this), PLATFORM);
    }

    @OnClick({R.id.send_iv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send_iv:
                if (getUserConstant().isLogin()) {
                    if (TextUtils.isEmpty(mCommentEdt.getText())) {
                        showShortToast(getString(R.string.toast_no_content));
                        return;
                    }

                    mPresenter.reply(mTopicId, mCommentEdt.getText().toString());
                } else {
                    showShortToast(getString(R.string.toast_no_login));
                }
                break;
        }
    }

    @Override
    public void replySuccess(TopicReplyEntity topicReplyEntity) {
        mCommentEdt.setText("");
        if (topicReplyEntity.getData() != null) mWebView.loadUrl(mCommentUrl, getAuth());
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
        hideKeyboard(mCommentEdt);
    }

    @Override
    public void onInternetError() {
        super.onInternetError();
        showShortToast(getString(R.string.toast_comment_fail));
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }
}
