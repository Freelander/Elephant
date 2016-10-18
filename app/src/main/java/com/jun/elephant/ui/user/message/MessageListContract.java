package com.jun.elephant.ui.user.message;

import com.jun.elephant.entity.user.UserMessageEntity;
import com.jun.elephant.mvpframe.BaseModel;
import com.jun.elephant.mvpframe.BasePresenter;
import com.jun.elephant.mvpframe.BaseView;

import rx.Observable;

/**
 * Created by Jun on 2016/9/10.
 */

public interface MessageListContract {
    interface Model extends BaseModel {
        Observable<UserMessageEntity> getUserMessage(int pageIndex);
    }

    interface View extends BaseView {
        void refreshMessageList(UserMessageEntity userMessageEntity);

        void loadMoreMessageList(UserMessageEntity userMessageEntity);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getMessageList(int pageIndex);
    }
}
