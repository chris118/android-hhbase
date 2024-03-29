package com.hhit.visaprocess.account.presenter;


import com.hhit.hhbase.base.BasePresenter;
import com.hhit.visaprocess.account.contract.LoginContract;
import com.hhit.visaprocess.account.model.LoginModel;
import com.hhit.visaprocess.account.model.entity.LoginResponse;

import rx.Subscriber;

/**
 * Created by chrisw on 2017/11/6.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View, LoginContract.Model> {
    private final static String TAG = LoginPresenter.class.getSimpleName();

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    protected LoginContract.Model createModel() {
        return new LoginModel();
    }

    public boolean validate(String mobile, String password) {
        if (mobile.isEmpty() ) {
            mView.mobileError();
            return false;
        }

        if (password.isEmpty() ) {
            mView.passwordError();
            return false;
        }

        return true;
    }

    public void login(String mobile, String password){
        addSubscribe(mModel.login(mobile, password).subscribe(new Subscriber<LoginResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mView.loginFailed();
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                mView.loginSuccess();
            }
        }));
    }
}
