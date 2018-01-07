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
package com.jun.elephant.ui.main;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.jun.elephant.R;
import com.jun.elephant.common.BaseActivity;
import com.jun.elephant.global.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by Jun on 2016/4/19.
 */
public class PhotoShowActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.photo_iv)
    PhotoDraweeView mPhotoIv;

    private String mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mImageUrl = getIntent().getExtras().getString(Constants.Key.TOPIC_IMAGE_URL);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.app_photo_show));
    }

    @Override
    public void initLoad() {
        super.initLoad();

        if (!TextUtils.isEmpty(mImageUrl)) {
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(mImageUrl);
            controller.setOldController(mPhotoIv.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null || mPhotoIv == null) {
                        return;
                    }
                    mPhotoIv.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            mPhotoIv.setController(controller.build());
        }
    }
}
