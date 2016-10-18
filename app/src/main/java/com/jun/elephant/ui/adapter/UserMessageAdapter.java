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
package com.jun.elephant.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jun.elephant.R;
import com.jun.elephant.entity.topic.TopicEntity;
import com.jun.elephant.entity.user.MessageEntity;
import com.jun.elephant.entity.user.UserEntity;
import com.jun.elephant.ui.topic.details.TopicDetailsActivity;
import com.jun.elephant.ui.user.info.UserInfoActivity;
import com.jun.elephant.ui.widget.MySimpleDraweeView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jun on 2016/5/6.
 */
public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageAdapter.MessageViewHolder> {

    private Context mContext;

    private List<MessageEntity> mMessageList;

    private Locale mLocale;

    private PrettyTime mPrettyTime;

    private SimpleDateFormat mDateFormat;

    @SuppressLint("SimpleDateFormat")
    public UserMessageAdapter(Context context, List<MessageEntity> messageList) {
        this.mContext = context;
        this.mMessageList = messageList;
        mLocale = mContext.getResources().getConfiguration().locale;
        mPrettyTime = new PrettyTime(mLocale);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final MessageEntity mEntity = mMessageList.get(position);
        final UserEntity mUserEntity = mEntity.getFromUserEntity().getData();
        TopicEntity mTopicEntity = mEntity.getTopic().getData();

        String msg = mEntity.getMessage();

        String msgDate = mUserEntity.getName();
        if (mEntity.getCreatedAt() != null) {

            String dateStr = mEntity.getCreatedAt();

            try {
                msgDate += " • " + mPrettyTime.format(mDateFormat.parse(dateStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        holder.mMsgDateTv.setText(msgDate);
        holder.mAvatarIv.setImageUrl(mUserEntity.getAvatar());
        if (!TextUtils.isEmpty(msg)) {
            holder.mReplyTv.setText(msg);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(TopicDetailsActivity.newIntent(mContext, mEntity.getTopic().getData()));
            }
        });

        holder.mAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(UserInfoActivity.newIntent(mContext, mUserEntity.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private MySimpleDraweeView mAvatarIv;
        private TextView mMsgDateTv;
        private TextView mReplyTv;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mAvatarIv = (MySimpleDraweeView) itemView.findViewById(R.id.user_img_iv);
            mMsgDateTv = (TextView) itemView.findViewById(R.id.msg_date_tv);
            mReplyTv = (TextView) itemView.findViewById(R.id.msg_details_tv);
        }
    }
}
