package com.hhit.visaprocess.account.model;

import com.hhit.hhbase.retrofit.RxRetrofitComposer;
import com.hhit.visaprocess.account.contract.LoginContract;
import com.hhit.visaprocess.account.model.entity.LoginResponse;
import com.hhit.visaprocess.api.HHServiceManager;
import com.hhit.visaprocess.api.UserApi;

import rx.Observable;

/**
 * Created by chrisw on 2017/11/6.
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginResponse> login(String username, String password) {
        UserApi api = HHServiceManager.getInstance().getUserApi();
        return api.login(username, password)
                .compose(RxRetrofitComposer.<LoginResponse>applySchedulers());
    }
}
