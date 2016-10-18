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
package com.jun.elephant.mvpframe.base;

import android.os.Bundle;

import com.jun.elephant.common.BaseWebViewActivity;
import com.jun.elephant.mvpframe.BaseModel;
import com.jun.elephant.mvpframe.BasePresenter;
import com.jun.elephant.mvpframe.BaseView;
import com.jun.elephant.mvpframe.util.TUtil;

/**
 * Created by Jun on 2016/10/15.
 */
public abstract class BaseFrameWebViewActivity <P extends BasePresenter, M extends BaseModel> extends BaseWebViewActivity implements BaseView  {

    public P mPresenter;

    public M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.setVM(this, mModel);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onInternetError() {
//        showShortToast("网络异常");
    }

    @Override
    public void onRequestError(String msg) {
        System.out.println("request msg ======= [" + msg + "]");
//        showShortToast(msg);
    }

}
