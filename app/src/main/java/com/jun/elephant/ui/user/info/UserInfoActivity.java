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
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jun.elephant.R;
import com.jun.elephant.common.FragmentAdapter;
import com.jun.elephant.entity.user.UserEntity;
import com.jun.elephant.entity.user.UserInfoEntity;
import com.jun.elephant.mvpframe.base.BaseFrameActivity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.ui.main.WebViewActivity;
import com.jun.elephant.ui.user.reply.UserReplyFragment;
import com.jun.elephant.ui.topic.list.TopicListByUserFragment;
import com.jun.elephant.util.ShareUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Jun on 2016/10/14.
 */
public class UserInfoActivity extends BaseFrameActivity<UserInfoPresenter, UserInfoModel> implements UserInfoContract.View, Toolbar.OnMenuItemClickListener {

    @Bind(R.id.main_bg_iv)
    ImageView mUserImgBg;
    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.user_name_tv)
    TextView mUserNameTv;
    @Bind(R.id.user_real_name_tv)
    TextView mUserRealNameTv;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.user_address_tv)
    TextView mUserAddressTv;
    @Bind(R.id.user_avatar_civ)
    SimpleDraweeView mUserAvatarCiv;
    @Bind(R.id.user_info_ll)
    LinearLayout mUserInfoLl;
    @Bind(R.id.signature_tv)
    TextView mSignatureTv;
    @Bind(R.id.user_share_tv)
    TextView mUserShareTv;
    @Bind(R.id.user_reply_tv)
    TextView mUserReplyTv;
    @Bind(R.id.user_follow_tv)
    TextView mUserFollowTv;
    @Bind(R.id.edit_tv)
    TextView mSettingTv;
    @Bind(R.id.user_focus_ll)
    LinearLayout mUserFocusLl;
    @Bind(R.id.page_title_tv)
    TextView mPageTitleTv;

    private TopicListByUserFragment mTopicFragment;
    private TopicListByUserFragment mFollowFragment;
    private TopicListByUserFragment mVoteFragment;
    private UserReplyFragment mReplyFragment;

    private boolean isShowUserInfo = true;
    private boolean isShowUserFoucs = true;
    private boolean isShowUserDesc = true;
    private boolean isShowTitle = true;

    private UserEntity mUserEntity;

    private int mUserId;

    public static Intent newIntent(Context context, int userId) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(Constants.Key.USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Custom_Theme);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mUserId = getIntent().getIntExtra(Constants.Key.USER_ID, 1);
    }

    @Override
    public void initView() {
        setToolbar(mToolBar, "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        mToolBar.inflateMenu(R.menu.menu_user_info);
        alphaView(mPageTitleTv, 200, 4);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float f = ((float) Math.abs(verticalOffset)) / (float) (mAppBarLayout.getTotalScrollRange());

                appBarScrollChange(f);
            }
        });

    }

    @Override
    public void initListener() {
        super.initListener();
        mToolBar.setOnMenuItemClickListener(this);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        mPresenter.getUserInfoById(mUserId);
    }

    @OnClick({R.id.user_avatar_civ, R.id.edit_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.user_avatar_civ:
                break;
            case R.id.edit_tv:
                startActivityForResult(UserInfoEditActivity.newIntent(this, mUserEntity), Constants.Activity.UserInfoEditActivity);
                break;
        }
    }

    private void setupViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("分享");
        titles.add("关注");
        titles.add("赞过");
        titles.add("回复");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));

        Bundle bundleReply = new Bundle();
        bundleReply.putString(Constants.Key.WEB_URL, mUserEntity.getLinks().getReplies_web_view());

        mTopicFragment = new TopicListByUserFragment();
        mFollowFragment = new TopicListByUserFragment();
        mVoteFragment = new TopicListByUserFragment();
        mReplyFragment = new UserReplyFragment();

        mTopicFragment.setArguments(TopicListByUserFragment.newBundle(mUserId, Constants.User.USER_TOPIC_MY));
        mFollowFragment.setArguments(TopicListByUserFragment.newBundle(mUserId, Constants.User.USER_TOPIC_FOLLOW));
        mVoteFragment.setArguments(TopicListByUserFragment.newBundle(mUserId, Constants.User.USER_TOPIC_VOTES));
        mReplyFragment.setArguments(bundleReply);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(mTopicFragment, titles.get(0));
        fragmentAdapter.addFragment(mFollowFragment, titles.get(1));
        fragmentAdapter.addFragment(mVoteFragment, titles.get(2));
        fragmentAdapter.addFragment(mReplyFragment, titles.get(3));

        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);
    }

    private void alphaView(View view, long duration, int i) {
        view.animate().alpha(i == 0 ? 1.0f : 0.0f).setDuration(duration).start();
    }

    private void appBarScrollChange(float f) {
        if (f >= 0.1f) {
            if (isShowUserInfo) {
                alphaView(mUserInfoLl, 200, 4);
                isShowUserInfo = false;
            }
        } else if (!isShowUserInfo){
            alphaView(mUserInfoLl, 200, 0);
            isShowUserInfo = true;
        }

        if (f >= 0.5f) {
            if (isShowUserDesc) {
                alphaView(mSignatureTv, 200, 4);
                isShowUserDesc = false;
            }
        } else if (!isShowUserDesc) {
            alphaView(mSignatureTv, 200, 0);
            mSignatureTv.animate().alpha(0.5f);
            isShowUserDesc = true;
        }

        if (f >= 0.7f) {
            if (isShowUserFoucs) {
                alphaView(mUserFocusLl, 200, 4);
                isShowUserFoucs = false;
            }
        } else if (!isShowUserFoucs) {
            alphaView(mUserFocusLl, 200, 0);
            isShowUserFoucs = true;

        }

        if (f >= 0.86f) {
            if (!isShowTitle) {
                alphaView(mPageTitleTv, 200, 0);
                isShowTitle = true;
            }
        } else if (isShowTitle) {
            alphaView(mPageTitleTv, 200, 4);
            isShowTitle = false;
        }
    }

    private void initUserInfo(UserEntity userEntity) {
        Glide.with(this).load(userEntity.getAvatar())
                .bitmapTransform(new BlurTransformation(this, 5))
                .into(mUserImgBg);
        mCollapsingToolbar.setTitle(userEntity.getName());
        mPageTitleTv.setText(userEntity.getName());
        mUserNameTv.setText(userEntity.getName());
        mUserAvatarCiv.setImageURI(Uri.parse(userEntity.getAvatar()));
        mUserShareTv.setText(String.valueOf(userEntity.getTopic_count()));
        mUserReplyTv.setText(String.valueOf(userEntity.getReply_count()));
        mSignatureTv.setText(userEntity.getIntroduction());

        if (!TextUtils.isEmpty(userEntity.getCity())) {
            mUserAddressTv.setText(userEntity.getCity());
            mUserAddressTv.setVisibility(View.VISIBLE);
        }

        if (getUserConstant().isLogin()) {
            if (userEntity.getId() == getUserConstant().getUserData().getData().getId()) {
                mSettingTv.setVisibility(View.VISIBLE);
            }
        }

        if (!TextUtils.isEmpty(mUserEntity.getGithub_url())) {
            mToolBar.getMenu().findItem(R.id.action_github).setVisible(true);
        }

        if (!TextUtils.isEmpty(mUserEntity.getEmail())) {
            mToolBar.getMenu().findItem(R.id.action_email).setVisible(true);
        }

        if (!TextUtils.isEmpty(mUserEntity.getPersonal_website())) {
            mToolBar.getMenu().findItem(R.id.action_blog).setVisible(true);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_github:
                startActivity(WebViewActivity.newIntent(this, mUserEntity.getGithub_name(), mUserEntity.getGithub_url()));
                break;
            case R.id.action_blog:
                String temp = mUserEntity.getPersonal_website();
                if (!temp.contains("http://") && !temp.contains("https://")) {
                    temp = "http://" + temp;
                }
                startActivity(WebViewActivity.newIntent(this, mUserEntity.getName(), temp));
                break;
            case R.id.action_email:
                ShareUtil.feedback(this, mUserEntity.getEmail());
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.Activity.UserInfoEditActivity && resultCode == RESULT_OK) {
            UserEntity userEntity = data.getExtras().getParcelable(Constants.Key.USER_DATA);
            this.mUserEntity = userEntity;
            initUserInfo(userEntity);
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void getUserInfo(UserInfoEntity userInfoEntity) {
        mUserEntity = userInfoEntity.getData();
        initUserInfo(mUserEntity);
        setupViewPager();
    }
}
