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

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.jun.elephant.R;
import com.jun.elephant.api.Networks;
import com.jun.elephant.common.BaseActivity;
import com.jun.elephant.util.OpenWebViewUtils;
import com.jun.elephant.entity.DrawerMenuEntity;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.global.UserConstant;
import com.jun.elephant.ui.adapter.DrawerMenuAdapter;
import com.jun.elephant.ui.topic.list.TopicListByMeFragment;
import com.jun.elephant.ui.login.LoginActivity;
import com.jun.elephant.ui.user.info.UserInfoActivity;
import com.jun.elephant.ui.user.message.UserMessageActivity;
import com.jun.elephant.ui.topic.list.TopicListByForumFragment;
import com.jun.elephant.ui.widget.MySimpleDraweeView;
import com.jun.elephant.ui.widget.ThemeDialog;
import com.jun.elephant.util.SharePreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jun on 2016/3/1.
 */
public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,
        ThemeDialog.OnThemeChangeListener,
        DrawerMenuAdapter.OnMenuItemClickListener,
        MaterialDialog.SingleButtonCallback {

    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.container)
    FrameLayout mContainer;
    @Bind(R.id.main_content)
    LinearLayout mMainContentLl;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.user_email_tv)
    TextView mUserEmailTv;
    @Bind(R.id.menu_down_iv)
    ImageView mMenuDownIv;
    @Bind(R.id.drawer_menu_rc)
    RecyclerView mDrawerMenuRc;
    @Bind(R.id.navigation_view)
    LinearLayout mNavigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.user_iv)
    MySimpleDraweeView mUserImgIv;
    @Bind(R.id.login_tv)
    TextView mLoginTv;
    @Bind(R.id.login_success_rl)
    RelativeLayout mLoginShowRl;

    private List<DrawerMenuEntity> mMainMenuList, mMyMenuList;

    private DrawerMenuAdapter mMenuAdapter;

    private TopicListByForumFragment mCommonFragment;

    private TopicListByMeFragment mTopicListByMeFragment;

    private UserInfoEntity mUserInfoEntity;

    private ThemeDialog mThemeDialog;

    private MaterialDialog.Builder mExitLoginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setFrameId(R.id.container);
        setExit(true);
    }

    @Override
    public void initData() {
        super.initData();

        mThemeDialog = new ThemeDialog(this);
        mExitLoginDialog = new MaterialDialog.Builder(this);

        mCommonFragment = new TopicListByForumFragment();
        mTopicListByMeFragment = new TopicListByMeFragment();

        String token = SharePreferencesHelper.getInstance(this).getString(Constants.Key.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            Networks.setToken(token);
        }

        mMainMenuList = new ArrayList<>();
        mMainMenuList.add(new DrawerMenuEntity(getString(R.string.menu_recommend), R.mipmap.ic_main_recommend));
        mMainMenuList.add(new DrawerMenuEntity(getString(R.string.menu_hot), R.mipmap.ic_main_hot));
        mMainMenuList.add(new DrawerMenuEntity(getString(R.string.menu_newest), R.mipmap.ic_main_newest));
        mMainMenuList.add(new DrawerMenuEntity(getString(R.string.menu_nobody), R.mipmap.ic_main_nobody));
        mMainMenuList.add(new DrawerMenuEntity(getString(R.string.menu_jobs), R.mipmap.ic_main_work));
        mMainMenuList.add(new DrawerMenuEntity(getString(R.string.menu_wiki), R.mipmap.ic_main_wiki));

        mMyMenuList = new ArrayList<>();
        mMyMenuList.add(new DrawerMenuEntity(getString(R.string.menu_vote), R.mipmap.ic_my_vote));
        mMyMenuList.add(new DrawerMenuEntity(getString(R.string.menu_topic), R.mipmap.ic_my_topic));
        mMyMenuList.add(new DrawerMenuEntity(getString(R.string.menu_revert), R.mipmap.ic_my_reply));

        mMenuAdapter = new DrawerMenuAdapter(this, mMainMenuList);

    }

    @Override
    public void initView() {
        initExitLoginDialog();
        initToolbar();

        if (getUserConstant().isLogin()) {
            mUserInfoEntity = UserConstant.getInstance(this).getUserData();

            Uri uri = Uri.parse(mUserInfoEntity.getData().getAvatar());
            mUserImgIv.setImageURI(uri);
            mUserEmailTv.setText(mUserInfoEntity.getData().getEmail());
            mNameTv.setText(mUserInfoEntity.getData().getName());

            mLoginShowRl.setVisibility(View.VISIBLE);
            mLoginTv.setVisibility(View.GONE);

            mMainMenuList.addAll(mMyMenuList);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mDrawerMenuRc.setLayoutManager(layoutManager);
        mDrawerMenuRc.setAdapter(mMenuAdapter);

        Bundle bundle = new Bundle();
        bundle.putString(Constants.Key.TOPIC_TYPE, Constants.Topic.EXCELLENT);
        mCommonFragment.setArguments(bundle);
        setCurrFragment(mCommonFragment);
        toFragment(mCommonFragment);
    }

    private void initToolbar() {
        mToolBar.inflateMenu(R.menu.menu_main);
        Resources.Theme theme = getTheme();
        TypedValue navIcon = new TypedValue();
        TypedValue overFlowIcon = new TypedValue();

        theme.resolveAttribute(R.attr.navIcon, navIcon, true);
        theme.resolveAttribute(R.attr.overFlowIcon, overFlowIcon, true);

        mToolBar.setNavigationIcon(navIcon.resourceId);
        mToolBar.setOverflowIcon(ContextCompat.getDrawable(this, overFlowIcon.resourceId));
    }

    private void initExitLoginDialog() {
        mExitLoginDialog.content(getString(R.string.dialog_exit_login))
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.colorPrimary)
                .positiveText(getString(R.string.dialog_exit_positive_text))
                .negativeText(getString(R.string.dialog_exit_negative_text));
    }

    @Override
    public void initListener() {
        mToolBar.setOnMenuItemClickListener(this);
        mToolBar.setNavigationOnClickListener(this);
        mThemeDialog.setOnThemeChangeListener(this);
        mMenuAdapter.setOnMenuItemClickListener(this);
        mExitLoginDialog.onPositive(this);
    }

    private void toUserFragment() {
        toFragment(mTopicListByMeFragment);
        if (mTopicListByMeFragment.isAdded()) {
            mTopicListByMeFragment.getData();
        }
    }

    private void toCommonFragment() {
        toFragment(mCommonFragment);
        if (mCommonFragment.isAdded()) {
            mCommonFragment.getData();
        }
    }

    @OnClick({R.id.login_tv, R.id.login_success_rl, R.id.user_iv, R.id.setting_ll, R.id.login_help_ll})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_success_rl:
                mExitLoginDialog.show();
                break;

            case R.id.login_tv:
            case R.id.user_iv:
                if (getUserConstant().isLogin()) {
                    startActivity(UserInfoActivity.newIntent(this, getUserConstant().getUserData().getData().getId()));
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, Constants.Activity.LoginActivity);
                }
                break;
            case R.id.setting_ll:
                mThemeDialog.show();
                break;
            case R.id.login_help_ll:
                OpenWebViewUtils.loginHelp(this);
                break;
            default:
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
    }

    /**
     * 退出登录 dialog 按钮点击监听事件
     * @param dialog
     * @param which
     */
    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        switch (which) {
            case POSITIVE:
                if (getUserConstant().logout()) { //退出登录
                    mLoginShowRl.setVisibility(View.GONE);
                    mLoginTv.setVisibility(View.VISIBLE);
                    mMainMenuList.removeAll(mMyMenuList);
                    mMenuAdapter.notifyDataSetChanged();
                    mUserImgIv.setImageURI(Uri.parse("res://com.jun.elephant/" + R.color.main_bg));
                    dialog.dismiss();
                }
                break;
            case NEGATIVE:
                dialog.dismiss();
                break;
        }
    }

    /**
     * DrawerLayout menu click callback
     * @param position menu click position
     */
    @Override
    public void onMenuItemClick(int position) {
        switch (position) {
            case 0:
                switchFragment(getString(R.string.menu_recommend), Constants.Topic.EXCELLENT);
                break;
            case 1:
                switchFragment(getString(R.string.menu_hot), Constants.Topic.VOTE);
                break;
            case 2:
                switchFragment(getString(R.string.menu_newest), Constants.Topic.NEWEST);
                break;
            case 3:
                switchFragment(getString(R.string.menu_nobody), Constants.Topic.NOBODY);
                break;
            case 4:
                switchFragment(getString(R.string.menu_jobs), Constants.Topic.JOBS);
                break;
            case 5:
                switchFragment(getString(R.string.menu_wiki), Constants.Topic.WIKI);
                break;
            case 6:
                mToolBar.setTitle(getString(R.string.menu_vote));
                mTopicListByMeFragment.TYPE = Constants.User.USER_TOPIC_VOTES;
                toUserFragment();
                break;
            case 7:
                mToolBar.setTitle(getString(R.string.menu_topic));
                mTopicListByMeFragment.TYPE = Constants.User.USER_TOPIC_MY;
                toUserFragment();
                break;
            case 8:
                OpenWebViewUtils.myReply(MainActivity.this, mUserInfoEntity.getData().getLinks().getReplies_web_view());
                break;
        }

        if (position != 8) {
            mMenuAdapter.setSelectPosition(position);
            mMenuAdapter.notifyDataSetChanged();
            mDrawerLayout.closeDrawers();
        }
    }

    /**
     * toolbar menu click callback
     * @param item menu item
     * @return true
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notice:
                if (getUserConstant().isLogin()) {
                    openActivity(UserMessageActivity.class);
                } else {
                    showShortToast(getString(R.string.toast_no_login));
                }
                break;

            case R.id.action_about:
                OpenWebViewUtils.aboutMe(this);
                break;

            case R.id.action_search:
                showShortToast(getString(R.string.toast_adorn));
                break;

            case R.id.action_settings:
                openActivity(SettingActivity.class);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Constants.Activity.LoginActivity) {
            mUserInfoEntity = data.getExtras().getParcelable(Constants.Key.USER_DATA);

            assert mUserInfoEntity != null;
            Uri uri = Uri.parse(mUserInfoEntity.getData().getAvatar());
            mUserImgIv.setImageURI(uri);
            mUserEmailTv.setText(mUserInfoEntity.getData().getEmail());
            mNameTv.setText(mUserInfoEntity.getData().getName());

            getUserConstant().saveUserData(new Gson().toJson(mUserInfoEntity));

            mLoginShowRl.setVisibility(View.VISIBLE);
            mLoginTv.setVisibility(View.GONE);

            mMainMenuList.addAll(mMyMenuList);
            mMenuAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 切换 fragment 操作
     * @param title 标题
     * @param type 类型
     */
    private void switchFragment(String title, String type) {
        mCommonFragment.TYPE = type;
        mToolBar.setTitle(title);
        toCommonFragment();
    }

    /**
     * 改变主题
     */
    private void changeTheme() {
        refreshUI();
        mCommonFragment.refreshUI();
        mDrawerLayout.closeDrawers();
    }

    /**
     * 刷新 UI
     * 遍历所有view，替换相对应主题颜色
     */
    private void refreshUI() {
        TypedValue themeColor = new TypedValue();        //主题
        TypedValue statusColor = new TypedValue();       //状态栏
        TypedValue toolbarTextColor = new TypedValue();  //状态栏字体颜色
        TypedValue navIcon = new TypedValue();           //toolbar 导航图标
        TypedValue searchIcon = new TypedValue();        //toolbar 搜索图标
        TypedValue noticeIcon = new TypedValue();        //toolbar 通知图标
        TypedValue overFlowIcon = new TypedValue();          //toolbar 更多图标

        //获取切换后的主题，以及主题相对应对的属性值
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.elephantTheme, themeColor, true);
        theme.resolveAttribute(R.attr.elephantStatus, statusColor, true);
        theme.resolveAttribute(R.attr.elephantToolbarText, toolbarTextColor, true);
        theme.resolveAttribute(R.attr.navIcon, navIcon, true);
        theme.resolveAttribute(R.attr.menuSearch, searchIcon, true);
        theme.resolveAttribute(R.attr.menuNotice, noticeIcon, true);
        theme.resolveAttribute(R.attr.overFlowIcon, overFlowIcon, true);

        //切换到主题相对应的图标以及颜色值
        mToolBar.getMenu().findItem(R.id.action_search).setIcon(searchIcon.resourceId);
        mToolBar.getMenu().findItem(R.id.action_notice).setIcon(noticeIcon.resourceId);
        mToolBar.setNavigationIcon(navIcon.resourceId);
        mToolBar.setOverflowIcon(ContextCompat.getDrawable(this, overFlowIcon.resourceId));
        mToolBar.setBackgroundColor(ContextCompat.getColor(this, themeColor.resourceId));
        mToolBar.setTitleTextColor(ContextCompat.getColor(this, toolbarTextColor.resourceId));

        changeStatusColor(statusColor.resourceId);
    }

    /**
     * 切换状态栏颜色值
     * @param colorValue 颜色值
     */
    private void changeStatusColor(int colorValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, colorValue));
        }
    }

    @Override
    public void onChangeTheme(View view) {
        switch (view.getId()) {
            case R.id.theme_blue:
                setTheme(R.style.BlueTheme);                //设置主题
                mThemeUtil.setTheme(Constants.Theme.Blue);  //保存当前主题
                break;
            case R.id.theme_gray:
                setTheme(R.style.GrayTheme);
                mThemeUtil.setTheme(Constants.Theme.Gray);
                break;
            case R.id.theme_white:
                setTheme(R.style.WhiteTheme);
                mThemeUtil.setTheme(Constants.Theme.White);
                break;
        }
        changeTheme();
    }

}
