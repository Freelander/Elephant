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
package com.jun.elephant.ui.user.reply;

import android.os.Bundle;
import android.webkit.WebView;

import com.jun.elephant.R;
import com.jun.elephant.api.Networks;
import com.jun.elephant.common.BaseFragment;
import com.jun.elephant.common.WebAppClient;
import com.jun.elephant.global.Constants;
import com.jun.elephant.ui.widget.MultiStateView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jun on 2016/5/8.
 */
public class UserReplyFragment extends BaseFragment {
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    private String mWebUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_webview);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initData() {
        super.initData();
        mWebUrl = getArguments().getString(Constants.Key.WEB_URL);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        mWebView.setWebViewClient(new WebAppClient(getContext(), mMultiStateView, mWebView));
        mWebView.loadUrl(mWebUrl, getAuth());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public Map<String, String> getAuth() {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + Networks.getToken());
        return header;
    }
}
