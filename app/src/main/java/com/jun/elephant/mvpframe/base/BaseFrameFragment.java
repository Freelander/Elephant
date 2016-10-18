package com.jun.elephant.mvpframe.base;

import android.os.Bundle;

import com.jun.elephant.common.BaseFragment;
import com.jun.elephant.mvpframe.BaseModel;
import com.jun.elephant.mvpframe.BasePresenter;
import com.jun.elephant.mvpframe.BaseView;
import com.jun.elephant.mvpframe.util.TUtil;
import com.jun.elephant.util.JLog;


/**
 * Created by quan on 16/9/20.
 */

public abstract  class BaseFrameFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment implements BaseView {

    public P mPresenter;

    public M mModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.setVM(this, mModel);
        }

    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onInternetError() {
//        showShortToast("网络异常");
    }

    @Override
    public void onRequestError(String msg) {
//        showShortToast(msg);
        JLog.e("REQUEST_ERROR ==== ", msg);
    }

}
