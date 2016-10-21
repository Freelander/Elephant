package com.jun.elephant.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jun.elephant.R;
import com.jun.elephant.entity.topic.TopicEntity;
import com.jun.elephant.ui.topic.details.TopicDetailsActivity;
import com.jun.elephant.ui.user.info.UserInfoActivity;
import com.jun.elephant.ui.widget.MySimpleDraweeView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;


/**
 * Created by Jun on 2016/9/23.
 */

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicViewHolder> {

    private List<TopicEntity> mDatas;

    private Context mContext;

    private Locale mLocale;

    private PrettyTime mPrettyTime;

    private SimpleDateFormat mDateFormat;

    @SuppressLint("SimpleDateFormat")
    public TopicListAdapter(Context context, List<TopicEntity> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mLocale = mContext.getResources().getConfiguration().locale;
        mPrettyTime = new PrettyTime(mLocale);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_topic_list, parent, false));
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        final TopicEntity item = mDatas.get(position);

        String mCommentCount = String.valueOf(item.getReplyCount());
        StringBuilder temp = new StringBuilder();

        if (item.getReplyCount() > 99) {
            mCommentCount = "99+";
        }

        if (item.getCategory() != null) {
            temp.append(item.getCategory().getData().getName());
        }

        if (item.getLastReplyUser() != null) {
            temp.append(" • ").append("最后由").append(item.getLastReplyUser().getData().getName());
        }

        if (item.getUpdatedAt() != null) {
            String dateStr = item.getUpdatedAt();
            try {
                String prettyTimeString = mPrettyTime.format(mDateFormat.parse(dateStr));
                temp.append(" • ").append(prettyTimeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String avatarUrl = item.getUser().getData().getAvatar();
        if (avatarUrl != null) {
            holder.mUserImg.setImageUrl(avatarUrl);
        }

        holder.mTitleTv.setText(item.getTitle());
        holder.mTimeTv.setText(temp.toString());
        holder.mBgaRl.showTextBadge(mCommentCount);

        holder.mBgaRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(TopicDetailsActivity.newIntent(mContext, item.getId()));
            }
        });

        holder.mUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(UserInfoActivity.newIntent(mContext, Integer.parseInt(item.getUser().getData().getId())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {
        private BGABadgeRelativeLayout mBgaRl;
        private MySimpleDraweeView mUserImg;
        private TextView mTitleTv;
        private TextView mTimeTv;

        TopicViewHolder(View itemView) {
            super(itemView);
            mBgaRl = (BGABadgeRelativeLayout) itemView.findViewById(R.id.bgaRl);
            mTimeTv = (TextView) itemView.findViewById(R.id.content_time_tv);
            mTitleTv = (TextView) itemView.findViewById(R.id.content_title_tv);
            mUserImg = (MySimpleDraweeView) itemView.findViewById(R.id.user_img_iv);
        }
    }
}
