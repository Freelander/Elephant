/*
 * Copyright 2016 Freelander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jun.elephant.ui.topic.list;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Jun on 2016/7/9.
 */
public class TopicListByForumFragment extends BaseFrameFragment<TopicPresenter, TopicModel> implements TopicContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout)
    MySwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.fab)
    FloatingActionButton mFabBtn;

    private TopicListAdapter mAdapter;

    private List<TopicEntity> mTopicList;

    public String TYPE = Constants.Topic.EXCELLENT;

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
                mPageIndex++;
                mPresenter.getTopicListByForum(TYPE, mPageIndex);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                mPageIndex = 1;
                mPresenter.getTopicListByForum(TYPE, mPageIndex);
            }
        });

        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserConstant().isLogin()) {
                    openActivity(TopicPublishActivity.class);
                } else {
                    showShortToast("先登录...");
                }
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
//        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);

        getData();
    }

    public void getData() {
        int state = mMultiStateView.getViewState();
        if (state == MultiStateView.VIEW_STATE_EMPTY || state == MultiStateView.VIEW_STATE_ERROR) {
            mPageIndex = 1;
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            mPresenter.getTopicListByForum(TYPE, mPageIndex);
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
    public void onDestroyView() {
        super.onDestroyView();
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
    public void loadMoreTopicList(final TopicListEntity topicListEntity) {
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
        mSwipeLayout.refreshComplete();
    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    public void refreshUI() {
        TypedValue badgeColor = new TypedValue();
        TypedValue floatBtnColor = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.elephantBadge, badgeColor, true);
        theme.resolveAttribute(R.attr.elephantFloatBtn, floatBtnColor, true);

        int childCount = mRecyclerView.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            ViewGroup childView = (ViewGroup) mRecyclerView.getChildAt(childIndex);
            BGABadgeRelativeLayout bgarl = (BGABadgeRelativeLayout) childView.findViewById(R.id.bgaRl);
            bgarl.getBadgeViewHelper().setBadgeBgColorInt(ContextCompat.getColor(getContext(), badgeColor.resourceId));

        }

        mFabBtn.setColorNormalResId(floatBtnColor.resourceId);
        mFabBtn.setColorPressedResId(floatBtnColor.resourceId);

        //让 RecyclerView 缓存在 Pool 中的 Item 失效
        //那么，如果是ListView，要怎么做呢？这里的思路是通过反射拿到 AbsListView 类中的 RecycleBin 对象，然后同样再用反射去调用 clear 方法
        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(mRecyclerView), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = mRecyclerView.getRecycledViewPool();
            recycledViewPool.clear();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
