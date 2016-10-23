package com.jun.elephant.util;

import android.content.Context;

/**
 * Created by ykrc17 on 2016/10/23.
 */

public class ResUtil {
    public static int dp2px(Context context, float dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5);
    }
}
