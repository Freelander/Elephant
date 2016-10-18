package com.jun.elephant.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Jun on 2016/9/27.
 */

public class MySimpleDraweeView extends SimpleDraweeView {

    public MySimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public MySimpleDraweeView(Context context) {
        super(context);
    }

    public MySimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageURI(Uri uri) {

        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setLocalThumbnailPreviewsEnabled(true)
                .setProgressiveRenderingEnabled(false)
                .setResizeOptions(new ResizeOptions(50, 50))
                .build();
/*
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setImageRequest(request)
                .setOldController(getController())
                .build();*/

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setImageRequest(request)
                .setOldController(getController())
                .build();

        setController(controller);

        super.setImageURI(uri);
    }

    public void setImageUrl(String url) {
        setImageURI(Uri.parse(url));
    }
}
