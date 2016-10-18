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

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jun.elephant.R;
import com.jun.elephant.entity.DrawerMenuEntity;

import java.util.List;

/**
 * Created by Jun on 2016/3/9.
 */
public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.DrawerHolder> {
    
    private Context mContext;
    
    private List<DrawerMenuEntity> mMenuList;

    private int mSelectPosition = 0;
    
    private OnMenuItemClickListener onMenuItemClickListener;

    public DrawerMenuAdapter(Context context, List<DrawerMenuEntity> menuList) {
        this.mContext = context;
        this.mMenuList = menuList;
    }

    public void setSelectPosition(int mSelectPosition) {
        this.mSelectPosition = mSelectPosition;
    }

    @Override
    public DrawerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerHolder(LayoutInflater.from(mContext).inflate(R.layout.item_drawer_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(final DrawerHolder holder, int position) {
        DrawerMenuEntity item = mMenuList.get(position);
        holder.mMenuText.setText(item.getTitle());
        holder.mMenuIcon.setImageResource(item.getIcon());
        
        if (mSelectPosition == position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.line_space));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.text_white));
        }
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuItemClickListener != null)
                    onMenuItemClickListener.onMenuItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }

    class DrawerHolder extends RecyclerView.ViewHolder {
        
        private TextView mMenuText;
        private ImageView mMenuIcon;

        public DrawerHolder(View itemView) {
            super(itemView);
            mMenuText = (TextView) itemView.findViewById(R.id.drawer_menu_tv);
            mMenuIcon = (ImageView) itemView.findViewById(R.id.drawer_menu_ic);
        }
    }
    
    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }
}
