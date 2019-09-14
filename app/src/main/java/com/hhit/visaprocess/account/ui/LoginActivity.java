package com.hhit.visaprocess.account.ui;

import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;


import com.hhit.hhbase.base.MvpActivity;
import com.hhit.hhbase.utils.PreferencesUtils;
import com.hhit.hhbase.utils.ToastUtils;
import com.hhit.visaprocess.R;
import com.hhit.visaprocess.account.contract.LoginContract;
import com.hhit.visaprocess.account.presenter.LoginPresenter;
import com.hhit.visaprocess.api.BaseUrl;

import butterknife.BindView;
import rx.functions.Action1;

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;

    private boolean isProduct = true;

    @Override
    protected int layoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        rxClick(btnLogin, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String mobile = etMobile.getText().toString();
                String password = etPassword.getText().toString();
                if(mPresenter.validate(mobile, password)){
                    mPresenter.login(mobile, password);
                }
            }
        });

        rxlongClick(btnLogin, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if(isProduct){
                    BaseUrl.init(BaseUrl.HOST_TEST);
                    PreferencesUtils.setEnviroment("TEST");
                }else {
                    BaseUrl.init(BaseUrl.HOST_PRODUCT);
                    PreferencesUtils.setEnviroment("PRODUCT");
                }
                isProduct = !isProduct;

                ToastUtils.showToast(mContext, isProduct ? "切换生产环境" : "切换到测试环境");

            }
        });
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void mobileError() {
        etMobile.setError("请输出正确手机号码");
    }

    @Override
    public void passwordError() {
        etPassword.setError("密码不能为空");
    }

    @Override
    public void loginSuccess() {
    }

    @Override
    public void loginFailed() {
        ToastUtils.showToast(this, "用户名密码不正确");
    }
}
