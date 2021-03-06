package com.sunfusheng.github.datasource;

import com.sunfusheng.github.cache.db.UserDatabase;
import com.sunfusheng.github.model.User;
import com.sunfusheng.github.http.Api;
import com.sunfusheng.github.http.response.ResponseData;
import com.sunfusheng.github.viewmodel.params.UsernameParams;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author by sunfusheng on 2019-05-27
 */
public class UserDetailDataSource extends BaseDataSource<UsernameParams, User> {
    private String mUserName;

    @Override
    public void setParams(UsernameParams params) {
        this.mUserName = params.username;
    }

    @Override
    public Observable<ResponseData<User>> localObservable() {
        return Observable.defer(() -> Observable.create((ObservableOnSubscribe<ResponseData<User>>) emitter -> {
            DataSourceHelper.emitLocalResponseData(emitter, UserDatabase.instance().getUserDao().query(mUserName));
        })).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<ResponseData<User>> remoteObservable() {
        return Api.getCommonService().fetchUserDetail(mUserName)
                .subscribeOn(Schedulers.io())
                .compose(DataSourceHelper.applyRemoteTransformer())
                .doOnNext(it -> {
                    if (DataSourceHelper.isSuccess(it)) {
                        UserDatabase.instance().getUserDao().insert(it.data);
                    }
                });
    }
}