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
package com.jun.elephant.ui.topic.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jun.elephant.R;
import com.jun.elephant.entity.topic.TopicEntity;
import com.jun.elephant.entity.topic.TopicListEntity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.mvpframe.base.BaseFrameFragment;
import com.jun.elephant.ui.adapter.TopicListAdapter;
import com.jun.elephant.ui.topic.publish.TopicPublishActivity;
import com.jun.elephant.ui.widget.MultiStateView;
import com.jun.elephant.ui.widget.MySwipeRefreshLayout;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Jun on 2016/4/18.
 */
public class TopicListByMeFragment extends BaseFrameFragment<TopicPresenter, TopicModel> implements TopicContract.View {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_layout)
    MySwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @Bind(R.id.fab)
    FloatingActionButton mFabBtn;

    private List<TopicEntity> mTopicList;

    public int TYPE = Constants.User.USER_TOPIC_MY;

    private int mUserId;

    private TopicListAdapter mAdapter;

    private int mPageIndex = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_topic_list);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initData() {
        super.initData();
        mTopicList = new ArrayList<>();
        mAdapter = new TopicListAdapter(getContext(), mTopicList);
        mUserId = getUserConstant().getUserData().getData().getId();
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mFabBtn.attachToRecyclerView(mRecyclerView);

        mSwipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                mPageIndex ++;
                mPresenter.getTopicListByUser(TYPE, mUserId, mPageIndex);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                mPageIndex = 1;
                mPresenter.getTopicListByUser(TYPE, mUserId, mPageIndex);
            }
        });

        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(TopicPublishActivity.class);
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        getData();
    }

    public void getData() {
        int state = mMultiStateView.getViewState();
        if (state == MultiStateView.VIEW_STATE_EMPTY || state == MultiStateView.VIEW_STATE_ERROR) {
            mPageIndex = 1;
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.getTopicListByUser(TYPE, mUserId, mPageIndex);
            return;
        }
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.autoRefresh();
            }
        });
    }

    @Override
    public void refreshTopicList(TopicListEntity topicListEntity) {
        if (topicListEntity.getData().size() == 0) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            return;
        }

        mTopicList.clear();
        mTopicList.addAll(topicListEntity.getData());
        mAdapter.notifyDataSetChanged();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void loadMoreTopicList(TopicListEntity topicListEntity) {
        List<TopicEntity> temp = topicListEntity.getData();
        if (temp.size() != 0) {
            mTopicList.addAll(temp);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {
        if (mSwipeLayout != null) mSwipeLayout.refreshComplete();
    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
