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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jun.elephant.R;
import com.jun.elephant.global.Constants;
import com.jun.elephant.global.UserConstant;
import com.jun.elephant.util.JLog;
import com.jun.elephant.util.ThemeUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Jun on 2016/4/29.
 */
public class BaseActivity extends AppCompatActivity implements BaseFuncIml , View.OnClickListener {

    protected Fragment mCurrFragment;

    private int mFragmentId;

    private static final String TAG = "BaseActivity";

    private UserConstant mUserConstant;

    private MaterialDialog.Builder mLoadingDialog;

    public ThemeUtil mThemeUtil;

    private long mExitTime;

    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
        initView();
        initListener();
        initLoad();
    }

    @Override
    protected void onStart() {
        JLog.logd(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        JLog.logd(TAG, "onResume");
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        JLog.logd(TAG, "onPause");
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        JLog.logd(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        JLog.logd(TAG, "onDestroy");
        super.onDestroy();
    }

    public void setFrameId(int mFragmentId) {
        this.mFragmentId = mFragmentId;
    }

    protected void toFragment(Fragment toFragment) {
        if (mCurrFragment == null) {
            showShortToast("mCurrFragment is null!");
            return;
        }

        if (toFragment == null) {
            showShortToast("toFragment is null!");
            return;
        }

        if (toFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(mCurrFragment)
                    .show(toFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(mCurrFragment)
                    .add(mFragmentId, toFragment).show(toFragment)
                    .commit();
        }

        mCurrFragment = toFragment;

    }

    public Fragment getCurrFragment() {
        return mCurrFragment;
    }

    public void setCurrFragment(Fragment mCurrFragment) {
        this.mCurrFragment = mCurrFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData() {
        mUserConstant = UserConstant.getInstance(this);
    }

    @Override
    public void initView() {
        initLoadingDialog();
    }

    private void initLoadingDialog() {
        mLoadingDialog = new MaterialDialog.Builder(this);
        mLoadingDialog.title("请稍候");
        mLoadingDialog.progress(true, 0);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }

    private void initTheme() {
        mThemeUtil = ThemeUtil.getInstance(this);
        String theme = mThemeUtil.getTheme();
        switch (theme) {
            case Constants.Theme.Blue:
                setTheme(R.style.BlueTheme);
                break;

            case Constants.Theme.White:
                setTheme(R.style.WhiteTheme);
                break;

            case Constants.Theme.Gray:
                setTheme(R.style.GrayTheme);
                break;

            default:
                setTheme(R.style.BlueTheme);
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }

    protected void showShortToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);

    }

    public UserConstant getUserConstant() {
        return mUserConstant;
    }

    public MaterialDialog.Builder getLoadingDialog() {
        return mLoadingDialog;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    showShortToast("再按一次退出程序");
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }

    protected  void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
