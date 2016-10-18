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
package com.jun.elephant.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jun.elephant.R;
import com.jun.elephant.entity.topic.TopicEntity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 投票会话框
 * Created by Jun on 2016/5/4.
 */
public class VoteDialog extends Dialog implements View.OnClickListener {

    public OnVoteDialogClickListener mOnVoteDialogClickListener;
    @Bind(R.id.vote_up_tv)
    TextView mVoteUpTv;
    @Bind(R.id.vote_down_tv)
    TextView mVoteDownTv;
    private Context mContext;
    private TopicEntity mTopicEntity;

    public VoteDialog(Context context) {
        super(context, R.style.MyDialog_style);
        mContext = context;

        initContentView();
        ButterKnife.bind(this);
        initListener();
    }

    private void initContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_vote, null);

        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        view.setMinimumWidth((int) (displayRectangle.width() * 0.8f));
        window.setBackgroundDrawableResource(R.color.dialog_bg);

        setContentView(view);
    }

    public void setTopicEntity(TopicEntity mTopicEntity) {
        this.mTopicEntity = mTopicEntity;
        initView();
    }

    private void initView() {
        if (mTopicEntity.isVoteDown()) {
            setDrawableTop(mVoteDownTv, R.mipmap.ic_vote_down_select);
        } else {
            setDrawableTop(mVoteDownTv, R.mipmap.ic_vote_down_normal);
        }

        if (mTopicEntity.isVoteUp()) {
            setDrawableTop(mVoteUpTv, R.mipmap.ic_vote_up_select);
        } else {
            setDrawableTop(mVoteUpTv, R.mipmap.ic_vote_up_normal);
        }
    }

    private void initListener() {
        mVoteDownTv.setOnClickListener(this);
        mVoteUpTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vote_down_tv:
                if (mOnVoteDialogClickListener != null)
                    mOnVoteDialogClickListener.onVoteDownClick();
                break;
            case R.id.vote_up_tv:
                if (mOnVoteDialogClickListener != null) mOnVoteDialogClickListener.onVoteUpClick();
                break;
        }
    }

    private void setDrawableTop(TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    public void setOnVoteDialogClickListener(OnVoteDialogClickListener onVoteDialogClickListener) {
        mOnVoteDialogClickListener = onVoteDialogClickListener;
    }

    public interface OnVoteDialogClickListener {
        void onVoteDownClick();

        void onVoteUpClick();
    }
}
