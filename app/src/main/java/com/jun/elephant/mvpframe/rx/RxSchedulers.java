package com.jun.elephant.mvpframe.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by baixiaokang on 16/5/6.
 */
public class RxSchedulers {
//    public static <T> Observable.Transformer<T, T> handleDialog(final Context context) {
//        return tObservable -> {
//            tObservable.subscribe(new ProgressSubscriber<>(context));
//            return tObservable;
//        };
//
//    }

    public static <T> Observable.Transformer<T, T> io_main() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {

                return tObservable
                        //生产线程
                        .subscribeOn(Schedulers.io())
                        //消费线程
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };


    }
}
