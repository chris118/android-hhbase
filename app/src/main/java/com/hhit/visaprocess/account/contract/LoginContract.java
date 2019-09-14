package com.hhit.visaprocess.account.contract;


import com.hhit.hhbase.base.IModel;
import com.hhit.hhbase.base.IView;
import com.hhit.visaprocess.account.model.entity.LoginResponse;

import rx.Observable;

/**
 * Created by chrisw on 2017/11/6.
 */

public class LoginContract {
    public interface View extends IView {
        void mobileError();
        void passwordError();
        void loginSuccess();
        void loginFailed();
    }

    public interface Model extends IModel {
        Observable<LoginResponse> login(String username, String password);
    }
}
