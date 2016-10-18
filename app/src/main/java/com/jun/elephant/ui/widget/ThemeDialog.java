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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.jun.elephant.R;

/**
 * Created by Jun on 2016/9/22.
 */

public class ThemeDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private OnThemeChangeListener onThemeChangeListener;

    public ThemeDialog(Context context) {
        this(context, R.style.MyDialog_style);

    }

    public ThemeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initContentView();
    }

    private void initContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_theme, null);

        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        view.setMinimumWidth((int)(displayRectangle.width() * 0.8f));
        window.setBackgroundDrawableResource(R.color.dialog_bg);

        setContentView(view);

        view.findViewById(R.id.theme_blue).setOnClickListener(this);
        view.findViewById(R.id.theme_gray).setOnClickListener(this);
        view.findViewById(R.id.theme_white).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onThemeChangeListener != null) {
            onThemeChangeListener.onChangeTheme(v);
            dismiss();
        }
    }

    public interface OnThemeChangeListener {
        void onChangeTheme(View view);
    }

    public void setOnThemeChangeListener(OnThemeChangeListener onThemeChangeListener) {
        this.onThemeChangeListener = onThemeChangeListener;
    }
}
