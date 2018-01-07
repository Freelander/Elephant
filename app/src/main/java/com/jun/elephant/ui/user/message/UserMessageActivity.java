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
package com.jun.elephant.ui.user.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jun.elephant.R;
import com.jun.elephant.entity.user.MessageEntity;
import com.jun.elephant.entity.user.UserMessageEntity;
import com.jun.elephant.mvpframe.base.BaseFrameActivity;
import com.jun.elephant.ui.adapter.UserMessageAdapter;
import com.jun.elephant.ui.widget.MultiStateView;
import com.jun.elephant.ui.widget.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Jun on 2016/5/6.
 */
public class UserMessageActivity extends BaseFrameActivity<MessagePresenter, MessageModel> implements MessageListContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.swipe_layout)
    MySwipeRefreshLayout mSwipeLayout;

    private UserMessageAdapter mAdapter;

    private List<MessageEntity> mMessageList;

    private int mPageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mMessageList = new ArrayList<>();
        mAdapter = new UserMessageAdapter(this, mMessageList);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.app_user_message));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mSwipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                mPageIndex ++;
                mPresenter.getMessageList(mPageIndex);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                mPageIndex = 1;
                mPresenter.getMessageList(mPageIndex);
            }
        });

    }

    @Override
    public void initLoad() {
        super.initLoad();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);

        mPresenter.getMessageList(mPageIndex);
    }

    @Override
    public void refreshMessageList(UserMessageEntity userMessageEntity) {
        if (userMessageEntity.getData().size() == 0){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            return;
        }
        mMessageList.clear();
        mMessageList.addAll(userMessageEntity.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMoreMessageList(UserMessageEntity userMessageEntity) {
        mMessageList.addAll(userMessageEntity.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
        mSwipeLayout.refreshComplete();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
