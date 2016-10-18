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
package com.jun.elephant.ui.user.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.jun.elephant.R;
import com.jun.elephant.entity.user.UserEntity;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.mvpframe.base.BaseFrameActivity;
import com.jun.elephant.global.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jun on 2016/10/14.
 */
public class UserInfoEditActivity extends BaseFrameActivity<UserInfoPresenter, UserInfoModel> implements UserInfoContract.View {

    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.name_tv)
    TextView mUserNameTv;
    @Bind(R.id.signature_tv)
    TextView mUserSignatureTv;
    @Bind(R.id.user_address_tv)
    TextView mUserAddressTv;
    @Bind(R.id.user_github_tv)
    TextView mUserGithubTv;
    @Bind(R.id.user_blog_tv)
    TextView mUserBlogTv;
    @Bind(R.id.user_twitter_tv)
    TextView mUserTwitterTv;
    @Bind(R.id.intro_tv)
    TextView mIntroTv;

    private MaterialDialog.Builder mInputDialog;

    private UserEntity mUserEntity;

    private MaterialDialog mLoadingDialog;

    private boolean isChange; //记录是否修改过个人信息

    public static Intent newIntent(Context context, UserEntity userEntity) {
        Intent intent = new Intent(context, UserInfoEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Key.USER_DATA, userEntity);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mUserEntity = getIntent().getExtras().getParcelable(Constants.Key.USER_DATA);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.app_edit_info));
        getLoadingDialog().content(getString(R.string.dialog_modify_loading));
        mLoadingDialog = getLoadingDialog().build();

        initPersonalInfo();

        initDialog();
    }

    private void initPersonalInfo() {
        String signature = mUserEntity.getSignature();
        String city = mUserEntity.getCity();
        String blog = mUserEntity.getPersonal_website();
        String twitter = mUserEntity.getTwitter_account();
        String github = mUserEntity.getGithub_name();
        String name = mUserEntity.getName();
        String intro = mUserEntity.getIntroduction();

        if (name != null) {
            mUserNameTv.setText(name);
        }

        if (city != null) {
            mUserAddressTv.setText(city);
        }

        if (signature != null) {
            mUserSignatureTv.setText(signature);
        }

        if (intro != null) {
            mIntroTv.setText(intro);
        }

        if (blog != null) {
            mUserBlogTv.setText(blog);
        }

        if (twitter != null) {
            mUserTwitterTv.setText(twitter);
        }

        if (github != null) {
            mUserGithubTv.setText(github);
        }
    }

    private void initDialog() {
        mInputDialog = new MaterialDialog.Builder(this);
        mInputDialog.negativeText(getString(R.string.dialog_btn_cancel));
        mInputDialog.positiveText(getString(R.string.dialog_btn_confirm));
        mInputDialog.positiveColorRes(R.color.colorPrimary);
        mInputDialog.negativeColorRes(R.color.colorPrimary);
        mInputDialog.titleColorRes(R.color.text_common);
        mInputDialog.inputType(InputType.TYPE_CLASS_TEXT);
        mInputDialog.widgetColorRes(R.color.colorPrimary);
    }

    @OnClick({R.id.user_name_ll, R.id.user_signature_ll, R.id.user_intro_ll, R.id.user_address_ll, R.id.user_github_ll, R.id.user_blog_ll, R.id.user_twitter_ll})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.user_name_ll:
            case R.id.user_signature_ll:
            case R.id.user_intro_ll:
            case R.id.user_address_ll:
            case R.id.user_github_ll:
            case R.id.user_blog_ll:
            case R.id.user_twitter_ll:
                showDialogs(id);
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        if (isChange) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.Key.USER_DATA, mUserEntity);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }
        super.finish();
    }

    private void showDialogs(int id) {
        switch (id) {
            case R.id.user_name_ll:
                mInputDialog.title(getString(R.string.dialog_edit_name));
                mInputDialog.input("", mUserEntity.getName(),nameCallback);
                break;
            case R.id.user_signature_ll:
                mInputDialog.title(getString(R.string.dialog_edit_signature));
                mInputDialog.input("", mUserEntity.getSignature(), signatureCallback);
                break;
            case R.id.user_intro_ll:
                mInputDialog.title(getString(R.string.dialog_edit_intro));
                mInputDialog.input("", mUserEntity.getIntroduction(), introCallback);
                break;
            case R.id.user_address_ll:
                mInputDialog.title(getString(R.string.dialog_edit_address));
                mInputDialog.input("", mUserEntity.getCity(), addressCallback);
                break;
            case R.id.user_github_ll:
                mInputDialog.title(getString(R.string.dialog_edit_github));
                mInputDialog.input("", mUserEntity.getGithub_name(), githubCallback);
                break;
            case R.id.user_blog_ll:
                mInputDialog.title(getString(R.string.dialog_edit_blog));
                mInputDialog.input("", mUserEntity.getPersonal_website(), blogCallback);
                break;
            case R.id.user_twitter_ll:
                mInputDialog.title(getString(R.string.dialog_edit_twitter));
                mInputDialog.input("", mUserEntity.getTwitter_account(), twitterCallback);
                break;
        }

        mInputDialog.show();
    }

    MaterialDialog.InputCallback nameCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                mUserEntity.setName(input.toString());
                mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback introCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                mUserEntity.setIntroduction(input.toString());
                mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback signatureCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                mUserEntity.setSignature(input.toString());
                mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback addressCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                mUserEntity.setCity(input.toString());
                mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback githubCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                mUserEntity.setGithub_name(input.toString());
                mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback blogCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                mUserEntity.setPersonal_website(input.toString());
                mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    MaterialDialog.InputCallback twitterCallback = new MaterialDialog.InputCallback() {
        @Override
        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
            if (!TextUtils.isEmpty(input)) {
                mUserEntity.setTwitter_account(input.toString());
                mPresenter.saveUserInfoById(mUserEntity.getId(), mUserEntity);
            }
        }
    };

    @Override
    public void getUserInfo(UserInfoEntity userInfoEntity) {
        getUserConstant().saveUserData(new Gson().toJson(userInfoEntity));
        mUserEntity = userInfoEntity.getData();
        initPersonalInfo();
        isChange = true;
        showShortToast(getString(R.string.toast_modify_success));
    }

    @Override
    public void onRequestStart() {
        mLoadingDialog.show();
    }

    @Override
    public void onRequestEnd() {
        mLoadingDialog.dismiss();
    }
}
