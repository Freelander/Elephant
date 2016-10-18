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
package com.jun.elephant.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.Result;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.mvpframe.base.BaseFrameActivity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.util.JLog;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Jun on 2016/10/14.
 */
public class LoginActivity extends BaseFrameActivity<LoginPresenter, LoginModel> implements ZXingScannerView.ResultHandler, LoginContract.View {

    private ZXingScannerView mScannerView;

    private MaterialDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void initView() {
        super.initView();
        getLoadingDialog().content("正在登录...");
        mLoadingDialog = getLoadingDialog().build();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        String mUserName = "";
        String mLoginToken = "";
        String s = result.getText();
        if (!TextUtils.isEmpty(s) && s.contains(",")) {
            String[] data = s.split(",", 2);
            if (data.length == 2) {
                mUserName = data[0];
                mLoginToken = data[1];
            }
        }

        JLog.d("Login info: ", "UserName === " + mUserName + " LoginToken ======= "+ mLoginToken);

        mPresenter.login(this, mUserName, mLoginToken);
    }

    @Override
    public void onRequestStart() {
        mLoadingDialog.show();
    }

    @Override
    public void onRequestEnd() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onLoginSuccess(UserInfoEntity userInfoEntity) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Key.USER_DATA, userInfoEntity);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

}
