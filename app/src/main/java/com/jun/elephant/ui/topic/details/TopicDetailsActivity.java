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
package com.jun.elephant.ui.topic.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jun.elephant.R;
import com.jun.elephant.entity.topic.TopicDetailEntity;
import com.jun.elephant.entity.topic.TopicEntity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.mvpframe.base.BaseFrameWebViewActivity;
import com.jun.elephant.ui.topic.reply.TopicCommentListActivity;
import com.jun.elephant.ui.user.info.UserInfoActivity;
import com.jun.elephant.ui.widget.MultiStateView;
import com.jun.elephant.ui.widget.MySimpleDraweeView;
import com.jun.elephant.ui.widget.VoteDialog;
import com.jun.elephant.util.ShareUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jun on 2016/3/13.
 */
public class TopicDetailsActivity extends BaseFrameWebViewActivity<TopicDetailsPresenter, TopicDetailsModel> implements
        Toolbar.OnMenuItemClickListener, VoteDialog.OnVoteDialogClickListener, TopicDetailsContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.point_tv)
    TextView mPointTv;
    @BindView(R.id.comment_tv)
    TextView mCommentTv;
    @BindView(R.id.favorite_tv)
    TextView mFavoriteTv;
    @BindView(R.id.follow_tv)
    TextView mFollowTv;
    @BindView(R.id.user_img_iv)
    MySimpleDraweeView mUserImgIv;
    @BindView(R.id.user_name_tv)
    TextView mUserNameTv;
    @BindView(R.id.user_desc_tv)
    TextView mUserDescTv;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;

    private TopicEntity mTopicEntity;

    private VoteDialog mVoteDialog;

    private int mTopicId;

    private boolean isLoadFinish; //记录网络加载是否完成，即是否成功取回帖子详情数据

    public static Intent newIntent(Context context, int topicId) {
        Intent intent = new Intent(context, TopicDetailsActivity.class);
        intent.putExtra(Constants.Key.TOPIC_ID, topicId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mTopicId = getIntent().getIntExtra(Constants.Key.TOPIC_ID, 1);
        mVoteDialog = new VoteDialog(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, "");
        mToolBar.inflateMenu(R.menu.menu_topic);
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolBar.setOnMenuItemClickListener(this);
        mVoteDialog.setOnVoteDialogClickListener(this);
    }

    @Override
    public void initLoad() {
        super.initLoad();

        mPresenter.getDetailsInfo(mTopicId);
    }

    private void initViewDetail(TopicEntity mTopicEntity) {
        RelativeLayout mUserInfoRl = (RelativeLayout) mToolBar.getChildAt(1);
        mUserInfoRl.setVisibility(View.VISIBLE);

        mUserImgIv.setImageUrl(mTopicEntity.getUser().getData().getAvatar());

        if (!TextUtils.isEmpty(mTopicEntity.getUser().getData().getIntroduction())) {
            mUserDescTv.setText(mTopicEntity.getUser().getData().getIntroduction());
            mUserDescTv.setVisibility(View.VISIBLE);
        }

        mUserNameTv.setText(mTopicEntity.getUser().getData().getName());
        mCommentTv.setText(String.valueOf(mTopicEntity.getReplyCount()));
        mPointTv.setText(String.valueOf(mTopicEntity.getVoteCount()));

        mWebView.setWebViewClient(new WebAppClient(this, mMultiStateView, mWebView));
        mWebView.loadUrl(mTopicEntity.getLinks().getDetailsWebView(), getAuth());
        mWebView.addJavascriptInterface(new WebAppInterface(this), PLATFORM);

        if (getUserConstant().isLogin()) {
            if (mTopicEntity.isAttention()) {
                setDrawableTop(mFollowTv, R.mipmap.ic_follow_selector);
            } else {
                setDrawableTop(mFollowTv, R.mipmap.ic_follow_normal);
            }

            if (mTopicEntity.isFavorite()) {
                setDrawableTop(mFavoriteTv, R.mipmap.ic_bookmark_selector);
            } else {
                setDrawableTop(mFavoriteTv, R.mipmap.ic_bookmark_normal);
            }

            if (mTopicEntity.isVoteDown()) {
                changeVoteStyle(Constants.TopicOpt.TOPIC_VOTE_DOWN);
            }

            if (mTopicEntity.isVoteUp()) {
                changeVoteStyle(Constants.TopicOpt.TOPIC_VOTE_UP);
            }
        }

    }

    @OnClick({R.id.point_tv, R.id.comment_tv, R.id.favorite_tv, R.id.follow_tv, R.id.user_img_iv})
    public void onClick(View view) {
        if (!isLoadFinish) return; //若还没有加载完成阻止继续下面操作

        switch (view.getId()) {
            case R.id.point_tv:
                if (!getUserConstant().isLogin()) {
                    showShortToast(getString(R.string.toast_no_login));
                    return;
                }

                mVoteDialog.setTopicEntity(mTopicEntity);
                mVoteDialog.show();
                break;
            case R.id.comment_tv:
                startActivity(TopicCommentListActivity.newIntent(this, mTopicEntity.getLinks().getRepliesWebView(), mTopicEntity.getId()));
                break;
            case R.id.user_img_iv:
                startActivity(UserInfoActivity.newIntent(this, Integer.parseInt(mTopicEntity.getUser().getData().getId())));
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (isLoadFinish)
                    ShareUtil.share(this, "分享 " + mTopicEntity.getUser().getData().getName() + " 的文章" + mTopicEntity.getTitle()
                        + " " + mTopicEntity.getLinks().getWebUrl() + "「来自:大象」");
                break;
        }
        return true;
    }

    @Override
    public void onVoteDownClick() {
        mPresenter.optStatusType(Constants.TopicOpt.TOPIC_VOTE_DOWN, mTopicEntity.getId());
    }

    @Override
    public void onVoteUpClick() {
        mPresenter.optStatusType(Constants.TopicOpt.TOPIC_VOTE_UP, mTopicEntity.getId());
    }

    @Override
    public void optVoteUpSuccess(int voteCount) {
        optVote(Constants.TopicOpt.TOPIC_VOTE_UP);
    }

    @Override
    public void optVoteDownSuccess(int voteCount) {
        optVote(Constants.TopicOpt.TOPIC_VOTE_DOWN);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onInternetError() {
        super.onInternetError();
        showShortToast(getString(R.string.toast_opt_fail));
    }

    private void optVote(int type) {
        int voteCount = mTopicEntity.getVoteCount();

        switch (type) {
            case Constants.TopicOpt.TOPIC_VOTE_DOWN:
                if (mTopicEntity.isVoteDown()) {
                    mTopicEntity.setVoteDown(false);
                    changeVoteStyle(-1);
                    voteCount++;
                } else {

                    if (mTopicEntity.isVoteUp()) {
                        voteCount -= 2;
                    } else {
                        voteCount--;
                    }
                    mTopicEntity.setVoteDown(true);
                    changeVoteStyle(type);
                }
                mTopicEntity.setVoteUp(false);
                break;
            case Constants.TopicOpt.TOPIC_VOTE_UP:
                if (mTopicEntity.isVoteUp()) {
                    mTopicEntity.setVoteUp(false);
                    changeVoteStyle(-1);
                    voteCount--;
                } else {
                    if (mTopicEntity.isVoteDown()) {
                        voteCount += 2;
                    } else {
                        voteCount++;
                    }
                    mTopicEntity.setVoteUp(true);
                    changeVoteStyle(type);
                }
                mTopicEntity.setVoteDown(false);
                break;
        }

        mTopicEntity.setVoteCount(voteCount);
        mPointTv.setText(String.valueOf(voteCount));

        if (mVoteDialog.isShowing())
            mVoteDialog.dismiss();
    }

    private void changeVoteStyle(int type) {

        mPointTv.setBackgroundResource(R.drawable.btn1_selector);
        mPointTv.setTextColor(ContextCompat.getColor(this, R.color.text_white));

        switch (type) {
            case Constants.TopicOpt.TOPIC_VOTE_DOWN:
                setDrawableLeft(mPointTv, R.mipmap.ic_vote_down);
                break;
            case Constants.TopicOpt.TOPIC_VOTE_UP:
                setDrawableLeft(mPointTv, R.mipmap.ic_vote_up);
                break;
            default:
                setDrawableLeft(mPointTv, R.mipmap.ic_point);
                mPointTv.setBackgroundResource(R.drawable.btn1_normal);
                mPointTv.setTextColor(ContextCompat.getColor(this, R.color.bg_gray));
                break;
        }

    }

    private void setDrawableTop(TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(this, resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    private void setDrawableLeft(TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(this, resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public void getDetailsInfo(TopicDetailEntity topicDetailEntity) {
        mTopicEntity = topicDetailEntity.getData();
        initViewDetail(topicDetailEntity.getData());
        isLoadFinish = true;
    }
}
