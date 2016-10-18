package com.jun.elephant.ui.topic.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jun.elephant.R;
import com.jun.elephant.entity.topic.TopicEntity;
import com.jun.elephant.entity.topic.TopicListEntity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.mvpframe.base.BaseFrameFragment;
import com.jun.elephant.ui.adapter.TopicListAdapter;
import com.jun.elephant.ui.widget.MultiStateView;
import com.jun.elephant.ui.widget.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Jun on 2016/3/18.
 */
public class TopicListByUserFragment extends BaseFrameFragment<TopicPresenter, TopicModel> implements TopicContract.View {

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @Bind(R.id.swipe_layout)
    MySwipeRefreshLayout mSwipeLayout;

    private TopicListAdapter mAdapter;

    private List<TopicEntity> mTopicList;

    private int mUserId, mType;

    private int mPageIndex = 1;

    /**
     *
     * @param userId 用户 Id
     * @param type 用户分享、赞过的话题
     * @return bundle
     */
    public static Bundle newBundle(int userId, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.USER_ID, userId);
        bundle.putInt(Constants.Key.TOPIC_TYPE, type);
        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_topic_list);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initData() {
        super.initData();
        mUserId = getArguments().getInt(Constants.Key.USER_ID);
        mType = getArguments().getInt(Constants.Key.TOPIC_TYPE);

        mTopicList = new ArrayList<>();
        mAdapter = new TopicListAdapter(getContext(), mTopicList);
    }

    @Override
    public void initView() {
        super.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mSwipeLayout.setMode(PtrFrameLayout.Mode.LOAD_MORE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void initListener() {
        super.initListener();

        mSwipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                mPageIndex ++;
                mPresenter.getTopicListByUser(mType, mUserId, mPageIndex);

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                mPageIndex = 1;
                mPresenter.getTopicListByUser(mType, mUserId, mPageIndex);
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
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.autoRefresh();
            }
        });
    }

    @Override
    public void refreshTopicList(TopicListEntity topicListEntity) {
        if (mRecyclerView == null)
            return;

        if (topicListEntity.getData().size() == 0) {
            mSwipeLayout.refreshComplete();
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            return;
        }

        mTopicList.clear();
        mTopicList.addAll(topicListEntity.getData());
        mRecyclerView.setAdapter(mAdapter);
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
    public void onInternetError() {
        super.onInternetError();
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
