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
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jun.elephant.Elephant;
import com.jun.elephant.R;
import com.jun.elephant.common.BaseActivity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.util.FileUtils;
import com.jun.elephant.util.OpenWebViewUtils;
import com.jun.elephant.util.ShareUtil;
import com.jun.elephant.util.SystemUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jun on 2016/3/22.
 */
public class SettingActivity extends BaseActivity implements MaterialDialog.SingleButtonCallback {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.version_tv)
    TextView mVersionTv;
    @BindView(R.id.cache_size_tv)
    TextView mCacheSizeTv;

    private MaterialDialog.Builder mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mDialog = new MaterialDialog.Builder(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.app_setting));
        initCleanCacheDialog();

        mVersionTv.setText(String.valueOf(getString(R.string.app_name) + " " + SystemUtil.getVersionName(this)));
        mCacheSizeTv.setText(getCacheSize());
    }

    @OnClick({R.id.feedback_tv, R.id.clear_cache_ll, R.id.about_app_tv, R.id.get_open_source_tv, R.id.about_phphub_tv, R.id.about_me_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feedback_tv:
                ShareUtil.feedback(this, Constants.CONTACT_INFO);
                break;

            case R.id.clear_cache_ll:
                mDialog.show();
                break;

            case R.id.get_open_source_tv:
                OpenWebViewUtils.getOpenSource(this);
                break;

            case R.id.about_phphub_tv:
                OpenWebViewUtils.aboutPHPHub(this);
                break;

            case R.id.about_me_tv:
                OpenWebViewUtils.aboutMe(this);
                break;

            case R.id.about_app_tv:
                openActivity(AboutAppActivity.class);
                break;

            default:
                finish();
                break;
        }
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        switch (which) {
            case POSITIVE:
                cleanCache();
                break;
            case NEGATIVE:
                dialog.dismiss();
                break;
        }
    }

    private void initCleanCacheDialog() {
        mDialog.content(getString(R.string.dialog_clean_cache));
        mDialog.negativeText(getString(R.string.dialog_clean_negative_text));
        mDialog.positiveText(getString(R.string.dialog_clean_positive_text));
        mDialog.positiveColorRes(R.color.colorPrimary);
        mDialog.negativeColorRes(R.color.colorPrimary);
        mDialog.onPositive(this);
    }

    /**
     * 获取缓存大小
     * @return 缓存大小，例 1.63MB
     */
    private String getCacheSize() {
        File dirCacheFile  = Elephant.getOwnCacheDirectory(this, Elephant.APP_CACHE_PATH);
        return FileUtils.getCacheDirSize(dirCacheFile);
    }

    /**
     * 清空缓存
     */
    private void cleanCache() {
        File dirCacheFile  = Elephant.getOwnCacheDirectory(this, Elephant.APP_CACHE_PATH);
        if (FileUtils.deleteDir(dirCacheFile)) { // 删掉缓存目录所有文件
            mCacheSizeTv.setText("");
            showShortToast(getString(R.string.toast_clean_success));
        } else {
            showShortToast(getString(R.string.toast_clean_fail));
        }
    }

}
